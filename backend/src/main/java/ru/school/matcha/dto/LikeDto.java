package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import ru.school.matcha.domain.Image;

@Data
@JsonAutoDetect
public class LikeDto {

    private Image avatar;
    private String username;
    private Long id;

}
