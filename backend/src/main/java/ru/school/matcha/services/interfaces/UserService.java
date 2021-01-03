package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.Form;
import ru.school.matcha.domain.User;
import ru.school.matcha.domain.UserFullForBatch;

import java.util.List;

public interface UserService {

    List<User> getAllUsers(long userId);

    List<User> search(long userId, Form form, List<String> tags);

    User getUserById(Long id);

    User getUserByEmail(String email);

    User getUserByUsername(String username);

    List<User> getUsersByTagId(Long tagId, Long userId);

    List<User> getMatcha(Long id);

    Long createUser(User user);

    void batchCreateUsers(List<UserFullForBatch> users);

    void updateUser(User user);

    void updatePassword(String hash);

    void formingResetPasswordEmail(String email, String newPass);

    void deleteUserById(Long id);

    String getUserEncryptPasswordById(Long id);

    void formingVerificationEmail(String email);

    void verified(String hash);

    void addToBlackList(long from, long to);

    void deleteFromBlackList(long from, long to);

    void checkUsers(Long from, Long to);

    List<User> getUserBlackList(long userId);

    void checkOnBlackList(long from, long to);

    void updateActivityStatusForUsers(List<Long> ids);

    void userIsFake(long from, long to, String message);

    void userIsOffline(Long userId);

    void userIsOnline(Long userId);

}
