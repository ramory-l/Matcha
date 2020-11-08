package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.AuthConverter;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.domain.Auth;
import ru.school.matcha.dto.AuthDto;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.AuthenticationServiceImpl;
import ru.school.matcha.services.interfaces.AuthenticationService;
import spark.Route;

import javax.naming.AuthenticationException;

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
        try {
            return authenticationService.authenticate(authData.getUsername(), authData.getPassword());
        } catch (AuthenticationException ex) {
            log.error("Failed to login", ex);
            response.status(403);
            response.body(String.format("Authentication failed. %s", ex.getMessage()));
        } catch (MatchaException ex) {
            log.error("Failed to login", ex);
            response.status(400);
            response.body(String.format("Failed to login. %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to login", ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to login. %s", ex.getMessage()));
        }
        return response.body();
    };

}
