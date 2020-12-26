package ru.school.matcha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.school.matcha.domain.User;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    List<User> getAllUsers(Long userId);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserWithMinimumInfo(Long id);

    Optional<User> getUserByUsername(String username);

    List<User> getUsersByTagId(@Param("tagId") Long tagId, @Param("userId") Long userId);

    void createUser(@Param("user") User user, @Param("formId") Long formId);

    void createFullUser(User users);

    void updateUserById(User user);

    void deleteUserById(Long id);

    String getUserEncryptPasswordById(Long id);

    void updatePasswordByUserId(@Param("userId") Long userId, @Param("newPassword") String newPassword);

    void verifiedUser(Long userId);

    Optional<User> getUserFromBlackList(@Param("from") Long from, @Param("to") Long to);

    void addToBlackList(@Param("from") Long from, @Param("to") Long to);

    void deleteFromBlackList(@Param("from") Long from, @Param("to") Long to);

    List<User> getUserBlackList(Long userId);

    void updateLastLoginDateUsers(@Param("listIds") List<Long> listIds);

    void updateOfflineUsers(@Param("listIds") List<Long> listIds);

    void userIsOffline(Long id);

    void userIsOnline(Long id);

    void addingComplaint(@Param("from") Long from, @Param("to") Long to, @Param("message") String message);

    long getUserComplaint(@Param("from") Long from, @Param("to") Long to);

}
