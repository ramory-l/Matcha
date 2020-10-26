package ru.school.matcha;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.controllers.AuthenticateController;
import ru.school.matcha.controllers.AuthorizationController;
import ru.school.matcha.controllers.FormController;
import ru.school.matcha.controllers.UserController;
import ru.school.matcha.converters.*;

import static spark.Spark.*;

@Slf4j
public class MatchaApplication {

    public static void main(String[] args) {
        port(8080);
        enableCORS();
        path("/api", () -> {
            path("/auth", () ->
                    post("/login", AuthenticateController.authenticate, new JsonTransformer()));
            post("/user", UserController.createUser, new JsonTransformer());
            path("/user", () -> {
                before("*", AuthorizationController.authorize);
                post("/batch", UserController.batchUsersCreate, new JsonTransformer());
                get("/all", UserController.getAllUsers, new JsonTransformer());
                get("/:id", UserController.getUserById, new JsonTransformer());
                get("/username/:username", UserController.getUserByUsername, new JsonTransformer());
                put("/", UserController.updateUser, new JsonTransformer());
                delete("/:id", UserController.deleteUserById, new JsonTransformer());
                delete("/username/:username", UserController.deleteUserByUsername, new JsonTransformer());
                path("/like", () -> {
                    post("/from/:from/to/:to", UserController.like, new JsonTransformer());
                    get("/:id", UserController.getLikes, new JsonTransformer());
                    delete("/from/:from/to/:to", UserController.deleteLike, new JsonTransformer());
                });
            });
            path("/form", () -> {
                before("*", AuthorizationController.authorize);
                post("*", FormController.createForm, new JsonTransformer());
                get("/all", FormController.getAllForms, new JsonTransformer());
                get("/:id", FormController.getFormById, new JsonTransformer());
                get("/user/:id", FormController.getFormByUserId, new JsonTransformer());
                put("/{userId}", FormController.updateFormByUserId, new JsonTransformer());
                delete("/:id", FormController.deleteFormById, new JsonTransformer());
                delete("/user/:id", FormController.deleteFormByUserId, new JsonTransformer());
            });
        });
    }

    private static void enableCORS() {
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
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
