package com.lisa.menu.controller;

//设置随机图形验证码
//1,引入验证码生成工具VerifyCodeUtils
//2,在controller中创建生成验证码实例

import com.lisa.menu.entity.User;
import com.lisa.menu.service.UserSerivce;
import com.lisa.menu.utils.VerifyCodeUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/user")
@Log
public class UserController {

    @Autowired
    private UserSerivce userSerivce;


    @GetMapping("/generateImageCode")
    public void generateImageCode(HttpServletResponse response, HttpSession session) throws IOException {
        //生成随机数
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //存入session
        session.setAttribute("code",code);
        //根据随机数生成随即图片
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        VerifyCodeUtils.outputImage(220,60,outputStream,code);

    }

    @PostMapping("/regist")
    public String regist(User user, String code, HttpSession session) throws UnsupportedEncodingException {
        log.log("用户信息：" + user);
        log.log("验证码：" + code);
        try {
            userSerivce.regist(user,code,session);
        }catch (Exception ex){
            return "redirect:/regist?err=" + URLEncoder.encode(ex.getMessage(),"UTF-8");
        }

        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session) throws UnsupportedEncodingException {
        try {
            User user = userSerivce.login(username, password);
            session.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login?err=" + URLEncoder.encode(e.getMessage(), "UTF-8");
        }
        return "redirect:/employee/list";

    }

    //用户退出
    @RequestMapping("/logout")
    public String logout( HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}