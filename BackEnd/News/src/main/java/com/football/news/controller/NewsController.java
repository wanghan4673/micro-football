package com.football.news.controller;

import com.football.mfapi.dto.NewsDTO;
import com.football.news.mapper.NewsMapper;
import com.football.news.model.Entity.NewsEntity;
import com.football.news.service.intf.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private NewsService newsService;

//    // 按照新闻ID返回新闻内容（测试用）
//    @GetMapping("/getOneNewsById")
//    public ResponseEntity<?> getNewsById(@RequestParam Integer id){
//       return ResponseEntity.ok().body(newsMapper.findAllByNewsId(id));
//    }

    //初始化新闻页面的信息，防止初始化页面请求次数过多
    @GetMapping("/init")
    public ResponseEntity<?> initNews(){
        return ResponseEntity.ok().body(newsService.Init());
    }

    // 返回所有新闻
    @GetMapping("/allnews")
//    public ResponseEntity<?> getAllNews(){
//        return ResponseEntity.ok().body(newsService.getAllNews());
//    }
    public List<NewsDTO> getAllNews(){
        return newsService.getAllNewsDTO();
    }


    // 按照新闻的两个Tag返回新闻列表，num代表返回数量
    @GetMapping("/news")
    public ResponseEntity<?> getNews(@RequestParam String Tag1,String Tag2,Integer num){
        return ResponseEntity.ok().body(newsService.getNewsInfo(Tag1,Tag2,num));
    }

    // 按照新闻的两个Tag返回视频列表，num代表返回数量
    @GetMapping("/video")
    public ResponseEntity<?> getVideo(@RequestParam String Tag1,String Tag2,Integer num){
        return ResponseEntity.ok().body(newsService.getVideo(Tag1,Tag2,num));
    }

//    //按tag筛选新闻
//    @GetMapping("/SelectNews")
//    public ResponseEntity<?> selectNews(@RequestParam String Tag1,String Tag2){
//        return ResponseEntity.ok().body(newsService.getNewsInfo(Tag1,Tag2));
//    }
//
//    //按tag筛选视频
//    @GetMapping("/SelectVideo")
//    public ResponseEntity<?> selectVideo(@RequestParam String Tag1,String Tag2){
//        return ResponseEntity.ok().body(newsService.getVideo(Tag1,Tag2));
//    }

    //搜索新闻
    @GetMapping("/searchnews")
    public ResponseEntity<?> SearchNews(@RequestParam String SearchItem){
        return ResponseEntity.ok().body(newsService.searchNews(SearchItem));
    }

    //搜索视频
    @GetMapping("/searchvideo")
    public ResponseEntity<?> SearchVideo(@RequestParam String SearchItem){
        return ResponseEntity.ok().body(newsService.searchVideo(SearchItem));
    }

    //添加一个新闻
    @PostMapping("/news")
    public void AddNews(@RequestParam NewsEntity news){
        int id=newsMapper.getMaxNewId()+1;
        newsMapper.addNews(id,news.getPublishdatetime(),news.getSummary(),news.getContains(),news.getMatchtag(),news.getPropertag(),news.getTitle());
        return;
    }

    //删除一个新闻
    @DeleteMapping("/news")
    public void DeleteNews(@RequestParam Integer news_id){
        newsMapper.deleteNews(news_id);
        return;
    }


}
