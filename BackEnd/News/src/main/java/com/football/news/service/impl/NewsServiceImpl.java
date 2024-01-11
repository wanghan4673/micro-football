package com.football.news.service.impl;

import com.football.news.mapper.NewsMapper;
import com.football.news.model.*;
import com.football.news.service.intf.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsMapper newsMapper;
    @Override
    public List<News> getTextNewsByPage(Integer page, Integer pageSize) {
        Integer start = (page - 1) * pageSize;
        return newsMapper.getTextNewsByPage(start,pageSize);
    }

    @Override
    public List<PictureNews> getRandomPicNews(Integer count) {
        return newsMapper.getRandomPicNews(count);
    }

    @Override
    public List<VideoNews> getRandomVideoNews(Integer count) {
        return newsMapper.getRandomVideoNews(count);
    }

    @Override
    public Boolean insertReportedNews(Long userId, String userName,Long newsId,String content) {
        return newsMapper.insertReportedNews(userId,userName,newsId,content);
    }

    @Override
    public NewsDetail getNewsById(Long newsId) {
        return newsMapper.getNewsById(newsId);
    }

    @Override
    public Boolean getReportStatus(Long newsId, Long userId) {
        return (newsMapper.getReportStatus(newsId, userId) > 0);
    }

    @Override
    public List<News> getAllNews() {
        return newsMapper.getAllNews();
    }

    @Override
    public List<ReportedNews> getReportedNews() {
        return newsMapper.getReportedNews();
    }

    @Override
    public Boolean deleteNews(Long id) {
        return newsMapper.deleteNewsById(id);
    }
}
