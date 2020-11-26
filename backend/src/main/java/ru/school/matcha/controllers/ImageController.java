package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.converters.ImageConverter;
import ru.school.matcha.domain.Image;
import ru.school.matcha.dto.ImageBase64Dto;
import ru.school.matcha.dto.ImageDto;
import ru.school.matcha.enums.Location;
import ru.school.matcha.enums.Response;
import ru.school.matcha.enums.Role;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.ImageServiceImpl;
import ru.school.matcha.services.interfaces.ImageService;
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
        AuthorizationController.authorize(request, Role.USER);
        ImageBase64Dto imageBase64Dto = imageBase64DtoSerializer.deserialize(request.body(), ImageBase64Dto.class);
        Long imageId = imageService.createImage(imageBase64Dto.getBase64(), imageBase64Dto.getName() + ".jpg", imageBase64Dto.getUserId());
        response.header(Location.HEADER, Location.IMAGES.getUrl() + imageId);
        response.status(Response.POST.getStatus());
        return "";
    };

    public static Route getImageById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        AuthorizationController.authorize(request, Role.USER);
        response.status(Response.GET.getStatus());
        return imageConverter.convertFromEntity(imageService.getImageById(id));
    };

    public static Route getImagesByUserId = (request, response) -> {
        Long userId = parseLong(request.params("id"));
        AuthorizationController.authorize(request, Role.USER);
        response.status(Response.GET.getStatus());
        return imageConverter.createFromEntities(imageService.getImagesByUserId(userId));
    };

    public static Route deleteImageById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        AuthorizationController.authorize(request, Role.USER);
        imageService.deleteImageById(id);
        response.status(Response.DELETE.getStatus());
        return "";
    };

}
