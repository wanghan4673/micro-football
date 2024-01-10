package com.football.news.service.intf;

import com.football.news.model.News;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {
    List<News> getNewsByPage(Integer page, Integer pageSize);

    News getNewsById(Long newsId);

    Boolean getReportStatus(Long newsId, Long userId);

    List<News> getAllNews();
}
