package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.Image;

import java.util.List;

public interface ImageService {

    Long createImage(String base64, String fileName);

    Image getImageById(Long id);

    Image getImageByExternalId(String externalId);

    Image getAvatarByUserId(Long userId);

    List<Image> getImagesByUserId(Long userId);

    void deleteImageById(Long id);

    void deleteImageByExternalId(String externalId);

    void deleteAllImagesByUserId(Long userId);

}
