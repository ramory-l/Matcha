package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.dao.ImageMapper;
import ru.school.matcha.domain.Image;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.exceptions.NotFoundException;
import ru.school.matcha.services.interfaces.ImageService;
import ru.school.matcha.services.interfaces.UserService;
import ru.school.matcha.utils.CloudinaryAPI;
import ru.school.matcha.utils.ImageUtil;
import ru.school.matcha.utils.MyBatisUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class ImageServiceImpl implements ImageService {
    private static final UserService userService = new UserServiceImpl();

    private static final int LIMIT = 5;

    @Override
    public Long createImage(String base64, String fileName, Long userId) {
        if (getCountImagesByUserId(userId) >= LIMIT) {
            throw new MatchaException("Photo limit reached (limit: " + LIMIT + ")");
        }
        SqlSession sqlSession = null;
        try {
            ImageUtil.decodeImage(base64, fileName);
            Image image = CloudinaryAPI.createFile(fileName);
            if (isNull(image)) {
                throw new MatchaException("Failed to create image in Cloudinary");
            }
            image.setUserId(userId);
            deleteImageFromServer(fileName);
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
    public Long getCountImagesByUserId(Long userId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
            return imageMapper.getCountImagesByUserId(userId);
        }
    }

    private void deleteImageFromServer(String fileName) {
        Path filepath = Paths.get("backend/images/" + fileName);
        try {
           Files.deleteIfExists(filepath);
        } catch (IOException ex) {
            log.debug(ex.getMessage());
        }
    }

    @Override
    public void getImageById(Long id) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
            imageMapper.getImageById(id).orElseThrow(NotFoundException::new);
        }
    }

    @Override
    public Image getAvatarByUserId(Long userId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
            userService.getUserById(userId);
            return imageMapper.getAvatarByUserId(userId).orElseThrow(NotFoundException::new);
        }
    }

    @Override
    public List<Image> getImagesByUserId(Long userId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
            userService.getUserById(userId);
            return imageMapper.getImagesByUserId(userId);
        }
    }

    @Override
    public void deleteImageById(Long id) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
            Image image = imageMapper.getImageById(id)
                    .orElseThrow(() -> new NotFoundException("Image not found"));
            CloudinaryAPI.deleteFile(image);
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
}
