package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.domain.*;
import ru.school.matcha.exceptions.NotFoundException;
import ru.school.matcha.security.jwt.JwtTokenProvider;
import ru.school.matcha.services.interfaces.*;
import ru.school.matcha.utils.MailUtil;
import ru.school.matcha.utils.MyBatisUtil;
import ru.school.matcha.dao.UserMapper;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.security.PasswordCipher;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class UserServiceImpl implements UserService {

    private final FormService formService = new FormServiceImpl();
    private final ImageService imageService = new ImageServiceImpl();
    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
    private final LikeService likeService = new LikeServiceImpl();
    private final GuestService guestService = new GuestServiceImpl();

    @Override
    public List<User> getAllUsers(long userId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getAllUsers(userId);
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
        List<User> users = new ArrayList<>();
        List<Like> allLikesWithMentionUser = likeService.getAllLikesWithMentionUserById(id);
        allLikesWithMentionUser.stream().filter(like -> like.getFrom().equals(id)).forEach(like -> {
            if (allLikesWithMentionUser.stream().anyMatch(otherLike -> otherLike.getFrom().equals(like.getTo()))) {
                users.add(getUserById(like.getTo()));
            }
        });
        return users;
    }

    @Override
    public Long createUser(User user) {
        checkAllDataForNewUser(user);
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

    private void checkAllDataForNewUser(User user) {
        if (isNull(user) || isNull(user.getUsername()) || "".equals(user.getUsername())
                || isNull(user.getEmail()) || "".equals(user.getEmail())
                || isNull(user.getFirstName()) || "".equals(user.getFirstName())
                || isNull(user.getLastName()) || "".equals(user.getLastName())
                || isNull(user.getPassword()) || "".equals(user.getPassword())
        ) {
            throw new MatchaException("Not all fields are filled");
        }
    }

    @Override
    public void batchCreateUsers(List<UserFullForBatch> users) {
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
                userMapper.createImageForFullUser(user);
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
        avatarHelper(users);
    }

    private void avatarHelper(List<UserFullForBatch> users) {
        users.forEach(userFullForBatch -> {
            User user = new User();
            user.setId(userFullForBatch.getId());
            Image image = new Image();
            image.setId(userFullForBatch.getImage().getId());
            user.setAvatar(image);
            updateUser(user);
        });
    }

    @Override
    public void updateUser(User user) {
        if (isNull(user.getId())) {
            throw new MatchaException("Unidentified user");
        }
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
            if (nonNull(user.getEmail())) {
                if (nonNull(getUserByEmail(user.getEmail()))) {
                    throw new MatchaException("User with such email already exist");
                }
            }
            if (nonNull(user.getUsername())) {
                if (nonNull(getUserById(user.getId()))) {
                    throw new MatchaException("User with such username already exist");
                }
            }
            userMapper.updateUserById(user);
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
    public List<User> getUsersByTagId(Long tagId, Long userId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getUsersByTagId(tagId, userId);
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

    @Override
    public void addToBlackList(long from, long to) {
        SqlSession sqlSession = null;
        try {
            likeService.deleteLike(from, to, true);
            likeService.deleteLike(from, to, false);
            likeService.deleteLike(to, from, true);
            likeService.deleteLike(to, from, false);
            guestService.deleteGuest(from, to);
        } catch (MatchaException ex) {
            log.debug(ex.getMessage());
        }
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            if (userMapper.getUserFromBlackList(from, to).isPresent()) {
                throw new MatchaException("User already in black list");
            }
            checkUsers(from, to);
            userMapper.addToBlackList(from, to);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to adding to black list. " + ex.getMessage());
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void deleteFromBlackList(long from, long to) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            if (!userMapper.getUserFromBlackList(from, to).isPresent()) {
                throw new MatchaException("User already in black list");
            }
            checkUsers(from, to);
            userMapper.deleteFromBlackList(from, to);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to adding to black list. " + ex.getMessage());
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void checkUsers(Long from, Long to) {
        if (isNull(getUserById(from))) {
            throw new MatchaException("User 'from' with id: " + from + " doesn't exist");
        }
        if (isNull(getUserById(to))) {
            throw new MatchaException("User 'to' with id: " + to + " doesn't exist");
        }
    }

    @Override
    public List<User> getUserBlackList(long userId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getUserBlackList(userId);
        }
    }

    @Override
    public void checkOnBlackList(long from, long to) {
        List<User> blackListUsers = getUserBlackList(to);
        if (blackListUsers.stream().parallel().anyMatch(blackUser -> blackUser.getId().equals(from))) {
            throw new MatchaException("User in black list");
        }
    }

    @Override
    public void updateActivityStatusForUsers(List<Long> listIds) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.updateLastLoginDateUsers(listIds);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to update last login date users. " + ex.getMessage());
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void userIsFake(long from, long to, String message) {
        if (isNull(message) || "".equals(message)) {
            throw new MatchaException("Message is empty");
        }
        checkUsers(from, to);
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            if (userMapper.getUserComplaint(from, to) > 0) {
                throw new MatchaException("User already in black list");
            }
            userMapper.addingComplaint(from, to, message);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to adding complaint. " + ex.getMessage());
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
            addToBlackList(from, to);
        }
    }

    @Override
    public void userIsOffline(Long userId) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.userIsOffline(userId);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to offline. " + ex.getMessage());
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void userIsOnline(Long userId) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.userIsOnline(userId);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to online. " + ex.getMessage());
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

}
