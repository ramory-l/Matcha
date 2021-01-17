package org.example;

import lombok.Data;

@Data
public class Image {

    private Long id;
    private String name;
    private String link;
    private String externalId;
    private Long userId;

}
