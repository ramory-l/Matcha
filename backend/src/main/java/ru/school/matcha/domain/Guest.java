package ru.school.matcha.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Guest {

    private Long userId;
    private Date date;

}
