package ru.school.matcha.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Location {

    USERS(String.format("%s%s/", Path.API.getUrl(), Path.USERS.getUrl())),
    LIKES(String.format("%s%s/", Path.API.getUrl(), Path.USERS.getUrl())),
    DISLIKES(String.format("%s%s/", Path.API.getUrl(), Path.USERS.getUrl())),
    TAGS(String.format("%s%s/", Path.API.getUrl(), Path.USERS.getUrl())),
    GUESTS(String.format("%s%s/", Path.API.getUrl(), Path.USERS.getUrl())),
    IMAGES(String.format("%s%s/", Path.API.getUrl(), Path.IMAGES.getUrl())),
    FORMS(String.format("%s%s/", Path.API.getUrl(), Path.FORMS.getUrl()));

    private final String url;

    public static final String HEADER = "Location";

}
