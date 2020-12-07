package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.Date;

@Data
@JsonAutoDetect
public class GuestDto {

    private UserMinInfoDto user;
    private Date date;

}
