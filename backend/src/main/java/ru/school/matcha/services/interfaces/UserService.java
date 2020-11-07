package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

    void createUser(User user);

    void batchCreateUsers(List<User> users);

    void updateUser(User user);

    void deleteUserById(Long id);

    void deleteUserByUsername(String username);

    String getUserEncryptPasswordById(Long id);

}
