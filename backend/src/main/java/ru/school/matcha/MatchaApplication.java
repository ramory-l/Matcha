package ru.school.matcha;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.school.matcha.converters.*;
import ru.school.matcha.domain.Auth;
import ru.school.matcha.domain.Credentials;
import ru.school.matcha.domain.Form;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.*;
import ru.school.matcha.exceptions.JwtAuthenticationException;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.AuthenticationServiceImpl;
import ru.school.matcha.services.AuthorizationServiceImpl;
import ru.school.matcha.services.FormServiceImpl;
import ru.school.matcha.services.UserServiceImpl;
import ru.school.matcha.services.interfaces.AuthenticationService;
import ru.school.matcha.services.interfaces.AuthorizationService;
import ru.school.matcha.services.interfaces.FormService;
import ru.school.matcha.services.interfaces.UserService;
import spark.HaltException;

import javax.naming.AuthenticationException;
import java.util.List;

import static spark.Spark.*;
import static java.lang.Long.*;

public class MatchaApplication {

    private static final Logger logger = LoggerFactory.getLogger(MatchaApplication.class);

    public static void main(String[] args) {
        port(8080);
        Converter<UserDto, User> userConverter = new UserConverter();
        Converter<FormDto, Form> formConverter = new FormConverter();
        Converter<CredentialsDto, Credentials> credentialsConverter = new CredentialsConverter();
        Converter<UserFullDto, User> userFullConverter = new UserFullConverter();
        Converter<AuthDto, Auth> authConverter = new AuthConverter();
        UserService userService = new UserServiceImpl();
        FormService formService = new FormServiceImpl();
        AuthenticationService authenticationService = new AuthenticationServiceImpl();
        AuthorizationService authorizationService = new AuthorizationServiceImpl();
        before("/*", (req, res) -> {
            res.header("access-control-expose-headers", "x-auth-token");
        });
        path("/api", () -> {
            path("/auth", () -> post("/login", "application/json", (req, res) -> {
                Serializer<AuthDto> serializer = new Serializer<>();
                AuthDto authDto = serializer.deserialize(req.body(), AuthDto.class);
                Auth authData = authConverter.convertFromDto(authDto);
                try {
                    String token = authenticationService.authenticate(authData.getUsername(), authData.getPassword());
                    return ImmutableMap.<String, String>builder().put("username", authData.getUsername()).put("x-auth-token", token).build();
                } catch (AuthenticationException ex) {
                    logger.error("Failed to login", ex);
                    res.status(403);
                    res.body(String.format("Authentication failed. %s", ex.getMessage()));
                } catch (MatchaException ex) {
                    logger.error("Failed to login", ex);
                    res.status(400);
                    res.body(String.format("Failed to login. %s", ex.getMessage()));
                } catch (Exception ex) {
                    logger.error("An unexpected error occurred while trying to login", ex);
                    res.status(500);
                    res.body(String.format("An unexpected error occurred while trying to login. %s", ex.getMessage()));
                }
                return res.body();
            }, new JsonTransformer()));
            path("/user", () -> {
                before("/*", (request, response) -> {
                    boolean authorization = false;
                    try {
                        authorization = authorizationService.authorize(request.headers("x-auth-token"));
                    } catch (JwtAuthenticationException ex) {
                        logger.error("Credentials are invalid");
                    }
                    if (!authorization) {
                        halt(401, "Credentials are invalid");
                    }
                });
                post("/", "application/json", (req, res) -> {
                    try {
                        Serializer<CredentialsDto> serializer = new Serializer<>();
                        CredentialsDto credentialsDto = serializer.deserialize(req.body(), CredentialsDto.class);
                        Credentials credentials = credentialsConverter.convertFromDto(credentialsDto);
                        Long userId = userService.createUser(credentials);
                        res.status(200);
                        return userId;
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to create user", ex);
                        res.status(400);
                        res.body(String.format("Failed to create user. %s", ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to create user", ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to create user. %s", ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
                post("/batch", "application/json", (req, res) -> {
                    try {
                        Serializer<UserFullDto> serializer = new Serializer<>();
                        List<UserFullDto> userFullDtoList = serializer.deserializeList(req.body(), UserFullDto.class);
                        List<User> users = userFullConverter.createFromDtos(userFullDtoList);
                        userService.batchCreateUsers(users);
                        res.status(204);
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to batch create users", ex);
                        res.status(400);
                        res.body(String.format("Failed to batch create users. %s", ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to batch create users", ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to batch create users. %s", ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
                get("/all", "application/json", (req, res) -> {
                    try {
                        List<User> users = userService.getAllUsers();
                        List<UserDto> result = userConverter.createFromEntities(users);
                        res.status(200);
                        return result;
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to get all users", ex);
                        res.status(400);
                        res.body(String.format("Failed to get all users. %s", ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to get all users", ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to get all users. %s", ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
                get("/:id", "application/json", (req, res) -> {
                    Long id = parseLong(req.params("id"));
                    try {
                        User user = userService.getUserById(id)
                                .orElseThrow(() -> new MatchaException(String.format("User with id: %d doesn't exits", id)));
                        UserDto result = userConverter.convertFromEntity(user);
                        res.status(200);
                        return result;
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to get user by id: {}", id, ex);
                        res.status(400);
                        res.body(String.format("Failed to get user by id: %d. %s", id, ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to get user by id: {}", id, ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to get user by id: %d. %s", id, ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
                get("/username/:username", "application/json", (req, res) -> {
                    String username = req.params("username");
                    try {
                        User user = userService.getUserByUsername(username)
                                .orElseThrow(() -> new MatchaException(String.format("User with username: %s doesn't exist", username)));
                        UserDto result = userConverter.convertFromEntity(user);
                        res.status(200);
                        return result;
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to get user by username: {}", username, ex);
                        res.status(400);
                        res.body(String.format("Failed to get user by username: %s. %s", username, ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to get user by username: {}", username, ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to get user by username: %s. %s", username, ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
                put("/", "application/json", (req, res) -> {
                    try {
                        Serializer<UserDto> serializer = new Serializer<>();
                        UserDto userDto = serializer.deserialize(req.body(), UserDto.class);
                        User user = userConverter.convertFromDto(userDto);
                        userService.updateUser(user);
                        res.status(200);
//                        условие в зависимости от параметра
                        res.body(String.format("User with username: %s updated", user.getUsername()));
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to update user", ex);
                        res.status(400);
                        res.body(String.format("Failed to update user. %s", ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to update user", ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to update user. %s", ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
                delete("/:id", "application/json", (req, res) -> {
                    Long id = parseLong(req.params("id"));
                    try {
                        userService.deleteUserById(id);
                        res.status(200);
                        res.body(String.format("Removing user with id %d was successful", id));
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to delete user by id: {}", id, ex);
                        res.status(400);
                        res.body(String.format("Failed to delete user by id: %d. %s", id, ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to delete user by id: {}", id, ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to delete user by id: %d. %s", id, ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
                delete("/username/:username", "application/json", (req, res) -> {
                    String username = req.params("username");
                    try {
                        userService.deleteUserByUsername(username);
                        res.status(200);
                        res.body(String.format("Removing user with username %s was successful", username));
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to delete user by username: {}", username, ex);
                        res.status(400);
                        res.body(String.format("Failed to delete user by username: %s. %s", username, ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to delete user by username: {}", username, ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to delete user by username: %s. %s", username, ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
            });
            path("/form", () -> {
                before("/*", (request, response) -> {
                    boolean authorization = false;
                    try {
                        authorization = authorizationService.authorize(request.headers("token"));
                    } catch (JwtAuthenticationException ex) {
                        logger.error("Credentials are invalid");
                    }
                    if (!authorization) {
                        halt(401, "Credentials are invalid");
                    }
                });
                post("/", "application/json", (req, res) -> {
                    try {
                        Serializer<FormDto> serializer = new Serializer<>();
                        FormDto formDto = serializer.deserialize(req.body(), FormDto.class);
                        Form form = formConverter.convertFromDto(formDto);
                        Long formId = formService.createForm(form);
                        res.status(200);
                        return formId;
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to create form", ex);
                        res.status(400);
                        res.body(String.format("Failed to create form. %s", ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to create form", ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to create form. %s", ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
                get("/all", "application/json", (req, res) -> {
                    try {
                        List<Form> forms = formService.getAllForms();
                        List<FormDto> result = formConverter.createFromEntities(forms);
                        res.status(200);
                        return result;
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to get all forms", ex);
                        res.status(400);
                        res.body(String.format("Failed to get all forms. %s", ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to get all forms", ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to get all forms. %s", ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
                get("/:id", "application/json", (req, res) -> {
                    Long id = parseLong(req.params("id"));
                    try {
                        Form form = formService.getFormById(id)
                                .orElseThrow(() -> new MatchaException(String.format("Form with id: %s doesn't exist", id)));
                        FormDto result = formConverter.convertFromEntity(form);
                        res.status(200);
                        return result;
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to get form by id: {}", id, ex);
                        res.status(400);
                        res.body(String.format("Failed to get form by id: %d. %s", id, ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to get form by id: {}", id, ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to get form by id: %d. %s", id, ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
                get("/user/:id", "application/json", (req, res) -> {
                    Long userId = parseLong(req.params("id"));
                    try {
                        Form form = formService.getFormByUserId(userId)
                                .orElseThrow(() -> new MatchaException(String.format("Form by user with id %d doesn't exist", userId)));
                        FormDto result = formConverter.convertFromEntity(form);
                        res.status(200);
                        return result;
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to get form by user id: {}", userId, ex);
                        res.status(400);
                        res.body(String.format("Failed to get form by user id: %d. %s", userId, ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to get form by user id: {}", userId, ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to get form by user id: %d. %s", userId, ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
                put("/{userId}", "application/json", (req, res) -> {
                    Long userId = parseLong(req.params("userId"));
                    try {
                        Serializer<FormDto> serializer = new Serializer<>();
                        FormDto formDto = serializer.deserialize(req.body(), FormDto.class);
                        Form form = formConverter.convertFromDto(formDto);
                        formService.updateForm(form, userId);
                        res.status(200);
                        res.body("Form update was successful");
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to update form", ex);
                        res.status(400);
                        res.body(String.format("Failed to update form. %s", ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to update form", ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to update form. %s", ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
                delete("/:id", "application/json", (req, res) -> {
                    Long id = parseLong(req.params("id"));
                    try {
                        formService.deleteFormById(id);
                        res.status(200);
                        res.body(String.format("Removing form with id %s was successful", id));
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to delete form by id: {}", id, ex);
                        res.status(400);
                        res.body(String.format("Failed to delete form by id: %d. %s", id, ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to delete form by id: {}", id, ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to delete form by id: %d. %s", id, ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
                delete("/user/:id", (req, res) -> {
                    Long userId = parseLong(req.params("id"));
                    try {
                        formService.deleteFormByUserId(userId);
                        res.status(200);
                        res.body(String.format("Removing form by user with user id %d was successful", userId));
                    } catch (HaltException ex) {
                        logger.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        logger.error("Failed to delete form by user id: {}", userId, ex);
                        res.status(400);
                        res.body(String.format("Failed to delete form by user id: %d. %s", userId, ex.getMessage()));
                    } catch (Exception ex) {
                        logger.error("An unexpected error occurred while trying to delete form by user id: {}", userId, ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to delete form by user id: %d. %s", userId, ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
            });
        });
    }

}
