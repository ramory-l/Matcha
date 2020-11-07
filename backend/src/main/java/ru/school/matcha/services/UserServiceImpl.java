package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.utils.MyBatisUtil;
import ru.school.matcha.dao.UserMapper;
import ru.school.matcha.domain.Form;
import ru.school.matcha.domain.User;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.security.PasswordCipher;
import ru.school.matcha.services.interfaces.FormService;
import ru.school.matcha.services.interfaces.UserService;

import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
public class UserServiceImpl implements UserService {

    private static final FormService formService;

    static {
        formService = new FormServiceImpl();
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Get all users");
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getAllUsers();
        }
    }

    @Override
    public User getUserById(Long id) {
        log.info("Get user by id: {}", id);
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getUserById(id)
                    .orElseThrow(() -> new MatchaException(String.format("User with id: %d doesn't exist", id)));
        }
    }

    @Override
    public User getUserByUsername(String username) {
        log.info("Get user by username: {}", username);
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getUserByUsername(username)
                    .orElseThrow(() -> new MatchaException(String.format("User with username: %s doesn't exist", username)));
        }
    }

    @Override
    public void createUser(User user) {
        log.info("Create new user");
        String username = user.getUsername();
        try {
            getUserByUsername(username);
            try {
                user.setPassword(PasswordCipher.generateStrongPasswordHash(user.getPassword()));
            } catch (Exception ex) {
                throw new MatchaException("Encrypt password error");
            }
            Long formId = null;
            SqlSession sqlSession = null;
            try {
                sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
                UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                FormService formService = new FormServiceImpl();
                Form defaultForm = new Form();
                defaultForm.setMan(false);
                defaultForm.setWoman(false);
                defaultForm.setFriendship(false);
                defaultForm.setLove(false);
                defaultForm.setSex(false);
                defaultForm.setFlirt(false);
                User newUser = new User();
                newUser.setUsername(user.getUsername());
                newUser.setPassword(user.getPassword());
                newUser.setEmail(user.getEmail());
                newUser.setFirstName(user.getFirstName());
                newUser.setLastName(user.getLastName());
                newUser.setRate(0L);
                defaultForm.setId(formService.createForm(defaultForm));
                formId = defaultForm.getId();
                userMapper.createUser(newUser, defaultForm.getId());
                sqlSession.commit();
            } catch (Exception ex) {
                if (nonNull(sqlSession)) {
                    sqlSession.rollback();
                }
                FormService formService = new FormServiceImpl();
                formService.deleteFormById(formId);
                throw new MatchaException("Failed to create user with username: " + username);
            } finally {
                if (nonNull(sqlSession)) {
                    sqlSession.close();
                }
            }
        } catch (MatchaException ex) {
            throw new MatchaException(String.format("User with username %s already exist", username));
        }
    }

    @Override
    public void batchCreateUsers(List<User> users) {
        log.info("Batch create users");
        SqlSession sqlSession = null;
        FormService formService = new FormServiceImpl();
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession(ExecutorType.BATCH);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            users.forEach(user -> {
                user.getForm().setId(formService.createForm(user.getForm()));
                try {
                    user.setPassword(PasswordCipher.generateStrongPasswordHash(user.getPassword()));
                } catch (Exception ex) {
                    throw new MatchaException(ex.getMessage());
                }
                user.setRate(0L);
                userMapper.createFullUser(user);
            });
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            formService.deleteAllInactiveForms();
            throw new MatchaException("Failed to batch create users");
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void updateUser(User user) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            if (nonNull(user.getId())) {
                log.info("Update user with id: {}", user.getId());
                userMapper.updateUserById(user);
            } else if (nonNull(user.getUsername())) {
                log.info("Update user with username: {}", user.getUsername());
                userMapper.updateUserByUsername(user);
            }
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to update user");
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        log.info("Delete user with id: {}", userId);
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = getUserById(userId);
            formService.deleteFormById(user.getForm().getId());
            userMapper.deleteUserById(userId);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to delete user by id: " + userId);
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void deleteUserByUsername(String username) {
        log.info("Delete user with username: {}", username);
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = getUserByUsername(username);
            formService.deleteFormById(user.getForm().getId());
            userMapper.deleteUserByUsername(username);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to delete user by username: " + username);
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public String getUserEncryptPasswordById(Long id) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getUserEncryptPasswordById(id);
        }
    }

}
