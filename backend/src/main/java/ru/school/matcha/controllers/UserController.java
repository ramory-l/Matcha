package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.*;
import ru.school.matcha.domain.*;
import ru.school.matcha.dto.*;
import ru.school.matcha.enums.Location;
import ru.school.matcha.enums.Response;
import ru.school.matcha.enums.Role;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.MessageServiceImpl;
import ru.school.matcha.services.TagServiceImpl;
import ru.school.matcha.services.UserServiceImpl;
import ru.school.matcha.services.interfaces.MessageService;
import ru.school.matcha.services.interfaces.TagService;
import ru.school.matcha.services.interfaces.UserService;
import spark.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.Objects.nonNull;
import static spark.Spark.halt;

@Slf4j
public class UserController {

    private final static Converter<UserFullDto, User> userFullConverter = new UserFullConverter();
    private final static Converter<UserDto, User> userConverter = new UserConverter();
    private final static Converter<MessageDto, Message> messageConverter = new MessageConverter();
    private final static Converter<UserFullForBatchDto, UserFullForBatch> userFullForBatchConverter = new UserFullForBatchConverter();
    private final static Converter<UserWithTagsDto, User> userWithTagsConverter = new UserWithTagsConverter();
    private final static Converter<TagDto, Tag> tagConverter = new TagConverter();
    private final static Converter<FormDto, Form> formConverter = new FormConverter();

    private final static UserService userService = new UserServiceImpl();
    private final static MessageService messageService = new MessageServiceImpl();
    private final static TagService tagService = new TagServiceImpl();

    private final static Serializer<UserFullDto> userFullDtoSerializer = new Serializer<>();
    private final static Serializer<PassResetDto> passResetSerializer = new Serializer<>();

    public static Route createUser = (request, response) -> {
        UserFullDto userFullDto = userFullDtoSerializer.deserialize(request.body(), UserFullDto.class);
        User user = userFullConverter.convertFromDto(userFullDto);
        Long userId = userService.createUser(user);
        response.status(Response.POST.getStatus());
        response.header(Location.HEADER, Location.USERS.getUrl() + userId);
        return "";
    };

    public static Route batchUsersCreate = (request, response) -> {
        AuthorizationController.authorize(request, Role.ADMIN);
        Serializer<UserFullForBatchDto> serializer = new Serializer<>();
        List<UserFullForBatchDto> userFullDtoList = serializer.deserializeList(request.body(), UserFullForBatchDto.class);
        List<UserFullForBatch> userFullForBatchList = userFullForBatchConverter.createFromDtos(userFullDtoList);
        userService.batchCreateUsers(userFullForBatchList);
        response.status(Response.POST.getStatus());
        return "";
    };

    public static Route search = (request, response) -> {
        Integer ageFrom = nonNull(request.queryParams("agefrom")) ? parseInt(request.queryParams("agefrom")) : null;
        Integer ageTo = nonNull(request.queryParams("ageto")) ? parseInt(request.queryParams("ageto")) : null;
        Integer rateTo = nonNull(request.queryParams("rateto")) ? parseInt(request.queryParams("rateto")) : null;
        Integer rateFrom = nonNull(request.queryParams("ratefrom")) ? parseInt(request.queryParams("ratefrom")) : null;
        if (ageFrom < 0 || ageTo < 0) {
            throw new NumberFormatException();
        }
        FormDto formDto = new FormDto(
                null,
                Boolean.parseBoolean(request.queryParams("man")),
                Boolean.parseBoolean(request.queryParams("woman")),
                ageFrom,
                ageTo,
                rateFrom,
                rateTo,
                parseInt(request.queryParams("radius"))
        );
        String tags = request.queryParams("tags");
        List<String> tagList = null;
        if (tags != null && !tags.equals("")) {
            tagList = Arrays.asList(tags.split(","));
        }
        long id = parseLong(request.params("id")),
                userId = AuthorizationController.authorize(request, Role.USER);
        if (userId != 0 && id != userId) {
            halt(403, "Access is denied");
        }
        Form form = formConverter.convertFromDto(formDto);
        List<User> users = userService.search(id, form, tagList);
        List<UserDto> result = userConverter.createFromEntities(users);
        response.status(Response.GET.getStatus());
        return result;
    };

    public static Route getAllUsers = (request, response) -> {
        long userId = AuthorizationController.authorize(request, Role.USER);
        List<User> users = userService.getAllUsers(userId);
        List<UserDto> result = userConverter.createFromEntities(users);
        response.status(Response.GET.getStatus());
        return result;
    };

