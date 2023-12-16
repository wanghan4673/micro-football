package com.football.administrator.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SystemInfoMapper {

    @Select("SELECT date_time FROM system_info")
    List<LocalDate> getAllTime();


    @Insert("INSERT INTO system_info(date_time) VALUES (#{time})")
    void addNewRow(LocalDate time);

    @Update("UPDATE system_info SET new_user_num = new_user_num + 1 WHERE date_time = #{time};")
    void postNewUser(LocalDate time);

    @Update("UPDATE system_info SET new_news_num = new_news_num + 1 WHERE date_time = #{time};")
    void postNewNews(LocalDate time);

    @Update("UPDATE system_info SET new_post_num = new_post_num + 1 WHERE date_time = #{time};")
    void postNewPost(LocalDate time);

    @Update("UPDATE system_info SET new_announce_num = new_announce_num + 1 WHERE date_time = #{time};")
    void postNewAnnounce(LocalDate time);
}
