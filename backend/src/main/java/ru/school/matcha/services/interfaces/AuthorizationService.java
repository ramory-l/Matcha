package ru.school.matcha.services.interfaces;

import ru.school.matcha.exceptions.JwtAuthenticationException;

public interface AuthorizationService {

    boolean authorize(String token) throws JwtAuthenticationException;

}
