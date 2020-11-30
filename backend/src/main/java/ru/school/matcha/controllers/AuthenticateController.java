package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.AuthConverter;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.domain.Auth;
import ru.school.matcha.dto.AuthDto;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.AuthenticationServiceImpl;
import ru.school.matcha.services.interfaces.AuthenticationService;
import spark.Route;

@Slf4j
public class AuthenticateController {

    private static final Converter<AuthDto, Auth> authConverter;

    private static final AuthenticationService authenticationService;

    private static final Serializer<AuthDto> authDtoSerializer;

    static {
        authConverter = new AuthConverter();
        authenticationService = new AuthenticationServiceImpl();
        authDtoSerializer = new Serializer<>();
    }

    public static Route authenticate = (request, response) -> {
        AuthDto authDto = authDtoSerializer.deserialize(request.body(), AuthDto.class);
        Auth authData = authConverter.convertFromDto(authDto);
        return authenticationService.authenticate(authData.getUsername(), authData.getPassword());
    };

}
