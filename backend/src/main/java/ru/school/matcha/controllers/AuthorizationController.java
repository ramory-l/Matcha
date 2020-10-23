package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.exceptions.JwtAuthenticationException;
import ru.school.matcha.services.AuthorizationServiceImpl;
import ru.school.matcha.services.interfaces.AuthorizationService;
import spark.Filter;

import static spark.Spark.halt;

@Slf4j
public class AuthorizationController {

    private static final AuthorizationService authorizationService;

    static {
        authorizationService = new AuthorizationServiceImpl();
    }

    public static Filter authorize = (request, response) -> {
        boolean authorization = false;
        try {
            authorization = authorizationService.authorize(request.headers("x-auth-token"));
        } catch (JwtAuthenticationException ex) {
            log.error("Credentials are invalid");
        }
        if (!authorization) {
            halt(401, "Credentials are invalid");
        }
    };

}
