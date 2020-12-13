package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.converters.UserConverter;
import ru.school.matcha.converters.UserFullConverter;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.PassResetDto;
import ru.school.matcha.dto.UserDto;
import ru.school.matcha.dto.UserFullDto;
import ru.school.matcha.enums.Location;
import ru.school.matcha.enums.Response;
import ru.school.matcha.enums.Role;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.TagServiceImpl;
import ru.school.matcha.services.UserServiceImpl;
import ru.school.matcha.services.interfaces.TagService;
import ru.school.matcha.services.interfaces.UserService;
import spark.Route;

import java.util.List;

import static java.lang.Long.parseLong;

@Slf4j
public class UserController {

    private final static Converter<UserFullDto, User> userFullConverter;
    private final static Converter<UserDto, User> userConverter;

    private final static UserService userService;
    private final static TagService tagService;

    private final static Serializer<UserFullDto> userFullDtoSerializer;
    private final static Serializer<UserDto> userDtoSerializer;
    private static final Serializer<PassResetDto> passResetSerializer;

    static {
        userFullConverter = new UserFullConverter();
        userConverter = new UserConverter();
        userService = new UserServiceImpl();
        tagService = new TagServiceImpl();
        userFullDtoSerializer = new Serializer<>();
        userDtoSerializer = new Serializer<>();
        passResetSerializer = new Serializer<>();
    }

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
        UserDto userDto = userDtoSerializer.deserialize(request.body(), UserDto.class);
        User user = userConverter.convertFromDto(userDto);
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

}
