package com.football.news.mapper;

import com.football.news.model.News;
import com.football.news.model.NewsDetail;
import com.football.news.model.PictureNews;
import com.football.news.model.VideoNews;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsMapper {
    @Select({
            "SELECT id,title,content,tags,createtime",
            "FROM news ",
            "WHERE haspic = 0",
            "LIMIT #{start}, #{pageSize}"
    })
    List<News> getTextNewsByPage(Integer start, Integer pageSize);

    @Select("SELECT n.*, GROUP_CONCAT(DISTINCT pi.picurl) AS picUrls " +
            "FROM news n " +
            "LEFT JOIN picture pi ON n.id = pi.newsid " +
            "WHERE n.haspic = 1 " +
            "ORDER BY RAND() " +
            "LIMIT #{count}")
    List<PictureNews> getRandomPicNews(Integer count);

    @Select("SELECT n.*, GROUP_CONCAT(DISTINCT vi.videourl) AS videoUrls " +
            "FROM news n " +
            "LEFT JOIN video vi ON n.id = vi.newsid " +
            "WHERE n.haspic = 1 " +
            "ORDER BY RAND() " +
            "LIMIT #{count}")
    List<VideoNews> getRandomVideoNews(Integer count);

    @Select("SELECT n.tags AS tags, " +
            "GROUP_CONCAT(DISTINCT p.picurl) AS picUrl " +
            "FROM news n " +
            "LEFT JOIN picture p ON n.id = p.newsid " +
            "WHERE n.id = #{newsId}")
    NewsDetail getNewsById(Long newsId);

    @Select("SELECT id FROM newsreport WHERE reporterid = #{userId} AND newsid = #{newsId}")
    Boolean getReportStatus(Long newsId, Long userId);

    @Select("SELECT * FROM news")
    List<News> getAllNews();
}
