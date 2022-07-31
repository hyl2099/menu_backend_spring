package com.lisa.menu.controller;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Value("${img.file.dir}")
    private String realPath;

    @RequestMapping("/save")
    public String save(Employee employee, MultipartFile img) throws IOException {
        log.debug("employee: {}",employee);
        log.debug("file name: {}", img.getOriginalFilename());
        //创建新文件名
        String newImgName = setNewImgName(img);

        //将头像文件存储到文件夹
        img.transferTo(new File(realPath,newImgName));
        //存储新的文件名
        employee.setPhoto(newImgName);
        //存储员工信息
        employeeService.save(employee);
        return "redirect:/employee/list";
    }
    //生成新的文件名
    private String setNewImgName(MultipartFile img) {
        //前缀
        String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        //后缀
        String fileNameSuffix = FilenameUtils.getExtension(img.getOriginalFilename());
        //新文件名
        String newImgName = fileNamePrefix + "." + fileNameSuffix;
        return newImgName;
    }