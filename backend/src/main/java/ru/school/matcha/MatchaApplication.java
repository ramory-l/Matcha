package ru.school.matcha;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.controllers.*;
import ru.school.matcha.converters.*;
import ru.school.matcha.handlers.ChatWebSocketHandler;
import ru.school.matcha.utils.GoogleDrive;

import static spark.Spark.*;

@Slf4j
public class MatchaApplication {

    public static void main(String[] args) {
        port(8080);
        webSocket("/socket", ChatWebSocketHandler.class);
        enableCORS();
        GoogleDrive.run();
        path("/api", () -> {
            path("/auth", () ->
                    post("/login", AuthenticateController.authenticate, new JsonTransformer()));
            post("/users", UserController.createUser, new JsonTransformer());
            path("/users", () -> {
                post("/batch", UserController.batchUsersCreate, new JsonTransformer());
                get("/", UserController.getAllUsers, new JsonTransformer());
                get("/:id", UserController.getUserById, new JsonTransformer());
                get("/username/:username", UserController.getUserByUsername, new JsonTransformer());
                get("/:id/dislikes", LikeController.getDislikes, new JsonTransformer());
                get("/:id/likes", LikeController.getLikes, new JsonTransformer());
                get("/:id/likesDislikes", LikeController.getAllLikesAndDislikes, new JsonTransformer());
                get("/:id/guests", GuestController.getGuestsByUserId, new JsonTransformer());
                get("/:id/images", ImageController.getImagesByUserId, new JsonTransformer());
                get("/:id/tags", TagController.getTagsByUserId, new JsonTransformer());
                put("/", UserController.updateUser, new JsonTransformer());
                delete("/:id", UserController.deleteUserById, new JsonTransformer());
                delete("/username/:username", UserController.deleteUserByUsername, new JsonTransformer());
                path("/likes", () -> {
                    post("/from/:from/to/:to", LikeController.like, new JsonTransformer());
                    delete("/from/:from/to/:to", LikeController.deleteLike, new JsonTransformer());
                });
                path("/dislikes", () -> {
                    post("/from/:from/to/:to", LikeController.dislike, new JsonTransformer());
                    delete("/from/:from/to/:to", LikeController.deleteDislike, new JsonTransformer());
                });
                path("/tags", () -> {
                    post("/", TagController.createTag, new JsonTransformer());
                    get("/", TagController.getTags, new JsonTransformer());
                    delete("/:id", TagController.deleteTag, new JsonTransformer());
                });
                path("/guests", () -> {
                    post("/from/:from/to/:to", GuestController.createGuest, new JsonTransformer());
                    delete("/from/:from/to/:to", GuestController.deleteGuest, new JsonTransformer());
                });
                path("/images", () -> {
                    post("/", ImageController.createImage, new JsonTransformer());
                    get("/:id", ImageController.getImageById, new JsonTransformer());
                    delete("/:id", ImageController.deleteImageById, new JsonTransformer());
                });
            });
            path("/forms", () -> {
                post("/", FormController.createForm, new JsonTransformer());
                get("/all", FormController.getAllForms, new JsonTransformer());
                get("/:id", FormController.getFormById, new JsonTransformer());
                put("/", FormController.updateForm, new JsonTransformer());
                delete("/:id", FormController.deleteFormById, new JsonTransformer());
            });
        });
    }

    private static void enableCORS() {
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.type("application/json");
        });
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", "Content-Type, x-auth-token");
            }
            response.header("Access-Control-Expose-Headers", "Content-Type");
            response.header("Access-Control-Max-Age", "86400");
            return "OK";
        });
    }

}
