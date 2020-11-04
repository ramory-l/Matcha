package ru.school.matcha.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Slf4j
public class ImageCoder {

    private static final String IMAGE_PATH = "backend/images/";

    public static void decodeImage(String encodedString, String fileName) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
            FileUtils.writeByteArrayToFile(new File(IMAGE_PATH + fileName), decodedBytes);
            System.out.println("Hello");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String encodeImage(String fileName) {
        byte[] fileContent = null;
        try {
            fileContent = FileUtils.readFileToByteArray(new File(IMAGE_PATH + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(fileContent);
    }

}
