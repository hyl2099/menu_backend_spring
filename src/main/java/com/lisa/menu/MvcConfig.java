package com.lisa.menu;
//创建Config文件，将对html文件的访问对应到Controller
//这样做的目的是为了简化访问thymeleaf的html文件，不必为每个html文件单独创建controller请求
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    //不用再为每一个thymeleaf模板单独开发一个controller请求了
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //viewController:请求路径，viewName:跳转视图
        registry.addViewController("login").setViewName("login");
        registry.addViewController("regist").setViewName("regist");
//        通过浏览器能够访问到addEmp.html页面
        registry.addViewController("addEmp").setViewName("addEmp");
    }
}