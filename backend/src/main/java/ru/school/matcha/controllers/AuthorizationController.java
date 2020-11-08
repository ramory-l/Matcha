package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.exceptions.JwtAuthenticationException;
import ru.school.matcha.security.enums.Role;
import ru.school.matcha.services.AuthorizationServiceImpl;
import ru.school.matcha.services.interfaces.AuthorizationService;
import spark.Request;

import static java.util.Objects.isNull;
import static spark.Spark.halt;

@Slf4j
public class AuthorizationController {

    private static final AuthorizationService authorizationService;

    static {
        authorizationService = new AuthorizationServiceImpl();
    }

    public static void authorize(Request request, Role role) {
        Role userRole = null;
        try {
            userRole = authorizationService.authorize(request.headers("x-auth-token"));
        } catch (JwtAuthenticationException ex) {
            log.error("Credentials are invalid");
        }
        if (isNull(userRole)) {
            halt(401, "Credentials are invalid");
        } else if (!userRole.equals(role) && !userRole.equals(Role.ADMIN)) {
            halt(403, "Access is denied");
        }
    }

}
