package ru.school.matcha.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Cors {
    ALLOW_ORIGIN("Access-Control-Allow-Origin", "*"),
    ALLOW_METHODS("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"),
    ALLOW_HEADERS("Access-Control-Allow-Headers", "Content-Type, x-auth-token"),
    EXPOSE_HEADERS("Access-Control-Expose-Headers", "Content-Type"),
    MAX_AGE("Access-Control-Max-Age", "86400"),
    REQUEST_HEADERS("Access-Control-Request-Headers", null),
    TYPE(null ,"application/json"),
    RESULT(null, "OK");

    private final String header;
    private final String content;
}
