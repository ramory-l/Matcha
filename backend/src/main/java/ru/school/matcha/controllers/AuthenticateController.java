package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.AuthConverter;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.domain.Auth;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.AuthDto;
import ru.school.matcha.enums.Response;
import ru.school.matcha.enums.Role;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.AuthenticationServiceImpl;
import ru.school.matcha.services.UserServiceImpl;
import ru.school.matcha.services.interfaces.AuthenticationService;
import ru.school.matcha.services.interfaces.UserService;
import spark.Route;

import static spark.Spark.halt;

@Slf4j
public class AuthenticateController {

    private static final Converter<AuthDto, Auth> authConverter = new AuthConverter();

    private static final AuthenticationService authenticationService = new AuthenticationServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    private static final Serializer<AuthDto> authDtoSerializer = new Serializer<>();

    public static Route authenticate = (request, response) -> {
        AuthDto authDto = authDtoSerializer.deserialize(request.body(), AuthDto.class);
        Auth authData = authConverter.convertFromDto(authDto);
        return authenticationService.authenticate(authData.getUsername(), authData.getPassword());
    };

    public static Route checkPassword = (request, response) -> {
        Long userId = AuthorizationController.authorize(request, Role.USER);
        AuthDto authDto = authDtoSerializer.deserialize(request.body(), AuthDto.class);
        Auth authData = authConverter.convertFromDto(authDto);
        User user = userService.getUserByUsername(authData.getUsername());
        if (userId != 0 && !user.getId().equals(userId)) {
            halt(403, "Access is denied");
        }
        authenticationService.checkPassword(authData.getUsername(), authData.getPassword(), user);
        response.status(Response.GET.getStatus());
        return "OK";
    };

}
