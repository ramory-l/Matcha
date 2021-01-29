package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class ImageBase64Dto {
    private String name;
    private String base64;
    private Long userId;
}
