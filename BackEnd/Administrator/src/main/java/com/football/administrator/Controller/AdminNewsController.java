package com.football.administrator.Controller;

import com.football.administrator.Service.Intf.SystemInfoService;
import com.football.administrator.model.Result;
import com.football.mfapi.client.NewsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/news")
@Slf4j
public class AdminNewsController {
    @Autowired
    NewsClient newsClient;

    @GetMapping("")
    public Result getNews(){
        return Result.success(newsClient.getNews());
    }

    @DeleteMapping("/delete/{news_id}")
    public Result deleteNews(@PathVariable("news_id") Integer news_id){
        newsClient.deleteNews(news_id);
        return Result.success();
    }
}
