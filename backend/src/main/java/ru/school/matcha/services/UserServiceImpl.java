package ru.school.matcha.services;

import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.configs.MyBatisUtil;
import ru.school.matcha.dao.UserMapper;
import ru.school.matcha.domain.Credentials;
import ru.school.matcha.domain.Form;
import ru.school.matcha.domain.User;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.services.interfaces.FormService;
import ru.school.matcha.services.interfaces.UserService;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

public class UserServiceImpl implements UserService {

    @Override
    public List<User> getAllUsers() {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getAllUsers();
        }
    }

    @Override
    public Optional<User> getUserById(Long id) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getUserById(id);
        }
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getUserByUsername(username);
        }
    }

    @Override
    public Long createUser(Credentials credentials) {
        String username = credentials.getUsername();
        Optional<User> optionalUser = getUserByUsername(username);
        if (!optionalUser.isPresent()) {
            try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
                UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                FormService formService = new FormServiceImpl();
                Form defaultForm = new Form();
                defaultForm.setMan(false);
                defaultForm.setWoman(false);
                defaultForm.setFriendship(false);
                defaultForm.setLove(false);
                defaultForm.setSex(false);
                defaultForm.setFlirt(false);
                User user = new User();
                defaultForm.setId(formService.createForm(defaultForm));
                userMapper.createUser(credentials, defaultForm.getId(), user);
                sqlSession.commit();
                return user.getId();
            }
        } else {
            throw new MatchaException(String.format("User with username %s already exist", username));
        }
    }

    @Override
    public void updateUser(User user) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            if (nonNull(user.getId())) {
                userMapper.updateUserById(user);
            } else {
                userMapper.updateUserByUsername(user);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteUserById(Long id) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.deleteUserById(id);
            sqlSession.commit();
        }
    }

    @Override
    public void deleteUserByUsername(String username) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.deleteUserByUsername(username);
            sqlSession.commit();
        }
    }
}
