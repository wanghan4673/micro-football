package com.football.news.service.impl;

import com.football.news.mapper.NewsMapper;
import com.football.news.model.NewsEntity;
import com.football.news.service.intf.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceimpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;

    //test
    @Override
    public List<NewsEntity> TEST(String Tag1, String Tag2,Integer id){
        return ResponseEntity.ok().body(newsMapper.findAllByNewsId(id)).getBody();
    }
    //test over
}
