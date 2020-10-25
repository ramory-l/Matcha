package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.domain.User;
import ru.school.matcha.security.PasswordCipher;
import ru.school.matcha.security.jwt.JwtTokenProvider;
import ru.school.matcha.services.interfaces.AuthenticationService;
import ru.school.matcha.services.interfaces.UserService;

import javax.naming.AuthenticationException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public String authenticate(String username, String password) throws AuthenticationException, InvalidKeySpecException, NoSuchAlgorithmException {
        UserService userService = new UserServiceImpl();
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new AuthenticationException(String.format("User with username: %s not found", username)));
        String encryptedPassword = userService.getUserEncryptPasswordById(user.getId());
        if (!PasswordCipher.validatePassword(password, encryptedPassword)) {
            throw new AuthenticationException("Users password is wrong");
        }
        return jwtTokenProvider.createToken(user.getId(), user.getUsername());
    }

}
