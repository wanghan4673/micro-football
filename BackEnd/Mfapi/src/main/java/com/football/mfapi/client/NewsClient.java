package com.football.mfapi.client;

import com.football.mfapi.dto.NewsDTO;
import com.football.mfapi.dto.ReportedNewsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("news-service")
public interface NewsClient {
    @GetMapping("/news/all")
    List<NewsDTO> getAllNews();

    @GetMapping("/news/reported")
    List<ReportedNewsDTO> getReportedNews();

    @DeleteMapping("/news")
    Boolean deleteNews(@RequestParam("id") Long id);

    @DeleteMapping("/news/report")
    Boolean deleteReport(@RequestParam("reporterId") Long reporterId,
                         @RequestParam("newsId") Long newsId);
}
