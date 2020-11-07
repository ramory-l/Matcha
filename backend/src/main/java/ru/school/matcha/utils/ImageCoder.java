package ru.school.matcha.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Slf4j
public class ImageCoder {

    private static final String IMAGE_PATH = "backend/images/";

    public static void decodeImage(String encodedString, String fileName) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        FileUtils.writeByteArrayToFile(new File(IMAGE_PATH + fileName), decodedBytes);
    }

    public static String encodeImage(String fileName) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(new File(IMAGE_PATH + fileName));
        return Base64.getEncoder().encodeToString(fileContent);
    }

}
