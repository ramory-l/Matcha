package ru.school.matcha.daemons;

import lombok.SneakyThrows;
import org.eclipse.jetty.websocket.api.Session;
import ru.school.matcha.domain.User;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.handlers.WebSocketHandler;
import ru.school.matcha.services.UserServiceImpl;
import ru.school.matcha.services.interfaces.UserService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CheckUserOnlineDaemon implements Runnable {

    private final UserService userService = new UserServiceImpl();

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            try {
                Map<Session, User> sessionUsernameMap = WebSocketHandler.getSessionUsernameMap();
                List<Long> ids = sessionUsernameMap.values().stream().parallel().map(User::getId).collect(Collectors.toList());
                if (!ids.isEmpty()) {
                    userService.updateLastLoginDateUsers(ids);
                }
                TimeUnit.MILLISECONDS.sleep(900000);
            } catch (MatchaException ignored) {
            }
        }
    }

}
