package ru.school.matcha.services.interfaces;

import javax.naming.AuthenticationException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface AuthenticationService {

    String authenticate(String username, String password) throws AuthenticationException, InvalidKeySpecException, NoSuchAlgorithmException;

}
