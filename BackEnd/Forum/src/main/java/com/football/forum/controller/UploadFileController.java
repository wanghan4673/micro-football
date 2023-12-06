package com.football.forum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/upload")
public class UploadFileController {
    @PostMapping()
    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
//        构建新的文件名
        String newFileName= UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        // 获取项目根目录
        String rootPath = System.getProperty("user.dir");

        // 拼接保存路径
        String savePath = rootPath + File.separator + "public" + File.separator + newFileName;

        // 将文件保存到指定路径
        file.transferTo(new File(savePath));

        return newFileName;
    }
}
