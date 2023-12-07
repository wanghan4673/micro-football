package com.football.news.service.intf;

import com.football.news.model.NewsEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService  {

    List<NewsEntity> TEST(String Tag1, String Tag2,Integer id);
}
