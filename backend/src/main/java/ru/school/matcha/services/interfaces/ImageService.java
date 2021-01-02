package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.Image;

import java.util.List;

public interface ImageService {

    Long createImage(String base64, String fileName, Long userId);

    void getImageById(Long id);

    Image getAvatarByUserId(Long userId);

    List<Image> getImagesByUserId(Long userId);

    Long getCountImagesByUserId(Long userId);

    Image getImageByExternalId(String externalId);

    void deleteImageById(Long id);

}
