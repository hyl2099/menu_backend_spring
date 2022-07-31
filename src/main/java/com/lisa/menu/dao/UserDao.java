package com.lisa.menu.dao;

import com.lisa.menu.entity.User;

public interface UserDao {
    void save(User user);
    User getUserByUserName(String username);
}
