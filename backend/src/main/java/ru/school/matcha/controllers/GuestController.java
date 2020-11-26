package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.converters.GuestConverter;
import ru.school.matcha.domain.Guest;
import ru.school.matcha.dto.GuestDto;
import ru.school.matcha.enums.Location;
import ru.school.matcha.enums.Response;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.enums.Role;
import ru.school.matcha.services.GuestServiceImpl;
import ru.school.matcha.services.interfaces.GuestService;
import spark.HaltException;
import spark.Route;

import static java.lang.Long.parseLong;

@Slf4j
public class GuestController {

    private final static Converter<GuestDto, Guest> guestConverter;

    private final static GuestService guestService;

    static {
        guestService = new GuestServiceImpl();
        guestConverter = new GuestConverter();
    }

    public static Route createGuest = (request, response) -> {
        Long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        AuthorizationController.authorize(request, Role.USER);
        guestService.createGuest(to, from);
        response.header(Location.HEADER, Location.GUESTS.getUrl() + to + "/guests");
        response.status(Response.POST.getStatus());
        return "";
    };

    public static Route getGuestsByUserId = (request, response) -> {
        Long userId = parseLong(request.params("id"));
        AuthorizationController.authorize(request, Role.USER);
        response.status(Response.GET.getStatus());
        return guestConverter.createFromEntities(guestService.getGuestsByUserId(userId));
    };

    public static Route deleteGuest = (request, response) -> {
        Long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        AuthorizationController.authorize(request, Role.ADMIN);
        guestService.deleteGuest(to, from);
        response.status(Response.DELETE.getStatus());
        return "";
    };

}
