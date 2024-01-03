package com.football.mfapi.client;

import com.football.mfapi.dto.NewsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("news-service")
public interface NewsClient {
    @GetMapping("/news/allnews")
    List<NewsDTO> getNews();

    @DeleteMapping("/news/news")
    void deleteNews(@RequestParam Integer news_id);
}

