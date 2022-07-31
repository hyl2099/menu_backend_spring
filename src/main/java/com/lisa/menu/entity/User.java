package com.lisa.menu.entity;

import lombok.Data;

//Lombok项目是一个java库，它可以自动插入到编辑器和构建工具中，增强java的性能。不需要再写getter、setter或equals方法，只要有一个注解，你的类就有一个功能齐全的构建器、自动记录变量等等。

@Data
public class User {
    private Integer id;
    private String username;
    private String realname;
    private String password;
    private boolean gender;
}