package com.lisa.menu.service;

import com.lisa.menu.entity.User;

import javax.servlet.http.HttpSession;

public interface UserSerivce {
    void regist(User user, String code, HttpSession session);

    User login(String username, String password);
}