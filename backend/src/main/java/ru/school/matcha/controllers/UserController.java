package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.converters.CredentialsConverter;
import ru.school.matcha.converters.UserConverter;
import ru.school.matcha.converters.UserFullConverter;
import ru.school.matcha.domain.Credentials;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.CredentialsDto;
import ru.school.matcha.dto.UserDto;
import ru.school.matcha.dto.UserFullDto;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.UserServiceImpl;
import ru.school.matcha.services.interfaces.UserService;
import spark.HaltException;
import spark.Route;

import java.util.List;

import static java.lang.Long.parseLong;

@Slf4j
public class UserController {

    private final static Converter<CredentialsDto, Credentials> credentialsConverter;
    private final static Converter<UserFullDto, User> userFullConverter;
    private final static Converter<UserDto, User> userConverter;
    private final static UserService userService;

    static {
        credentialsConverter = new CredentialsConverter();
        userFullConverter = new UserFullConverter();
        userConverter = new UserConverter();
        userService = new UserServiceImpl();
    }

    public static Route createUser = (request, response) -> {
        try {
            Serializer<CredentialsDto> serializer = new Serializer<>();
            CredentialsDto credentialsDto = serializer.deserialize(request.body(), CredentialsDto.class);
            Credentials credentials = credentialsConverter.convertFromDto(credentialsDto);
            Long userId = userService.createUser(credentials);
            response.status(200);
            return userId;
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to create user", ex);
            response.status(400);
            response.body(String.format("Failed to create user. %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to create user", ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to create user. %s", ex.getMessage()));
        }
        return response.body();
    };

    public static Route batchUsersCreate = (request, response) -> {
        try {
            Serializer<UserFullDto> serializer = new Serializer<>();
            List<UserFullDto> userFullDtoList = serializer.deserializeList(request.body(), UserFullDto.class);
            List<User> users = userFullConverter.createFromDtos(userFullDtoList);
            userService.batchCreateUsers(users);
            response.status(204);
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to batch create users", ex);
            response.status(400);
            response.body(String.format("Failed to batch create users. %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to batch create users", ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to batch create users. %s", ex.getMessage()));
        }
        return response.body();
    };

    public static Route getAllUsers = (request, response) -> {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDto> result = userConverter.createFromEntities(users);
            response.status(200);
            return result;
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to get all users", ex);
            response.status(400);
            response.body(String.format("Failed to get all users. %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to get all users", ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to get all users. %s", ex.getMessage()));
        }
        return response.body();
    };

    public static Route getUserById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        try {
            User user = userService.getUserById(id)
                    .orElseThrow(() -> new MatchaException(String.format("User with id: %d doesn't exits", id)));
            UserDto result = userConverter.convertFromEntity(user);
            response.status(200);
            return result;
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to get user by id: {}", id, ex);
            response.status(400);
            response.body(String.format("Failed to get user by id: %d. %s", id, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to get user by id: {}", id, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to get user by id: %d. %s", id, ex.getMessage()));
        }
        return response.body();
    };

    public static Route getUserByUsername = (request, response) -> {
        String username = request.params("username");
        try {
            User user = userService.getUserByUsername(username)
                    .orElseThrow(() -> new MatchaException(String.format("User with username: %s doesn't exist", username)));
            UserDto result = userConverter.convertFromEntity(user);
            response.status(200);
            return result;
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to get user by username: {}", username, ex);
            response.status(400);
            response.body(String.format("Failed to get user by username: %s. %s", username, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to get user by username: {}", username, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to get user by username: %s. %s", username, ex.getMessage()));
        }
        return response.body();
    };

    public static Route updateUser = (request, response) -> {
        try {
            Serializer<UserDto> serializer = new Serializer<>();
            UserDto userDto = serializer.deserialize(request.body(), UserDto.class);
            User user = userConverter.convertFromDto(userDto);
            userService.updateUser(user);
            response.status(200);
//                        условие в зависимости от параметра
            response.body(String.format("User with username: %s updated", user.getUsername()));
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to update user", ex);
            response.status(400);
            response.body(String.format("Failed to update user. %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to update user", ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to update user. %s", ex.getMessage()));
        }
        return response.body();
    };

    public static Route deleteUserById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        try {
            userService.deleteUserById(id);
            response.status(200);
            response.body(String.format("Removing user with id %d was successful", id));
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to delete user by id: {}", id, ex);
            response.status(400);
            response.body(String.format("Failed to delete user by id: %d. %s", id, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to delete user by id: {}", id, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to delete user by id: %d. %s", id, ex.getMessage()));
        }
        return response.body();
    };

    public static Route deleteUserByUsername = (request, response) -> {
        String username = request.params("username");
        try {
            userService.deleteUserByUsername(username);
            response.status(200);
            response.body(String.format("Removing user with username %s was successful", username));
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to delete user by username: {}", username, ex);
            response.status(400);
            response.body(String.format("Failed to delete user by username: %s. %s", username, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to delete user by username: {}", username, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to delete user by username: %s. %s", username, ex.getMessage()));
        }
        return response.body();
    };

}
