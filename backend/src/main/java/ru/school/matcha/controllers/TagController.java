package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.security.enums.Role;
import ru.school.matcha.services.TagServiceImpl;
import ru.school.matcha.services.interfaces.TagService;
import spark.HaltException;
import spark.Route;

@Slf4j
public class TagController {

    private final static TagService tagService;

    static {
        tagService = new TagServiceImpl();
    }

    public static Route createTag = (request, response) -> {
        String username = request.params("username");
        try {
            AuthorizationController.authorize(request, Role.USER);
            response.status(200);
            return ;
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
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

    public static Route getTags = (request, response) -> {
        String username = request.params("username");
        try {
            AuthorizationController.authorize(request, Role.USER);
            response.status(200);
            return result;
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
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

    public static Route getTagsByUserId = (request, response) -> {
        String username = request.params("username");
        try {
            AuthorizationController.authorize(request, Role.USER);

            response.status(200);
            return result;
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
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

    public static Route deleteTag = (request, response) -> {
        String username = request.params("username");
        try {
            AuthorizationController.authorize(request, Role.USER);
            response.status(200);
            return result;
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
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

}
