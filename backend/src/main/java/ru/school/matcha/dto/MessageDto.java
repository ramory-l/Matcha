package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import ru.school.matcha.domain.Image;

import java.util.Date;

@Data
@JsonAutoDetect
public class MessageDto {

    private Long from;
    private Long to;
    private String message;
    private Date createTs;
    private String type;
    private String username;
    private Image avatar;

}
