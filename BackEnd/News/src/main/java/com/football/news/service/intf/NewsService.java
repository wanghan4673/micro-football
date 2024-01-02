package com.football.news.service.intf;

import com.football.news.model.Entity.NewsEntity;
import com.football.news.model.Info.InitInfo;
import com.football.news.model.Info.NewsEntityInfo;
import com.football.news.model.Entity.VideoEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService  {

    //将无图片的新闻与其图片相连构成NewEntityInfo
    List<NewsEntityInfo> addPicForNews(List<NewsEntity> newsWithoutPic);

    //从新闻列表中随机取n个元素组成新列表
    List<NewsEntityInfo> randomNews(List<NewsEntityInfo> list, Integer num);

    //从视频列表中随机取n个元素组成新列表
    List<VideoEntity> randomVideo(List<VideoEntity> list, Integer num);

    //获取所有新闻
    List<NewsEntityInfo> getAllNews();

    //按Tag获取新闻
    List<NewsEntityInfo> getNewsInfo(String Tag1, String Tag2);

    //按照Tag筛选新闻
    List<NewsEntityInfo> getNewsInfo(String Tag1, String Tag2, Integer num);

    //从所有新闻的列表里筛选满足tag的元素
    List<NewsEntityInfo> filterNewsByTags(List<NewsEntityInfo> newsList, String tag1, String tag2, Integer num);

    //按照Tag筛选视频
    List<VideoEntity> getVideo(String Tag1, String Tag2);

    List<VideoEntity> getVideo(String Tag1, String Tag2, Integer num);

    //初始化新闻
    InitInfo Init();

    //搜索新闻
    List<NewsEntityInfo> searchNews(String searchItem);

    //搜索视频
    List<VideoEntity> searchVideo(String searchItem);
}
