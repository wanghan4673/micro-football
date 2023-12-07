package com.football.forum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/upload")
public class UploadFileController {
    @PostMapping("")
    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
//        构建新的文件名
        String newFileName= UUID.randomUUID() +originalFilename.substring(originalFilename.lastIndexOf("."));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 构建请求体，这里需要根据实际情况填入multipart form data
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        // 添加 multipart form data
        // body.add("key1", "value1");
        body.add("key",newFileName);
        body.add("file",file);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 根据腾讯云的地址构建请求URL
        String url = "https://<BucketName-APPID>.cos.<Region>.myqcloud.com";

        // 发起请求
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // 处理响应
        HttpStatusCode statusCode = response.getStatusCode();
        String responseBody = response.getBody();



        return newFileName;
    }
}
