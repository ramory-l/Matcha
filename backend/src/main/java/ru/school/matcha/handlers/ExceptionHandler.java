package ru.school.matcha.handlers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.enums.Response;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.exceptions.NotFoundException;
import spark.HaltException;

import javax.naming.AuthenticationException;

import static spark.Spark.exception;

@Slf4j
public class ExceptionHandler {

    public static void enable() {
        exception(NotFoundException.class, notFound);
        exception(HaltException.class, halt);
        exception(MatchaException.class, matcha);
        exception(NumberFormatException.class, numberFormat);
        exception(AuthenticationException.class, authentication);
        exception(Exception.class, exception);
    }

    public static spark.ExceptionHandler<AuthenticationException> authentication = (ex, request, response) -> {
        response.status(Response.AUTHENTICATION_EXCEPTION.getStatus());
        response.body(Response.AUTHENTICATION_EXCEPTION.getBody());
    };

    public static spark.ExceptionHandler<HaltException> halt = (ex, request, response) -> {
        response.status(ex.statusCode());
        response.body(ex.body());
    };

    public static spark.ExceptionHandler<NotFoundException> notFound = (ex, request, response) -> {
        response.status(Response.NOT_FOUND_EXCEPTION.getStatus());
        response.body(Response.NOT_FOUND_EXCEPTION.getBody());
    };

    public static spark.ExceptionHandler<MatchaException> matcha = (ex, request, response) -> {

        log.error(ex.getMessage());
        response.status(Response.MATCHA_EXCEPTION.getStatus());
        response.body(ex.getMessage());
    };

    public static spark.ExceptionHandler<NumberFormatException> numberFormat = (ex, request, response) -> {

        response.status(Response.NUMBER_FORMAT_EXCEPTION.getStatus());
        response.body(Response.NUMBER_FORMAT_EXCEPTION.getBody());
    };

    public static spark.ExceptionHandler<Exception> exception = (ex, request, response) -> {
        response.status(Response.EXCEPTION.getStatus());
        response.body(Response.EXCEPTION.getBody());
    };

}
