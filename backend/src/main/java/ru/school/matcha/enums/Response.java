package ru.school.matcha.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Response {
    POST(org.eclipse.jetty.server.Response.SC_CREATED, null),
    GET(org.eclipse.jetty.server.Response.SC_OK, null),
    PUT(org.eclipse.jetty.server.Response.SC_NO_CONTENT, null),
    DELETE(org.eclipse.jetty.server.Response.SC_NO_CONTENT, null),
    AUTHENTICATION_EXCEPTION(org.eclipse.jetty.server.Response.SC_UNAUTHORIZED, "Authentication is failed"),
    NUMBER_FORMAT_EXCEPTION(org.eclipse.jetty.server.Response.SC_BAD_REQUEST, "Invalid number is passed"),
    NOT_FOUND_EXCEPTION(org.eclipse.jetty.server.Response.SC_NOT_FOUND, "Resource not found"),
    MATCHA_EXCEPTION(org.eclipse.jetty.server.Response.SC_BAD_REQUEST, "Bad request"),
    MAIL_EXCEPTION(org.eclipse.jetty.server.Response.SC_BAD_REQUEST, null),
    EXCEPTION(org.eclipse.jetty.server.Response.SC_INTERNAL_SERVER_ERROR, "An unexpected error on server");

    private final int status;
    private final String body;
}
