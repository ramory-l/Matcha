package ru.school.matcha.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Image {

    private Long id;
    private String name;
    private String link;
    private String externalId;
    private Long userId;
    private Date createTs;

}