    public static Route getUserById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        AuthorizationController.authorize(request, Role.USER);
        User user = userService.getUserById(id);
        UserDto result = userConverter.convertFromEntity(user);
        response.status(Response.GET.getStatus());
        return result;
    };

    public static Route getUserByUsername = (request, response) -> {
        String username = request.params("username");
        AuthorizationController.authorize(request, Role.USER);
        User user = userService.getUserByUsername(username);
        UserDto result = userConverter.convertFromEntity(user);
        response.status(Response.GET.getStatus());
        return result;
    };

    public static Route updateUser = (request, response) -> {
        AuthorizationController.authorize(request, Role.USER);
        UserFullDto userFullDto = userFullDtoSerializer.deserialize(request.body(), UserFullDto.class);
        User user = userFullConverter.convertFromDto(userFullDto);
        userService.updateUser(user);
        response.status(Response.PUT.getStatus());
        return "";
    };

    public static Route editPassword = (request, response) -> {
        String hash = request.params("hash");
        userService.updatePassword(hash);
        response.status(Response.GET.getStatus());
        return "Password edited";
    };

    public static Route getMatcha = (request, response) -> {
        Long id = parseLong(request.params("id"));
        List<User> users = userService.getMatcha(id);
        response.status(Response.GET.getStatus());
        List<UserWithTagsDto> result = userWithTagsConverter.createFromEntities(users);
        if (nonNull(result)) {
            result.forEach(user -> user.setTags(tagConverter.createFromEntities(tagService.getMutualTags(id, user.getId()))));
        }
        return result;
    };

    public static Route resetPassword = (request, response) -> {
        PassResetDto passResetDto = passResetSerializer.deserialize(request.body(), PassResetDto.class);
        userService.formingResetPasswordEmail(passResetDto.getEmail(), passResetDto.getNewPass());
        response.status(Response.PUT.getStatus());
        return "";
    };

    public static Route deleteUserById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        AuthorizationController.authorize(request, Role.ADMIN);
        userService.deleteUserById(id);
        response.status(Response.DELETE.getStatus());
        return "";
    };

    public static Route getMessages = (request, response) -> {
        int limit = parseInt(request.params("limit")),
                offset = parseInt(request.params("offset"));
        long first = parseLong(request.params("first")),
                second = parseLong(request.params("second"));
        Long userId = AuthorizationController.authorize(request, Role.USER);
        if (userId != 0 && first != userId && second != userId) {
            halt(403, "Access is denied");
        }
        long totalCount = messageService.getTotalCountMessages(first, second);
        if (totalCount == 0) {
            return new PageDto<>(new ArrayList<>(), totalCount, offset);
        } else if (offset >= totalCount) {
            throw new MatchaException("Offset is greater than total count entities");
        }
        List<MessageDto> result = messageConverter.createFromEntities(messageService.getMessages(limit, offset, first, second));
        response.status(Response.GET.getStatus());
        return new PageDto<>(result, totalCount, offset);
    };

    public static Route getUsersByTagName = (request, response) -> {
        String tagName = request.params("tagName");
        long userId = AuthorizationController.authorize(request, Role.USER);
        response.status(Response.GET.getStatus());
        return userConverter.createFromEntities(userService.getUsersByTagId(tagService.getTagByName(tagName).getId(), userId));
    };

    public static Route verified = (request, response) -> {
        String hash = request.params("hash");
        userService.verified(hash);
        response.status(Response.GET.getStatus());
        return "Profile verified";
    };

    public static Route addToBlackList = (request, response) -> {
        long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        Long userId = AuthorizationController.authorize(request, Role.USER);
        if (userId != 0 && from != userId) {
            halt(403, "Access is denied");
        }
        userService.addToBlackList(from, to);
        response.header(Location.HEADER, Location.USERS.getUrl() + to);
        response.status(Response.POST.getStatus());
        return "";
    };

    public static Route deleteFromBlackList = (request, response) -> {
        long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        Long userId = AuthorizationController.authorize(request, Role.USER);
        if (userId != 0 && from != userId) {
            halt(403, "Access is denied");
        }
        userService.deleteFromBlackList(from, to);
        response.status(Response.DELETE.getStatus());
        return "";
    };

    public static Route getUserBlackList = (request, response) -> {
        long id = parseLong(request.params("userId"));
        Long userId = AuthorizationController.authorize(request, Role.USER);
        if (userId != 0 && id != userId) {
            halt(403, "Access is denied");
        }
        List<User> user = userService.getUserBlackList(id);
        response.status(Response.GET.getStatus());
        return userConverter.createFromEntities(user);
    };

    public static Route report = (request, response) -> {
        long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        String message = request.body();
        Long userId = AuthorizationController.authorize(request, Role.USER);
        if (userId != 0 && from != userId) {
            halt(403, "Access is denied");
        }
        userService.userIsFake(from, to, message);
        response.header(Location.HEADER, Location.USERS.getUrl() + to);
        response.status(Response.POST.getStatus());
        return "";
    };

}
