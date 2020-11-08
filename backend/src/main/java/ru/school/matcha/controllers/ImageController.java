package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.converters.ImageConverter;
import ru.school.matcha.domain.Image;
import ru.school.matcha.dto.ImageBase64Dto;
import ru.school.matcha.dto.ImageDto;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.security.enums.Role;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.ImageServiceImpl;
import ru.school.matcha.services.interfaces.ImageService;
import spark.HaltException;
import spark.Route;

import static java.lang.Long.parseLong;

@Slf4j
public class ImageController {

    private final static Converter<ImageDto, Image> imageConverter;

    private final static ImageService imageService;

    private final static Serializer<ImageBase64Dto> imageBase64DtoSerializer;

    static {
        imageService = new ImageServiceImpl();
        imageConverter = new ImageConverter();
        imageBase64DtoSerializer = new Serializer<>();
    }

    public static Route createImage = (request, response) -> {
        try {
            AuthorizationController.authorize(request, Role.USER);
            ImageBase64Dto imageBase64Dto = imageBase64DtoSerializer.deserialize(request.body(), ImageBase64Dto.class);
            response.status(200);
            return imageService.createImage(imageBase64Dto.getBase64(), imageBase64Dto.getName() + ".jpg", imageBase64Dto.getUserId());
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to create image", ex);
            response.status(400);
            response.body(String.format("Failed to create image. %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to create image", ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to create image. %s", ex.getMessage()));
        }
        return response.body();
    };

    public static Route getImageById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        try {
            AuthorizationController.authorize(request, Role.USER);
            response.status(200);
            return imageConverter.convertFromEntity(imageService.getImageById(id));
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to get image by id: {}", id, ex);
            response.status(400);
            response.body(String.format("Failed to get image by id: %d. %s", id, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to get image by id: {}", id, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to get image by id: %d. %s", id, ex.getMessage()));
        }
        return response.body();
    };

    public static Route getImagesByUserId = (request, response) -> {
        Long userId = parseLong(request.params("id"));
        try {
            AuthorizationController.authorize(request, Role.USER);
            response.status(200);
            return imageConverter.createFromEntities(imageService.getImagesByUserId(userId));
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to get images by user with id: {}", userId, ex);
            response.status(400);
            response.body(String.format("Failed to get images by user with id: %d. %s", userId, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to get images by user with id: {}", userId, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to get images by user with id: %d. %s", userId, ex.getMessage()));
        }
        return response.body();
    };

    public static Route deleteImageById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        try {
            AuthorizationController.authorize(request, Role.USER);
            imageService.deleteImageById(id);
            response.status(204);
        } catch (HaltException ex) {
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to delete image by id: {}", id, ex);
            response.status(400);
            response.body(String.format("Failed to delete image by id: %d. %s", id, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to delete image by id: {}", id, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to delete image by id: %d. %s", id, ex.getMessage()));
        }
        return response.body();
    };

}
