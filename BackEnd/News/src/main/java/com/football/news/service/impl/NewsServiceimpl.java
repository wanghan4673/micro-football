package com.football.news.service.impl;

import com.football.mfapi.dto.NewsDTO;
import com.football.news.mapper.NewsMapper;
import com.football.news.model.Entity.NewsEntity;
import com.football.news.model.Info.NewsEntityInfo;
import com.football.news.model.Info.InitInfo;
import com.football.news.model.Entity.NewshavepictureEntity;
import com.football.news.model.Entity.VideoEntity;
import com.football.news.service.intf.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    @Override
    public List<NewsEntityInfo> addVoidPicForNews(List<NewsEntity> newsWithoutPic){
        List<NewsEntityInfo> newsWithPicList = new ArrayList<>();
        for(NewsEntity news: newsWithoutPic) {
            NewsEntityInfo newsWithPic = new NewsEntityInfo(news.getNewsId(),news.getTitle(),news.getPublishdatetime(),news.getSummary(),news.getContains(),news.getMatchtag(),news.getPropertag(), null);
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
    @Override
    public List<NewsEntity> randomNewsEntity(List<NewsEntity> list, Integer num){
        List<NewsEntity> newList = new ArrayList<> ();
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

    @Override
    //将新闻转换为DTO
    public List<NewsDTO> convertToDTOList(List<NewsEntityInfo> newsEntityInfoList) {
        List<NewsDTO> newsDTOList = new ArrayList<>();

        for (NewsEntityInfo newsEntityInfo : newsEntityInfoList) {
            int news_id = newsEntityInfo.getNewsId();
            String title = newsEntityInfo.getTitle();
            Timestamp publishTime = newsEntityInfo.getPublishdatetime();
            String summary = newsEntityInfo.getSummary();

            NewsDTO newsDTO = new NewsDTO(news_id, title, publishTime, summary);

            newsDTOList.add(newsDTO);
        }

        return newsDTOList;
    }

    //获取所有新闻
    @Override
    public List<NewsEntityInfo> getAllNews(){
        List<NewsEntity> allNews=new ArrayList<>();
        allNews=newsMapper.getAllNews();
        return this.addPicForNews(allNews);
    }

    //获取所有新闻
    @Override
    public List<NewsEntity> getAllNewsEntity(){
        return newsMapper.getAllNews();
    }

    @Override
    public List<NewsDTO> getAllNewsDTO(){
        return convertToDTOList(getAllNews());
    }


    //按照Tag筛选新闻
    @Override
    public List<NewsEntityInfo> getNewsInfo(String Tag1, String Tag2){
        List<NewsEntity> newsWithoutPic=new ArrayList<>();
        if(Tag1.equals("")&&Tag2.equals(""))
            newsWithoutPic=newsMapper.getAllNews();
        else if(Tag1.equals(""))
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
        if(Tag1.equals("")&&Tag2.equals(""))
            newsWithoutPic=newsMapper.getAllNews();
        else if(Tag1.equals(""))
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
    //从所有新闻的列表里筛选满足tag的元素
    @Override
    public List<NewsEntityInfo> filterNewsByTags(List<NewsEntityInfo> newsList, String tag1, String tag2, Integer num) {
        List<NewsEntityInfo> newsWithPic=new ArrayList<>();
        if(tag1.equals("")&&tag2.equals(""))
            newsWithPic=newsList;
        else if(tag1.equals(""))
            newsWithPic=newsList.stream()
                    .filter(news -> tag2.equals(news.getPropertag()))
                    .collect(Collectors.toList());
        else if (tag2.equals(""))
            newsWithPic=newsList.stream()
                    .filter(news -> tag1.equals(news.getMatchtag()))
                    .collect(Collectors.toList());
        else
            newsWithPic=newsList.stream()
                    .filter(news -> tag1.equals(news.getMatchtag()) && tag2.equals(news.getPropertag()))
                    .collect(Collectors.toList());
        if(num==-1)
            return newsWithPic;
        else
            return this.randomNews(newsWithPic,num);
    }
    @Override
    public List<NewsEntity> filterNewsEntityByTags(List<NewsEntity> newsList, String tag1, String tag2, Integer num) {
        List<NewsEntity> newsWithPic=new ArrayList<>();
        if(tag1.equals("")&&tag2.equals(""))
            newsWithPic=newsList;
        else if(tag1.equals(""))
            newsWithPic=newsList.stream()
                    .filter(news -> tag2.equals(news.getPropertag()))
                    .collect(Collectors.toList());
        else if (tag2.equals(""))
            newsWithPic=newsList.stream()
                    .filter(news -> tag1.equals(news.getMatchtag()))
                    .collect(Collectors.toList());
        else
            newsWithPic=newsList.stream()
                    .filter(news -> tag1.equals(news.getMatchtag()) && tag2.equals(news.getPropertag()))
                    .collect(Collectors.toList());
        if(num==-1)
            return newsWithPic;
        else
            return this.randomNewsEntity(newsWithPic,num);
    }


    //按照Tag筛选视频
    @Override
    public List<VideoEntity> getVideo(String Tag1, String Tag2){
        if(Tag1.equals("")&&Tag2.equals(""))
            return newsMapper.getAllVideo();
        else if(Tag1.equals(""))
            return newsMapper.findVideoProperTag(Tag2);
        else if (Tag2.equals(""))
            return newsMapper.findVideoMatchTag(Tag1);
        else
            return newsMapper.findVideoTag(Tag1,Tag2);
    }
    @Override
    public List<VideoEntity> getVideo(String Tag1, String Tag2, Integer num){
        List<VideoEntity> List=new ArrayList<>();
        if(Tag1.equals("")&&Tag2.equals(""))
            List=newsMapper.getAllVideo();
        else if(Tag1.equals(""))
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

    //初始化新闻
    @Override
    public InitInfo Init(){
        InitInfo result=new InitInfo();
        result.news=new ArrayList<List<NewsEntityInfo>>();
        result.videos=new ArrayList<List<VideoEntity>>();

        List<NewsEntity> newsTotal=getAllNewsEntity();

        List<NewsEntity> assist1=filterNewsEntityByTags(newsTotal,"","普通",19);
        result.news.add(addPicForNews(assist1.subList(0,4)));   //需要图片
        result.news.add(addPicForNews(assist1.subList(4,6)));   //需要图片
        result.news.add(addVoidPicForNews(assist1.subList(6,8)));

        result.news.add(addVoidPicForNews(filterNewsEntityByTags(newsTotal,"中超","普通",4)));
        result.news.add(addVoidPicForNews(filterNewsEntityByTags(newsTotal,"英超","普通",4)));
        result.news.add(addVoidPicForNews(filterNewsEntityByTags(newsTotal,"西甲","普通",4)));
        result.news.add(addVoidPicForNews(filterNewsEntityByTags(newsTotal,"德甲","普通",4)));
        result.news.add(addVoidPicForNews(filterNewsEntityByTags(newsTotal,"意甲","普通",4)));
        result.news.add(addVoidPicForNews(filterNewsEntityByTags(newsTotal,"法甲","普通",4)));


        List<NewsEntity> assist2=filterNewsEntityByTags(newsTotal,"","八卦",11);
        result.news.add(addPicForNews(assist2.subList(0, 4)));   //需要图片
        result.news.add(addPicForNews(assist2.subList(4, 7)));   //需要图片
        result.news.add(addVoidPicForNews(assist2.subList(7, 11)));

        //视频数据
        List<VideoEntity> assist3=getVideo("","",11);
        result.videos.add(assist3.subList(0,4));
        result.videos.add(assist3.subList(4,6));
        result.videos.add(assist3.subList(6,11));

        return result;
    }


    //搜索新闻
    @Override
    public List<NewsEntityInfo> searchNews(String searchItem){
        //SearcherNews searchNewsSys=new SearcherNews();
        //return searchNewsSys.search(searchItem, this.addPicForNews(newsMapper.getAllNews()));
        return this.addPicForNews(newsMapper.searchNews(searchItem));
    }

    //搜索视频
    @Override
    public List<VideoEntity> searchVideo(String searchItem){
        return newsMapper.searchVideo(searchItem);
    }


}
