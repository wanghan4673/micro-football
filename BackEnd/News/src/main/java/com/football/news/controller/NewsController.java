package com.football.news.controller;


import com.football.news.mapper.NewsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@Slf4j
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsMapper newsMapper;


    @GetMapping("/getOneNewsById")
    public ResponseEntity<?> getNewsById(@RequestParam Integer id){
       return ResponseEntity.ok().body(newsMapper.findAllByNewsId(id));
    }

}
