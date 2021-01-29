package ru.school.matcha.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Path {
    SOCKET("/socket"),
    API("/api"),
    AUTH("/auth"),
    USERS("/users"),
    LIKES("/likes"),
    DISLIKES("/dislikes"),
    TAGS("/tags"),
    GUESTS("/guests"),
    IMAGES("/images"),
    FORMS("/forms");

    private final String url;
}