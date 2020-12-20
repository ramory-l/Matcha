package ru.school.matcha.handlers;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.converters.MessageConverter;
import ru.school.matcha.domain.Message;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.MessageDto;
import ru.school.matcha.exceptions.JwtAuthenticationException;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.security.jwt.JwtTokenProvider;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.MessageServiceImpl;
import ru.school.matcha.services.UserServiceImpl;
import ru.school.matcha.services.interfaces.MessageService;
import ru.school.matcha.services.interfaces.UserService;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

@Slf4j
@WebSocket
public class ChatWebSocketHandler {

    private final static Converter<MessageDto, Message> messageConverter = new MessageConverter();

    private final static UserService userService = new UserServiceImpl();
    private final static MessageService messageService = new MessageServiceImpl();

    private final static Serializer<MessageDto> messageDtoSerializer = new Serializer<>();

    private final static Map<Session, User> sessionUsernameMap = new ConcurrentHashMap<>();

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
            User user = userService.getUserByUsername(username);
            sessionUsernameMap.put(session, user);
        } catch (JwtAuthenticationException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private static void sendMessage(User sender, Message message) {
        sessionUsernameMap
                .entrySet()
                .stream()
                .parallel()
                .filter(elem -> elem.getValue().getId().equals(message.getTo()))
                .forEach(elem -> {
                    try {
                        elem.getKey().getRemote().sendString(sender.getUsername() + ": " + message);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                });
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        sessionUsernameMap.remove(user);
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String json) {
        try {
            MessageDto messageDto = messageDtoSerializer.deserialize(json, MessageDto.class);
            if (isNull(messageDto.getMessage()) || isNull(messageDto.getCreateTs())) {
                throw new MatchaException("Invalid Message");
            }
            Message message = messageConverter.convertFromDto(messageDto);
            checkUserOnline(message);
            if ("message".equals(message.getType())) {
                messageService.saveMessage(message);
            }
            sendMessage(sessionUsernameMap.get(user), message);
        } catch (IOException ex) {
            log.error("Invalid message");
        } catch (MatchaException ex) {
            log.error(ex.getMessage());
        }
    }

    private static void checkUserOnline(Message message) {
        if (sessionUsernameMap
                .values()
                .stream()
                .parallel()
                .noneMatch(user -> user.getId().equals(message.getTo()))) {
            message.setMessage("User is offline");
            message.setType("notification");
        }
    }

}
