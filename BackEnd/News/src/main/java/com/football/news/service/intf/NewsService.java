package com.football.news.service.intf;

import com.football.news.model.News;
import com.football.news.model.NewsDetail;
import com.football.news.model.PictureNews;
import com.football.news.model.VideoNews;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {
    List<News> getTextNewsByPage(Integer page, Integer pageSize);

    NewsDetail getNewsById(Long newsId);

    Boolean getReportStatus(Long newsId, Long userId);

    List<News> getAllNews();

    List<PictureNews> getRandomPicNews(Integer count);

    List<VideoNews> getRandomVideoNews(Integer count);
}
