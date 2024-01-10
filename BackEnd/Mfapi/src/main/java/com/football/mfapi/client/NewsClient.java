package com.football.mfapi.client;

import com.football.mfapi.dto.NewsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("news-service")
public interface NewsClient {
    @GetMapping("/news/all")
    List<NewsDTO> getAllNews();
}
