package com.lisa.menu.entity;

import lombok.Data;

@Data
public class Menu {

    private Long id;
    private String name;
    private String description;
    private Float price;
    private String path;
    private String image;
}
