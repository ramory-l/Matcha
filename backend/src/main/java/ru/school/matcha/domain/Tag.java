package ru.school.matcha.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Tag {

    private Long id;
    private String name;
    private Date createTs;

}
