package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.exceptions.NotFoundException;
import ru.school.matcha.security.jwt.JwtTokenProvider;
import ru.school.matcha.services.interfaces.ImageService;
import ru.school.matcha.utils.MailUtil;
import ru.school.matcha.utils.MyBatisUtil;
import ru.school.matcha.dao.UserMapper;
import ru.school.matcha.domain.Form;
import ru.school.matcha.domain.User;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.security.PasswordCipher;
import ru.school.matcha.services.interfaces.FormService;
import ru.school.matcha.services.interfaces.UserService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class UserServiceImpl implements UserService {

    private final FormService formService = new FormServiceImpl();
    private final ImageService imageService = new ImageServiceImpl();
    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @Override
    public List<User> getAllUsers() {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getAllUsers();
        }
    }

    @Override
    public User getUserById(Long id) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getUserById(id).orElseThrow(NotFoundException::new);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getUserByEmail(email).orElseThrow(NotFoundException::new);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getUserByUsername(username).orElseThrow(NotFoundException::new);
        }
    }

    @Override
    public List<User> getMatcha(Long id) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getMatcha(id);
        }
    }

    @Override
    public Long createUser(User user) {
        String username = user.getUsername();
        try {
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
                newUser.setIsVerified(false);
                defaultForm.setId(formService.createForm(defaultForm).getId());
                formId = defaultForm.getId();
                userMapper.createUser(newUser, defaultForm.getId());
                sqlSession.commit();
                formingVerificationEmail(user.getEmail());
                return newUser.getId();
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
            throw new MatchaException("User already exist");
        }
    }

    @Override
    public void batchCreateUsers(List<User> users) {
        SqlSession sqlSession = null;
        FormService formService = new FormServiceImpl();
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession(ExecutorType.BATCH);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            users.forEach(user -> {
                user.getForm().setId(formService.createForm(user.getForm()).getId());
                try {
                    user.setPassword(PasswordCipher.generateStrongPasswordHash(user.getPassword()));
                } catch (Exception ex) {
                    throw new MatchaException(ex.getMessage());
                }
                user.setRate(0L);
                user.setIsVerified(true);
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
            if (nonNull(user.getAvatar()) && nonNull(user.getAvatar().getId())) {
                imageService.getImageById(user.getAvatar().getId());
            }
            if (nonNull(user.getPassword()) && !user.getPassword().equals("")) {
                try {
                    user.setPassword(PasswordCipher.generateStrongPasswordHash(user.getPassword()));
                } catch (Exception ex) {
                    throw new MatchaException("Encrypt password error");
                }
            }
            if (nonNull(user.getId())) {
                userMapper.updateUserById(user);
            } else if (nonNull(user.getUsername())) {
                userMapper.updateUserByUsername(user);
            }
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to update user. " + ex.getMessage());
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = getUserById(userId);
            userMapper.deleteUserById(userId);
            formService.deleteFormById(user.getForm().getId());
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
    public String getUserEncryptPasswordById(Long id) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getUserEncryptPasswordById(id);
        }
    }

    @Override
    public List<User> getUsersByTagId(Long tagId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getUsersByTagId(tagId);
        }
    }

    @Override
    public void updatePassword(String hash) {
        if (!jwtTokenProvider.validateToken(hash)) {
            throw new MatchaException("Link expired");
        }
        Long userId = jwtTokenProvider.getUserIdFromPasswordToken(hash);
        String newPassword;
        try {
            newPassword = PasswordCipher.generateStrongPasswordHash(jwtTokenProvider.getPasswordFromPasswordToken(hash));
        } catch (Exception ex) {
            throw new MatchaException("Encrypt password error");
        }
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.updatePasswordByUserId(userId, newPassword);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to update password. " + ex.getMessage());
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void formingResetPasswordEmail(String email, String newPass) {
        if (isNull(email) || isNull(newPass)) {
            throw new NotFoundException("Fields are not filled");
        }
        User user = getUserByEmail(email);
        if (nonNull(user)) {
            String passwordToken = jwtTokenProvider.createPasswordToken(newPass, user.getId());
            MailUtil.send(user.getEmail(), "Password change",
                    "Hello, " + user.getUsername() + "<br><br> Please Click " +
                            "<a href='http://localhost:8080/api/users/password/" + passwordToken + "'>" +
                            "<strong>here</strong></a> to reset your password. The link works " +
                            "within 24 hours. <br><br><br> Thanks!");
        }
    }

    @Override
    public void formingVerificationEmail(String email) {
        if (nonNull(email)) {
            User user = getUserByEmail(email);
            if (nonNull(user)) {
                String verifiedToken = jwtTokenProvider.createVerifiedToken(user.getId());
                MailUtil.send(user.getEmail(), "User verification",
                        "Hello, " + user.getUsername() + "<br><br> Please Click " +
                                "<a href='http://localhost:8080/api/users/verified/" + verifiedToken + "'>" +
                                "<strong>here</strong></a> to verified your account. The link works " +
                                "within 7 days. <br><br><br> Thanks!");
            }
        }
    }

    @Override
    public void verified(String hash) {
        if (!jwtTokenProvider.validateToken(hash)) {
            throw new MatchaException("Link expired");
        }
        Long userId = jwtTokenProvider.getUserIdFromVerifiedToken(hash);
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.verifiedUser(userId);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to verified user. " + ex.getMessage());
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

}
