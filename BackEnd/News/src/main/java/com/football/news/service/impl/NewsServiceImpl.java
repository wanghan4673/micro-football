package com.football.news.service.impl;

import com.football.news.mapper.NewsMapper;
import com.football.news.model.News;
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
    public List<News> getNewsByPage(Integer page, Integer pageSize) {
        Integer start = (page - 1) * pageSize;
        return newsMapper.getNewsByPage(start,pageSize);
    }

    @Override
    public News getNewsById(Long newsId) {
        return newsMapper.getNewsById(newsId);
    }

    @Override
    public Boolean getReportStatus(Long newsId, Long userId) {
        return newsMapper.getReportStatus(newsId,userId);
    }

    @Override
    public List<News> getAllNews() {
        return newsMapper.getAllNews();
    }
}
