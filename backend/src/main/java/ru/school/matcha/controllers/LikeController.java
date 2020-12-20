package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.converters.LikeConverter;
import ru.school.matcha.domain.Like;
import ru.school.matcha.dto.LikeDto;
import ru.school.matcha.enums.Location;
import ru.school.matcha.enums.Response;
import ru.school.matcha.enums.Role;
import ru.school.matcha.services.LikeServiceImpl;
import ru.school.matcha.services.interfaces.LikeService;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.parseLong;
import static spark.Spark.halt;

@Slf4j
public class LikeController {

    private static final Converter<LikeDto, Like> likeConverter = new LikeConverter();

    private static final LikeService likeService = new LikeServiceImpl();

    public static Route getLikes = (request, response) -> {
        Long id = parseLong(request.params("id"));
        Boolean outgoing = Boolean.parseBoolean(request.queryParams("outgoing"));
        AuthorizationController.authorize(request, Role.USER);
        List<LikeDto> likesDto = likeConverter.createFromEntities(likeService.getLikesByUserId(id, true, outgoing));
        response.status(Response.GET.getStatus());
        return likesDto;
    };

    public static Route getDislikes = (request, response) -> {
        Long id = parseLong(request.params("id"));
        Boolean outgoing = Boolean.parseBoolean(request.queryParams("outgoing"));
        AuthorizationController.authorize(request, Role.USER);
        List<LikeDto> dislikesDto = likeConverter.createFromEntities(likeService.getLikesByUserId(id, false, outgoing));
        response.status(Response.GET.getStatus());
        return dislikesDto;
    };

    public static Route getAllLikesAndDislikes = (request, response) -> {
        Long id = parseLong(request.params("id"));
        Boolean outgoing = Boolean.parseBoolean(request.queryParams("outgoing"));
        AuthorizationController.authorize(request, Role.USER);
        Map<String, List<Like>> likes = likeService.getLikesByUserId(id, outgoing);
        Map<String, List<LikeDto>> allLikesAndDislikesDto = new HashMap<>();
        allLikesAndDislikesDto.put("likes", likeConverter.createFromEntities(likes.get("like")));
        allLikesAndDislikesDto.put("dislikes", likeConverter.createFromEntities(likes.get("dislike")));
        response.status(Response.GET.getStatus());
        return allLikesAndDislikesDto;
    };

    public static Route createLike = (request, response) -> {
        long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        Long userId = AuthorizationController.authorize(request, Role.USER);
        if (userId != 0 && to != userId) {
            halt(403, "Access is denied");
        }
        likeService.like(from, to, true);
        response.header(Location.HEADER, Location.LIKES.getUrl() + to + "/likes");
        response.status(Response.POST.getStatus());
        return "";
    };

    public static Route createDislike = (request, response) -> {
        long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        Long userId = AuthorizationController.authorize(request, Role.USER);
        if (userId != 0 && to != userId) {
            halt(403, "Access is denied");
        }
        likeService.like(from, to, false);
        response.header(Location.HEADER, Location.DISLIKES.getUrl() + to + "/dislikes");
        response.status(Response.POST.getStatus());
        return "";
    };

    public static Route deleteLike = (request, response) -> {
        long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        Long userId = AuthorizationController.authorize(request, Role.USER);
        if (userId != 0 && to != userId) {
            halt(403, "Access is denied");
        }
        likeService.deleteLike(from, to, true);
        response.status(Response.DELETE.getStatus());
        return "";
    };

    public static Route deleteDislike = (request, response) -> {
        long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        Long userId = AuthorizationController.authorize(request, Role.USER);
        if (userId != 0 && to != userId) {
            halt(403, "Access is denied");
        }
        likeService.deleteLike(from, to, false);
        response.status(Response.DELETE.getStatus());
        return "";
    };

}
