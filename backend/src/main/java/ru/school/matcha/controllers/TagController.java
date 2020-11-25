package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.converters.TagConverter;
import ru.school.matcha.domain.Tag;
import ru.school.matcha.dto.TagDto;
import ru.school.matcha.enums.Location;
import ru.school.matcha.enums.Response;
import ru.school.matcha.enums.Role;
import ru.school.matcha.services.TagServiceImpl;
import ru.school.matcha.services.interfaces.TagService;
import spark.Route;

import static java.lang.Long.parseLong;

@Slf4j
public class TagController {

    private final static Converter<TagDto, Tag> tagConverter;

    private final static TagService tagService;

    static {
        tagConverter = new TagConverter();
        tagService = new TagServiceImpl();
    }

    public static Route createTag = (request, response) -> {
        String tagName = request.params("tagName");
        Long userId = parseLong(request.params("userId"));
        AuthorizationController.authorize(request, Role.USER);
        tagService.createUserRefTag(tagName, userId);
        response.header(Location.HEADER, Location.TAGS.getUrl() + userId + "/tags");
        response.status(Response.POST.getStatus());
        return response.body();
    };

    public static Route getTags = (request, response) -> {
        AuthorizationController.authorize(request, Role.USER);
        response.status(Response.GET.getStatus());
        return tagConverter.createFromEntities(tagService.getTags());
    };

    public static Route getTagsByUserId = (request, response) -> {
        Long userId = parseLong(request.params("id"));
        AuthorizationController.authorize(request, Role.USER);
        response.status(Response.GET.getStatus());
        return tagConverter.createFromEntities(tagService.getTagsByUserId(userId));
    };

    public static Route deleteTagById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        AuthorizationController.authorize(request, Role.USER);
        tagService.deleteTagById(id);
        response.status(Response.DELETE.getStatus());
        return response.body();
    };

    public static Route deleteTag = (request, response) -> {
        String tagName = request.params("tagName");
        Long userId = parseLong(request.params("userId"));
        AuthorizationController.authorize(request, Role.USER);
        tagService.deleteUserRefTag(tagName, userId);
        response.status(Response.DELETE.getStatus());
        return response.body();
    };

}
