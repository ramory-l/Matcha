package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.converters.MessageConverter;
import ru.school.matcha.converters.UserConverter;
import ru.school.matcha.converters.UserFullConverter;
import ru.school.matcha.domain.Message;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.*;
import ru.school.matcha.enums.Location;
import ru.school.matcha.enums.Response;
import ru.school.matcha.enums.Role;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.MessageServiceImpl;
import ru.school.matcha.services.TagServiceImpl;
import ru.school.matcha.services.UserServiceImpl;
import ru.school.matcha.services.interfaces.MessageService;
import ru.school.matcha.services.interfaces.TagService;
import ru.school.matcha.services.interfaces.UserService;
import spark.Route;

import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static spark.Spark.halt;

@Slf4j
public class UserController {

    private final static Converter<UserFullDto, User> userFullConverter = new UserFullConverter();
    private final static Converter<UserDto, User> userConverter = new UserConverter();
    private final static Converter<MessageDto, Message> messageConverter = new MessageConverter();

    private final static UserService userService = new UserServiceImpl();
    private final static TagService tagService = new TagServiceImpl();
    private final static MessageService messageService = new MessageServiceImpl();

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
        Serializer<UserFullDto> serializer = new Serializer<>();
        List<UserFullDto> userFullDtoList = serializer.deserializeList(request.body(), UserFullDto.class);
        List<User> users = userFullConverter.createFromDtos(userFullDtoList);
        userService.batchCreateUsers(users);
        response.status(Response.POST.getStatus());
        return "";
    };

    public static Route getAllUsers = (request, response) -> {
        AuthorizationController.authorize(request, Role.USER);
        List<User> users = userService.getAllUsers();
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
        return "";
    };

    public static Route getMatcha = (request, response) -> {
        Long id = parseLong(request.params("id"));
        List<User> users = userService.getMatcha(id);
        response.status(Response.GET.getStatus());
        return userConverter.createFromEntities(users);
    };

    public static Route resetPassword = (request, response) -> {
        PassResetDto passResetDto = passResetSerializer.deserialize(request.body(), PassResetDto.class);
        userService.formingEmail(passResetDto.getEmail(), passResetDto.getNewPass());
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

    public static Route deleteUserByUsername = (request, response) -> {
        String username = request.params("username");
        AuthorizationController.authorize(request, Role.ADMIN);
        userService.deleteUserByUsername(username);
        response.status(Response.DELETE.getStatus());
        return "";
    };

    public static Route getUsersByTagName = (request, response) -> {
        String tagName = request.params("tagName");
        AuthorizationController.authorize(request, Role.USER);
        response.status(Response.GET.getStatus());
        return userConverter.createFromEntities(userService.getUsersByTagId(tagService.getTagByName(tagName).getId()));
    };

    public static Route getMessages = (request, response) -> {
        int limit = parseInt(request.params("limit")),
                offset = parseInt(request.params("offset"));
        long first = parseLong(request.params("first")),
                second = parseLong(request.params("second"));
        Long userId = AuthorizationController.authorize(request, Role.USER);
        if (userId != 0 || (first != userId || second != userId)) {
            halt(403, "Access is denied");
        }
        long totalCount = messageService.getTotalCountMessages(first, second);
        List<MessageDto> result = messageConverter.createFromEntities(messageService.getMessages(limit, offset, first, second));
        response.status(Response.GET.getStatus());
        return new PageDto<>(result, totalCount, offset);
    };

}
