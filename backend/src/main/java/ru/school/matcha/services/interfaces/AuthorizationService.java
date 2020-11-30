package ru.school.matcha.services.interfaces;

import ru.school.matcha.exceptions.JwtAuthenticationException;
import ru.school.matcha.enums.Role;

public interface AuthorizationService {

    Role authorize(String token) throws JwtAuthenticationException;

}
