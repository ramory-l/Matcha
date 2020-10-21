package ru.school.matcha.services.interfaces;

public interface AuthorizationService {

    boolean authorize(String token);

}
