package com.football.news.service.impl;

import com.football.news.mapper.NewsMapper;
import com.football.news.model.NewsEntity;
import com.football.news.model.NewsEntityInfo;
import com.football.news.model.NewshavepictureEntity;
import com.football.news.model.VideoEntity;
import com.football.news.service.intf.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class NewsServiceimpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;

    //将无图片的新闻与其图片相连构成NewEntityInfo
    @Override
    public List<NewsEntityInfo> addPicForNews(List<NewsEntity> newsWithoutPic){
        List<NewsEntityInfo> newsWithPicList = new ArrayList<>();
        for(NewsEntity news: newsWithoutPic) {
            List<NewshavepictureEntity> pic = newsMapper.findPictureById(news.getNewsId());
            List<String> picList=new ArrayList<>();
            for(NewshavepictureEntity item: pic){
                picList.add(item.getPictureroute());
            }
            NewsEntityInfo newsWithPic = new NewsEntityInfo(news.getNewsId(),news.getTitle(),news.getPublishdatetime(),news.getSummary(),news.getContains(),news.getMatchtag(),news.getPropertag(), picList);
            newsWithPicList.add(newsWithPic);
        }
        return newsWithPicList;
    }

    //从新闻列表中随机取n个元素组成新列表
    @Override
    public List<NewsEntityInfo> randomNews(List<NewsEntityInfo> list, Integer num){
        List<NewsEntityInfo> newList = new ArrayList<> ();
        Collections.shuffle (list);
        newList = list.subList (0, num);
        return newList;
    }

    //从视频列表中随机取n个元素组成新列表
    @Override
    public List<VideoEntity> randomVideo(List<VideoEntity> list, Integer num){
        List<VideoEntity> newList = new ArrayList<> ();
        Collections.shuffle (list);
        newList = list.subList (0, num);
        return newList;
    }

    //按照Tag筛选新闻
    @Override
    public List<NewsEntityInfo> getNewsInfo(String Tag1, String Tag2){
        List<NewsEntity> newsWithoutPic=new ArrayList<>();
        if(Tag1.equals(""))
            newsWithoutPic=newsMapper.findNewsProperTag(Tag2);
        else if (Tag2.equals(""))
            newsWithoutPic=newsMapper.findNewsMatchTag(Tag1);
        else
            newsWithoutPic=newsMapper.findNewsTag(Tag1,Tag2);
        return this.addPicForNews(newsWithoutPic);
    }
    @Override
    public List<NewsEntityInfo> getNewsInfo(String Tag1, String Tag2, Integer num){
        List<NewsEntity> newsWithoutPic=new ArrayList<>();
        if(Tag1.equals(""))
            newsWithoutPic=newsMapper.findNewsProperTag(Tag2);
        else if (Tag2.equals(""))
            newsWithoutPic=newsMapper.findNewsMatchTag(Tag1);
        else
            newsWithoutPic=newsMapper.findNewsTag(Tag1,Tag2);
        if(num==-1)
            return this.addPicForNews(newsWithoutPic);
        else
            return this.randomNews(this.addPicForNews(newsWithoutPic),num);
    }


    //按照Tag筛选视频
    @Override
    public List<VideoEntity> getVideo(String Tag1, String Tag2){
        if(Tag1.equals(""))
            return newsMapper.findVideoProperTag(Tag2);
        else if (Tag2.equals(""))
            return newsMapper.findVideoMatchTag(Tag1);
        else
            return newsMapper.findVideoTag(Tag1,Tag2);
    }
    @Override
    public List<VideoEntity> getVideo(String Tag1, String Tag2, Integer num){
        List<VideoEntity> List=new ArrayList<>();
        if(Tag1.equals(""))
            List=newsMapper.findVideoProperTag(Tag2);
        else if (Tag2.equals(""))
            List=newsMapper.findVideoMatchTag(Tag1);
        else
            List=newsMapper.findVideoTag(Tag1,Tag2);
        if(num==-1)
            return List;
        else
            return this.randomVideo(List,num);
    }


    //搜索新闻
    @Override
    public List<NewsEntityInfo> searchNews(String searchItem){
        List<NewsEntityInfo> newsWithPicList = new ArrayList<>();
        return newsWithPicList;
    }

    //搜索视频
    @Override
    public List<VideoEntity> searchVideo(String searchItem){
        List<VideoEntity> VideoList = new ArrayList<>();
        return VideoList;
    }


}
