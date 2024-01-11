package com.football.news.controller;

import com.football.common.utils.UserContext;
import com.football.news.model.*;
import com.football.news.service.intf.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/news/text")
    public Result getTextNews(@RequestParam("page") Integer page,
                          @RequestParam("pageSize") Integer pageSize){
        log.info("----------------------获取仅含文本的新闻------------------------");
        try{
            List<News> newsResult = newsService.getTextNewsByPage(page,pageSize);
            return Result.success(newsResult);
        } catch (Exception e){
            return Result.error("errorGetNews");
        }
    }

    @GetMapping("/news/picture")
    public Result getPicNews(@RequestParam("count") Integer count){
        log.info("----------------------获取n个随机的图片新闻------------------------");
        try{
            List<PictureNews> newsResult = newsService.getRandomPicNews(count);
            return Result.success(newsResult);
        } catch (Exception e){
            return Result.error("errorGetPicNews");
        }
    }

    @GetMapping("/news/video")
    public Result getVideoNews(@RequestParam("count") Integer count){
        log.info("----------------------获取n个随机的视频新闻------------------------");
        try{
            List<VideoNews> newsResult = newsService.getRandomVideoNews(count);
            return Result.success(newsResult);
        } catch (Exception e){
            return Result.error("errorGetVideoNews");
        }
    }

    @GetMapping("/news/detail/{id}")
    public Result getNewsDetail(@PathVariable("id") Long newsId){
        log.info("----------------------查看新闻详情------------------------");
        NewsDetail newsDetail = newsService.getNewsById(newsId);
        return Result.success(newsDetail);
    }

    @GetMapping("/token-news/detail/{id}/report-status")
    public Result getReportStatus(@PathVariable("id") Long newsId){
        log.info("----------------------判断用户对新闻的举报状态------------------------");
        Long userId = UserContext.getUser();
        if(userId == null){
            return Result.success("noLogin");
        } else{
            return Result.success(newsService.getReportStatus(newsId, userId) ? "isReported" : "noReported");
        }
    }

    @PostMapping("/token-news/report")
    public Result reportNews(@RequestParam("name") String userName,
                             @RequestParam("newsId") Long newsId,
                             @RequestParam("content") String content){
        log.info("----------举报新闻----------");
        Long userId = UserContext.getUser();
        if(userId == null){
            // 未登录无法举报
            return Result.success("noLogin");
        } else{
            if(newsService.insertReportedNews(userId, userName,newsId,content))
                return Result.success();
            else
                return Result.error("reportError");
        }
    }

    @GetMapping("/news/all")
    public List<News> getAllNews(){
        log.info("----------获取所有新闻(为管理员准备)----------");
        return newsService.getAllNews();
    }

    @GetMapping("/news/reported")
    public List<ReportedNews> getReportedNews(){
        log.info("----------获取所有被举报过的新闻(为管理员准备)----------");
        return newsService.getReportedNews();
    }

    @DeleteMapping("/news")
    public Boolean deleteNews(@RequestParam("id") Long id){
        log.info("----------删除新闻(为管理员准备)----------");
        return newsService.deleteNews(id);
    }
}
