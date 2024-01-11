package com.football.news.service.intf;

import com.football.news.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {
    List<News> getTextNewsByPage(Integer page, Integer pageSize);

    NewsDetail getNewsById(Long newsId);

    Boolean getReportStatus(Long newsId, Long userId);

    List<News> getAllNews();
    List<ReportedNews> getReportedNews();

    List<PictureNews> getRandomPicNews(Integer count);

    List<VideoNews> getRandomVideoNews(Integer count);

    Boolean insertReportedNews(Long userId, String userName,Long newsId,String content);

    Boolean deleteNews(Long id);

    Boolean deleteReport(Long reporterId, Long newsId);
}
