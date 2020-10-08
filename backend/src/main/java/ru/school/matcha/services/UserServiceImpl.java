package ru.school.matcha.services;

import ru.school.matcha.dao.UserMapper;
import ru.school.matcha.domain.Credentials;
import ru.school.matcha.domain.User;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.services.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

//    private final UserMapper userMapper;

    public UserServiceImpl() {
//        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAllUsers() {
//        return userMapper.getAllUsers();
        List<User> mock = new ArrayList<>();
        User user1 = new User();
        user1.setUsername("test1");
        User user2 = new User();
        user2.setUsername("test2");
        User user3 = new User();
        user3.setUsername("test3");
        mock.add(user1);
        mock.add(user2);
        mock.add(user3);
        return mock;
    }

    @Override
    public Optional<User> getUserById(Long id) {
//        return userMapper.getUserById(id);
        return null;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
//        return userMapper.getUserByUsername(username);
        return null;
    }

    @Override
    public void createUser(Credentials credentials) {
        System.out.println(credentials);
//        userMapper.createUser(user);
    }

    @Override
    public void updateUser(User user) {
//        userMapper.updateUser(user);
    }

    @Override
    public void deleteUserById(Long id) {
//        userMapper.deleteUserById(id);
    }

    @Override
    public void deleteUserByUsername(String username) {
//        userMapper.deleteUserByUsername(username);
    }
}
