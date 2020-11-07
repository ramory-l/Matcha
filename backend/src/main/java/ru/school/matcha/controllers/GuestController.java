package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.services.GuestServiceImpl;
import ru.school.matcha.services.interfaces.GuestService;
import spark.HaltException;
import spark.Route;

import static java.lang.Long.parseLong;

@Slf4j
public class GuestController {

    private final static GuestService guestService;

    static {
        guestService = new GuestServiceImpl();
    }

    public static Route createGuest = (request, response) -> {
        Long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        try {
            AuthorizationController.authorize(request);
            response.status(204);
            guestService.createGuest(to, from);
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to create guest (from: {} to: {})", from, to, ex);
            response.status(400);
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to create guest (from: {} to: {})", from, to, ex);
            response.status(500);
        }
        return response.body();
    };

    public static Route getGuestsByUserId = (request, response) -> {
        Long userId = parseLong(request.params("id"));
        try {
            AuthorizationController.authorize(request);
            response.status(200);
            return guestService.getGuestsByUserId(userId);
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to get guests by user with id: {}", username, ex);
            response.status(400);
            response.body(String.format("Failed to get user by username: %s. %s", username, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to get user by username: {}", username, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to get user by username: %s. %s", username, ex.getMessage()));
        }
        return response.body();
    };

    public static Route deleteGuest = (request, response) -> {
        Long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        try {
            AuthorizationController.authorize(request);
            response.status(200);
            return result;
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to delete guest (from: {} to: {})", from, to, ex);
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
