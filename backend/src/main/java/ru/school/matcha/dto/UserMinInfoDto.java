package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.Date;

@Data
@JsonAutoDetect
public class UserMinInfoDto {

    private Long id;
    private String username;
    private String gender;
    private Date birthday;
    private ImageDto avatar;

}
