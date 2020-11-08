package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.converters.TagConverter;
import ru.school.matcha.domain.Tag;
import ru.school.matcha.dto.TagDto;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.security.enums.Role;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.TagServiceImpl;
import ru.school.matcha.services.interfaces.TagService;
import spark.HaltException;
import spark.Route;

import static java.lang.Long.parseLong;

@Slf4j
public class TagController {

    private final static Converter<TagDto, Tag> tagConverter;

    private final static TagService tagService;

    private final static Serializer<TagDto> tagDtoSerializer;

    static {
        tagConverter = new TagConverter();
        tagService = new TagServiceImpl();
        tagDtoSerializer = new Serializer<>();
    }

    public static Route createTag = (request, response) -> {
        try {
            AuthorizationController.authorize(request, Role.USER);
            TagDto tagDto = tagDtoSerializer.deserialize(request.body(), TagDto.class);
            Tag tag = tagConverter.convertFromDto(tagDto);
            tagService.createTag(tag);
            response.status(204);
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to create tag", ex);
            response.status(400);
            response.body(String.format("Failed to create tag. %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to create tag", ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to create tag. %s", ex.getMessage()));
        }
        return response.body();
    };

    public static Route getTags = (request, response) -> {
        try {
            AuthorizationController.authorize(request, Role.USER);
            response.status(200);
            return tagConverter.createFromEntities(tagService.getTags());
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to get tags", ex);
            response.status(400);
            response.body(String.format("Failed to get tags. %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to tags", ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to get tags. %s", ex.getMessage()));
        }
        return response.body();
    };

    public static Route getTagsByUserId = (request, response) -> {
        Long userId = parseLong(request.params("id"));
        try {
            AuthorizationController.authorize(request, Role.USER);
            response.status(200);
            return tagConverter.createFromEntities(tagService.getTagsByUserId(userId));
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to get tags by user with id: {}", userId, ex);
            response.status(400);
            response.body(String.format("Failed to get tags by user with id: %d. %s", userId, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to get tags by user with id: {}", userId, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to get tags by user with id: %d. %s", userId, ex.getMessage()));
        }
        return response.body();
    };

    public static Route deleteTagById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        try {
            AuthorizationController.authorize(request, Role.USER);
            tagService.deleteTagById(id);
            response.status(204);
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to delete tag with id: {}", id, ex);
            response.status(400);
            response.body(String.format("Failed to delete tag with id: %d. %s", id, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to delete tag with id: {}", id, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to delete tag with id: %d. %s", id, ex.getMessage()));
        }
        return response.body();
    };

}
