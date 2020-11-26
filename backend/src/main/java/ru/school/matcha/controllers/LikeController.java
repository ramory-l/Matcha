package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.enums.Location;
import ru.school.matcha.enums.Response;
import ru.school.matcha.enums.Role;
import ru.school.matcha.services.LikeServiceImpl;
import ru.school.matcha.services.interfaces.LikeService;
import spark.Route;

import java.util.List;
import java.util.Map;

import static java.lang.Long.parseLong;

@Slf4j
public class LikeController {

    private static final LikeService likeService;

    static {
        likeService = new LikeServiceImpl();
    }

    public static Route getLikes = (request, response) -> {
        Long id = parseLong(request.params("id"));
        Boolean outgoing = Boolean.parseBoolean(request.queryParams("outgoing"));
        AuthorizationController.authorize(request, Role.USER);
        List<Long> likes = likeService.getLikesByUserId(id, true, outgoing);
        response.status(Response.GET.getStatus());
        return likes;
    };

    public static Route getDislikes = (request, response) -> {
        Long id = parseLong(request.params("id"));
        Boolean outgoing = Boolean.parseBoolean(request.queryParams("outgoing"));
        AuthorizationController.authorize(request, Role.USER);
        List<Long> likes = likeService.getLikesByUserId(id, false, outgoing);
        response.status(Response.GET.getStatus());
        return likes;
    };

    public static Route getAllLikesAndDislikes = (request, response) -> {
        Long id = parseLong(request.params("id"));
        Boolean outgoing = Boolean.parseBoolean(request.queryParams("outgoing"));
        AuthorizationController.authorize(request, Role.USER);
        Map<String, List<Long>> likes = likeService.getLikesByUserId(id, outgoing);
        response.status(Response.GET.getStatus());
        return likes;
    };

    public static Route createLike = (request, response) -> {
        Long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        AuthorizationController.authorize(request, Role.USER);
        likeService.like(from, to, true);
        response.header(Location.HEADER, Location.LIKES.getUrl() + to + "/likes");
        response.status(Response.POST.getStatus());
        return "";
    };

    public static Route createDislike = (request, response) -> {
        Long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        AuthorizationController.authorize(request, Role.USER);
        likeService.like(from, to, false);
        response.header(Location.HEADER, Location.DISLIKES.getUrl() + to + "/dislikes");
        response.status(Response.POST.getStatus());
        return "";
    };

    public static Route deleteLike = (request, response) -> {
        Long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        AuthorizationController.authorize(request, Role.USER);
        likeService.deleteLike(from, to, true);
        response.status(Response.DELETE.getStatus());
        return "";
    };

    public static Route deleteDislike = (request, response) -> {
        Long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        AuthorizationController.authorize(request, Role.USER);
        likeService.deleteLike(from, to, false);
        response.status(Response.DELETE.getStatus());
        return "";
    };

}
