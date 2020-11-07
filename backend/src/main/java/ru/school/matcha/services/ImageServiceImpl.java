package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.dao.ImageMapper;
import ru.school.matcha.domain.Image;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.services.interfaces.ImageService;
import ru.school.matcha.services.interfaces.UserService;
import ru.school.matcha.utils.GoogleDrive;
import ru.school.matcha.utils.ImageCoder;
import ru.school.matcha.utils.MyBatisUtil;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class ImageServiceImpl implements ImageService {

    private static final UserService userService;

    static {
        userService = new UserServiceImpl();
    }

    @Override
    public Long createImage(String base64, String fileName) {
        log.info("Create image");
        SqlSession sqlSession = null;
        try {
            ImageCoder.decodeImage(base64, fileName);
            Image image = GoogleDrive.createFile(fileName);
            if (isNull(image)) {
                throw new MatchaException("Failed to create image in GoogleDrive");
            }
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
            imageMapper.createImage(image);
            sqlSession.commit();
                return image.getId();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to create image");
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public Image getImageById(Long id) {
        log.info("Get image by id: {}", id);
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
            return imageMapper.getImageById(id)
                    .orElseThrow(() -> new MatchaException(String.format("Image with id: %d doesn't exist", id)));
        }
    }

    @Override
    public Image getImageByExternalId(String externalId) {
        log.info("Get image by external id: {}", externalId);
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
            return imageMapper.getImageByExternalId(externalId)
                    .orElseThrow(() -> new MatchaException(String.format("Image with external id: %s doesn't exist", externalId)));
        }
    }

    @Override
    public Image getAvatarByUserId(Long userId) {
        log.info("Get avatar by user with id: {}", userId);
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
            userService.getUserById(userId);
            return imageMapper.getAvatarByUserId(userId)
                    .orElseThrow(() -> new MatchaException(String.format("Avatar by user with id: %d doesn't exist", userId)));
        }
    }

    @Override
    public List<Image> getImagesByUserId(Long userId) {
        log.info("Get images by user with id: {}", userId);
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
            userService.getUserById(userId);
            return imageMapper.getImagesByUserId(userId);
        }
    }

    @Override
    public void deleteImageById(Long id) {
        log.info("Delete image by id: {}", id);
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
            imageMapper.deleteImageById(id);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to delete image by id: " + id);
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void deleteImageByExternalId(String externalId) {
        log.info("Delete image by external id: {}", externalId);
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
            imageMapper.deleteImageByExternalId(externalId);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to delete image by external id: " + externalId);
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void deleteAllImagesByUserId(Long userId) {
        log.info("Delete all images by userId: {}", userId);
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
            imageMapper.deleteAllImagesByUserId(userId);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to delete all images by user with id: " + userId);
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

}
