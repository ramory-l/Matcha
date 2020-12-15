package ru.school.matcha.handlers;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.school.matcha.exceptions.JwtAuthenticationException;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.security.jwt.JwtTokenProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

@Slf4j
@WebSocket
public class ChatWebSocketHandler {

    static Map<Session, String> sessionUsernameMap = new ConcurrentHashMap<>();

    @OnWebSocketConnect
    public void onConnect(Session session) {
        try {
            if (isNull(session)) {
                throw new MatchaException("Session not transferred");
            }
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
            String token = jwtTokenProvider.resolveToken(session.getUpgradeRequest().getParameterMap().get("token").get(0));
            if (!jwtTokenProvider.validateToken(token)) {
                throw new JwtAuthenticationException("Credential are invalid");
            }
            String username = jwtTokenProvider.getUsernameFromToken(token);
            sessionUsernameMap.put(session, username);
        } catch (JwtAuthenticationException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void sendMessage(String sender, String message) {
        sessionUsernameMap.keySet().stream().parallel().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(sender + ": " + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        sessionUsernameMap.remove(user);
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        sendMessage(sessionUsernameMap.get(user), message);
    }

    public static boolean checkUserOnline(String login) {
        return sessionUsernameMap.values().stream().parallel().anyMatch(username -> username.equals(login));
    }

}
