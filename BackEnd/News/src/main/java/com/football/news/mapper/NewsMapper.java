package com.football.news.mapper;

import com.football.news.model.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsMapper extends JpaRepository<NewsEntity,Integer> {

    public List<NewsEntity> findAllByNewsId(Integer NewsId);



}
