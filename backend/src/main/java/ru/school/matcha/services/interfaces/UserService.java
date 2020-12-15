package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    User getUserByUsername(String username);

    List<User> getUsersByTagId(Long tagId);

    Long createUser(User user);

    void batchCreateUsers(List<User> users);

    void updateUser(User user);

    void updatePassword(String hash);

    void formingEmail(String email, String newPass);

    void deleteUserById(Long id);

    void deleteUserByUsername(String username);

    String getUserEncryptPasswordById(Long id);

}
