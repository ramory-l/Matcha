package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class ImageDto {
    private Long id;
    private String name;
    private String link;
    private String externalId;
    private Long userId;
}
