package ru.school.matcha.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Guest {
    private Image avatar;
    private String username;
    private Date date;
}
