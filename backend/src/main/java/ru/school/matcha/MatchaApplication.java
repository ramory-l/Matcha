package ru.school.matcha;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.controllers.AuthenticateController;
import ru.school.matcha.controllers.AuthorizationController;
import ru.school.matcha.controllers.UserController;
import ru.school.matcha.converters.*;
import ru.school.matcha.domain.Form;
import ru.school.matcha.dto.*;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.FormServiceImpl;
import ru.school.matcha.services.interfaces.FormService;
import spark.HaltException;

import java.util.List;

import static spark.Spark.*;
import static java.lang.Long.*;

@Slf4j
public class MatchaApplication {

    public static void main(String[] args) {
        port(8080);
        enableCORS();
        Converter<FormDto, Form> formConverter = new FormConverter();
        FormService formService = new FormServiceImpl();
        path("/api", () -> {
            path("/auth", () ->
                    post("/login", AuthenticateController.authenticate, new JsonTransformer()));
            path("/user", () -> {
                before("/*", AuthorizationController.authorize);
                post("/", UserController.createUser, new JsonTransformer());
                post("/batch", UserController.batchUsersCreate, new JsonTransformer());
                get("/all", UserController.getAllUsers, new JsonTransformer());
                get("/:id", UserController.getUserById, new JsonTransformer());
                get("/username/:username", UserController.getUserByUsername, new JsonTransformer());
                put("/", UserController.updateUser, new JsonTransformer());
                delete("/:id", UserController.deleteUserById, new JsonTransformer());
                delete("/username/:username", UserController.deleteUserByUsername, new JsonTransformer());
            });
            path("/form", () -> {
                before("/*", AuthorizationController.authorize);
                post("/", "application/json", (req, res) -> {
                    try {
                        Serializer<FormDto> serializer = new Serializer<>();
                        FormDto formDto = serializer.deserialize(req.body(), FormDto.class);
                        Form form = formConverter.convertFromDto(formDto);
                        Long formId = formService.createForm(form);
                        res.status(200);
                        return formId;
                    } catch (HaltException ex) {
                        log.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        log.error("Failed to create form", ex);
                        res.status(400);
                        res.body(String.format("Failed to create form. %s", ex.getMessage()));
                    } catch (Exception ex) {
                        log.error("An unexpected error occurred while trying to create form", ex);
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
                        log.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        log.error("Failed to get all forms", ex);
                        res.status(400);
                        res.body(String.format("Failed to get all forms. %s", ex.getMessage()));
                    } catch (Exception ex) {
                        log.error("An unexpected error occurred while trying to get all forms", ex);
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
                        log.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        log.error("Failed to get form by id: {}", id, ex);
                        res.status(400);
                        res.body(String.format("Failed to get form by id: %d. %s", id, ex.getMessage()));
                    } catch (Exception ex) {
                        log.error("An unexpected error occurred while trying to get form by id: {}", id, ex);
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
                        log.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        log.error("Failed to get form by user id: {}", userId, ex);
                        res.status(400);
                        res.body(String.format("Failed to get form by user id: %d. %s", userId, ex.getMessage()));
                    } catch (Exception ex) {
                        log.error("An unexpected error occurred while trying to get form by user id: {}", userId, ex);
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
                        log.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        log.error("Failed to update form", ex);
                        res.status(400);
                        res.body(String.format("Failed to update form. %s", ex.getMessage()));
                    } catch (Exception ex) {
                        log.error("An unexpected error occurred while trying to update form", ex);
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
                        log.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        log.error("Failed to delete form by id: {}", id, ex);
                        res.status(400);
                        res.body(String.format("Failed to delete form by id: %d. %s", id, ex.getMessage()));
                    } catch (Exception ex) {
                        log.error("An unexpected error occurred while trying to delete form by id: {}", id, ex);
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
                        log.error("Credentials are invalid");
                    } catch (MatchaException ex) {
                        log.error("Failed to delete form by user id: {}", userId, ex);
                        res.status(400);
                        res.body(String.format("Failed to delete form by user id: %d. %s", userId, ex.getMessage()));
                    } catch (Exception ex) {
                        log.error("An unexpected error occurred while trying to delete form by user id: {}", userId, ex);
                        res.status(500);
                        res.body(String.format("An unexpected error occurred while trying to delete form by user id: %d. %s", userId, ex.getMessage()));
                    }
                    return res.body();
                }, new JsonTransformer());
            });
        });
    }

    private static void enableCORS() {
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "content-type, x-auth-token");
            response.type("application/json");
        });
    }

}
