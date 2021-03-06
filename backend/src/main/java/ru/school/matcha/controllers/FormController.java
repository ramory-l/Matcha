package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.converters.FormConverter;
import ru.school.matcha.domain.Form;
import ru.school.matcha.dto.FormDto;
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

    private static final Converter<FormDto, Form> formConverter = new FormConverter();

    private static final FormService formService = new FormServiceImpl();

    private static final Serializer<FormDto> serializerFormDto = new Serializer<>();

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
        return "";
    };

    public static Route deleteFormById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        AuthorizationController.authorize(request, Role.ADMIN);
        formService.deleteFormById(id);
        response.status(Response.DELETE.getStatus());
        return "";
    };

}
