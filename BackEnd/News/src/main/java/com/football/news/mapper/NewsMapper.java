package com.football.news.mapper;

import com.football.news.model.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsMapper {
    @Select({
            "SELECT id,title,content,tags,createtime",
            "FROM news",
            "LIMIT #{start}, #{pageSize}"
    })
    List<News> getNewsByPage(Integer start, Integer pageSize);

    @Select("SELECT title,content,tags,createtime FROM news WHERE id = #{newsId}")
    News getNewsById(Long newsId);

    @Select("SELECT id FROM newsreport WHERE reporterid = #{userId} AND newsid = #{newsId}")
    Boolean getReportStatus(Long newsId, Long userId);

    @Select("SELECT * FROM news")
    List<News> getAllNews();
}
