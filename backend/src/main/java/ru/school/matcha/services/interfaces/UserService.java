package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.Credentials;
import ru.school.matcha.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);

    Long createUser(Credentials credentials);

    void updateUser(User user);

    void deleteUserById(Long id);

    void deleteUserByUsername(String username);

    String getUserEncryptPasswordById(Long id);

}
