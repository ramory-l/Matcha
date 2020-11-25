package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.converters.FormConverter;
import ru.school.matcha.domain.Form;
import ru.school.matcha.dto.FormDto;
import ru.school.matcha.enums.Location;
import ru.school.matcha.enums.Response;
import ru.school.matcha.enums.Role;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.FormServiceImpl;
import ru.school.matcha.services.interfaces.FormService;
import spark.Route;

import java.util.List;

import static java.lang.Long.parseLong;

@Slf4j
public class FormController {

    private static final Converter<FormDto, Form> formConverter;

    private static final FormService formService;

    private static final Serializer<FormDto> serializerFormDto;

    static {
        formService = new FormServiceImpl();
        formConverter = new FormConverter();
        serializerFormDto = new Serializer<>();
    }

    public static Route createForm = (request, response) -> {
        AuthorizationController.authorize(request, Role.USER);
        FormDto formDto = serializerFormDto.deserialize(request.body(), FormDto.class);
        Form form = formConverter.convertFromDto(formDto);
        Form result = formService.createForm(form);
        response.header(Location.HEADER, Location.FORMS.getUrl() + result.getId());
        response.status(Response.GET.getStatus());
        return response.body();
    };

    public static Route getAllForms = (request, response) -> {
        AuthorizationController.authorize(request, Role.ADMIN);
        List<Form> forms = formService.getAllForms();
        List<FormDto> result = formConverter.createFromEntities(forms);
        response.status(Response.GET.getStatus());
        return result;
    };

    public static Route getFormById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        AuthorizationController.authorize(request, Role.USER);
        Form form = formService.getFormById(id);
        FormDto result = formConverter.convertFromEntity(form);
        response.status(Response.GET.getStatus());
        return result;
    };

    public static Route updateForm = (request, response) -> {
        AuthorizationController.authorize(request, Role.USER);
        FormDto formDto = serializerFormDto.deserialize(request.body(), FormDto.class);
        Form form = formConverter.convertFromDto(formDto);
        formService.updateForm(form);
        response.status(Response.PUT.getStatus());
        return response.body();
    };

    public static Route deleteFormById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        AuthorizationController.authorize(request, Role.ADMIN);
        formService.deleteFormById(id);
        response.status(Response.DELETE.getStatus());
        return response.body();
    };

}
