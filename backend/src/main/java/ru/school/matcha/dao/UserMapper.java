package ru.school.matcha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.school.matcha.domain.User;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserWithMinimumInfo(Long id);

    Optional<User> getUserByUsername(String username);

    List<User> getUsersByTagId(Long tagId);

    void createUser(@Param("user") User user, @Param("formId") Long formId);

    void createFullUser(User users);

    void updateUserByUsername(User user);

    void updateUserById(User user);

    void deleteUserById(Long id);

    void deleteUserByUsername(String username);

    String getUserEncryptPasswordById(Long id);

    void updatePasswordByUserId(@Param("userId") Long userId, @Param("newPassword") String newPassword);

}
