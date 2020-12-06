package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.Date;

@Data
@JsonAutoDetect
public class GuestDto {

    private UserWithMinimumInfoDto user;
    private Date date;

}
