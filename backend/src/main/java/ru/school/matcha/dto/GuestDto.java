package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import ru.school.matcha.domain.Image;

import java.util.Date;

@Data
@JsonAutoDetect
public class GuestDto {

    private ImageDto avatar;
    private String username;
    private Date date;

}
