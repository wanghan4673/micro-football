package com.football.news.mapper;

import com.football.news.model.NewsEntity;
import com.football.news.model.NewsEntityInfo;
import com.football.news.model.NewshavepictureEntity;
import com.football.news.model.VideoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface NewsMapper{
    //按照id选出单条新闻（Test）
    @Select("""                                                                                       
             SELECT*                                                                    
             FROM news                                                                                 
             WHERE                                                                                     
                 news_id=#{NewsId}                                    
         """)
    List<NewsEntity> findAllByNewsId(@Param("NewsId") Integer NewsId);

    //按照id选出该条新闻对应的图片
    @Select("""
            SELECT*
            FROM newshavepicture
            WHERE
                news_id=#{NewsId}
            """)
    List<NewshavepictureEntity> findPictureById(@Param("NewsId") Integer NewsId);
     
    //按照两个tag筛选新闻，返回一个匹配的新闻列表
    @Select("""
            SELECT*
            FROM news
            WHERE
                matchtag = #{tag1} AND propertag = #{tag2}
        """)
    List<NewsEntity> findNewsTag(@Param("tag1") String tag1, @Param("tag2")  String tag2);

    //按照matchtag筛选新闻，返回一个匹配的新闻列表
    @Select("""                                                                            
            SELECT*                                                                        
            FROM news                                                                      
            WHERE                                                                          
                matchtag = #{tag1}                                
        """)
    List<NewsEntity> findNewsMatchTag(@Param("tag1") String tag1);


    //按照propertag筛选新闻，返回一个匹配的新闻列表
    @Select("""                                                                   
            SELECT*                                                               
            FROM news                                                             
            WHERE                                                                 
                propertag = #{tag2}                                                
        """)
    List<NewsEntity> findNewsProperTag(@Param("tag2") String tag2);


    //按照两个tag筛选视频，返回一个匹配的视频列表
    @Select("""                                                                              
             SELECT*                                                                          
             FROM video                                                                        
             WHERE                                                                            
                 matchtag = #{tag1} AND propertag = #{tag2}                                   
         """)
    List<VideoEntity> findVideoTag(@Param("tag1") String tag1, @Param("tag2")  String tag2);

    //按照matchtag筛选视频，返回一个匹配的视频列表
    @Select("""                                                                             
             SELECT*                                                                        
             FROM video                                                                     
             WHERE                                                                          
                 matchtag = #{tag1}                                 
         """)
    List<VideoEntity> findVideoMatchTag(@Param("tag1") String tag1);

    //按照matchtag筛选视频，返回一个匹配的视频列表
    @Select("""                                                      
             SELECT*                                                 
             FROM video                                              
             WHERE                                                   
                 propertag = #{tag2}                                  
         """)
    List<VideoEntity> findVideoProperTag(@Param("tag2") String tag2);
}
