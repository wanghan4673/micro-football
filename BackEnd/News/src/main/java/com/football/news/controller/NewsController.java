package com.football.news.controller;


import com.football.forum.service.intf.ForumService;
import com.football.news.mapper.NewsMapper;
import com.football.news.service.intf.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/News")
public class NewsController {

    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private NewsService newsService;

    // 按照新闻ID返回新闻内容（测试用）
    @GetMapping("/getOneNewsById")
    public ResponseEntity<?> getNewsById(@RequestParam Integer id){
       return ResponseEntity.ok().body(newsMapper.findAllByNewsId(id));
    }

    //初始化新闻页面的信息，防止初始化页面请求次数过多
    @GetMapping("/Init")
    public ResponseEntity<?> initNews(@RequestParam String Tag1,String Tag2){
        return ResponseEntity.ok().body(newsMapper.findNewsTag(Tag1,Tag2));
    }

    // 按照新闻的两个Tag返回新闻列表，num代表返回数量
    @GetMapping("/getNews")
    public ResponseEntity<?> getNews(@RequestParam String Tag1,String Tag2,Integer num){
        return ResponseEntity.ok().body(newsService.getNewsInfo(Tag1,Tag2,num));
    }

    // 按照新闻的两个Tag返回视频列表，num代表返回数量
    @GetMapping("/getVideo")
    public ResponseEntity<?> getVideo(@RequestParam String Tag1,String Tag2,Integer num){
        return ResponseEntity.ok().body(newsService.getVideo(Tag1,Tag2,num));
    }

    //按tag筛选新闻
    @GetMapping("/SelectNews")
    public ResponseEntity<?> selectNews(@RequestParam String Tag1,String Tag2){
        return ResponseEntity.ok().body(newsService.getNewsInfo(Tag1,Tag2));
    }

    //按tag筛选视频
    @GetMapping("/SelectVideo")
    public ResponseEntity<?> selectVideo(@RequestParam String Tag1,String Tag2){
        return ResponseEntity.ok().body(newsService.getVideo(Tag1,Tag2));
    }

    //搜索新闻
    @GetMapping("/SearchNews")
    public ResponseEntity<?> SearchNews(@RequestParam String SearchItem){
        return ResponseEntity.ok().body(newsService.searchNews(SearchItem));
    }

    //搜索视频
    @GetMapping("/SearchVideo")
    public ResponseEntity<?> SearchVideo(@RequestParam String SearchItem){
        return ResponseEntity.ok().body(newsService.searchVideo(SearchItem));
    }



}
