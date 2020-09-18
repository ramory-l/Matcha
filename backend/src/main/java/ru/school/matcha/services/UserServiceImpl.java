package ru.school.matcha.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.school.matcha.dao.UserMapper;
import ru.school.matcha.domain.User;
import ru.school.matcha.services.interfaces.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public void createUser(User user) {
        userMapper.createUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userMapper.deleteUserById(id);
    }

    @Override
    public void deleteUserByUsername(String username) {
        userMapper.deleteUserByUsername(username);
    }
}
