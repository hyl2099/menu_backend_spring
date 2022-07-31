package com.lisa.menu.service.impl;

import com.lisa.menu.dao.UserDao;
import com.lisa.menu.entity.User;
import com.lisa.menu.service.UserSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

@Service
@Transactional
public class UserServiceImpl implements UserSerivce {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void regist(User user, String code, HttpSession session) {
        //判断用户输入的验证码和session中存储的验证码是否相同
        log.debug("session code: {}",session.getAttribute("code"));
        if (!code.equalsIgnoreCase(session.getAttribute("code").toString())) {
            throw new RuntimeException("验证码错误！");
        }
        //判断用户名是否已注册过
        if (userDao.getUserByUserName(user.getUsername()) != null) {
            throw new RuntimeException("用户名已注册过了！");
        }
        //给密码加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        //将输入插入到数据表
        userDao.save(user);

    }
    @Override
    public User login(String username, String password) {
        User user = userDao.getUserByUserName(username);
        if (ObjectUtils.isEmpty(user)) throw new RuntimeException("用户不存在");
        String newPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (!user.getPassword().equals(newPassword)) throw new RuntimeException(("密码输入错误"));
        return user;
    }
}