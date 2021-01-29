package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.exceptions.JwtAuthenticationException;
import ru.school.matcha.enums.Role;
import ru.school.matcha.security.jwt.JwtTokenProvider;
import ru.school.matcha.services.interfaces.AuthorizationService;

import static java.util.Objects.isNull;

@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @Override
    public Role authorize(String token) {
        token = jwtTokenProvider.resolveToken(token);
        if (isNull(token)) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
        if (jwtTokenProvider.validateToken(token)) {
            return jwtTokenProvider.getRoleFromToken(token);
        } else {
            return null;
        }
    }

    @Override
    public Long getUserIdFromToken(String token) {
        return jwtTokenProvider.getIdFromToken(jwtTokenProvider.resolveToken(token));
    }

}
