package ru.school.matcha.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import ru.school.matcha.domain.Image;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class CloudinaryAPI {

    private static Cloudinary cloudinary;

    public static void init() {
        Properties properties;
        try {
            properties = Resources.getResourceAsProperties("cloudinary.properties");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        cloudinary = new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", properties.getProperty("cloudinary.cloud.name"),
                        "api_key", properties.getProperty("cloudinary.api.key"),
                        "api_secret", properties.getProperty("cloudinary.api.secret")
                ));
    }

    public static Image createFile(String fileName) {
        try {
            File file = new File("backend/images/" + fileName);
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            Image image = new Image();
            image.setName(fileName);
            image.setLink((String) uploadResult.get("url"));
            image.setExternalId((String) uploadResult.get("public_id"));
            return image;
        } catch (Exception ex) {
            log.debug("Failed to create file with name: {} in Cloudinary", fileName, ex);
        }
        log.error("An unexpected error occurred while trying to create file with name: {} in Cloudinary", fileName);
        return null;
    }

    public static void deleteFile(Image image) {
        try {
            cloudinary.uploader().destroy(image.getExternalId(), ObjectUtils.emptyMap());
        } catch (Exception ex) {
            log.debug("Failed to delete file with name: {} in Cloudinary", image.getName(), ex);
        }
    }

}
