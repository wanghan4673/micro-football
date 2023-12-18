package com.football.administrator.Mapper;

import com.football.administrator.model.SystemInfo;
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

    @Select("SELECT DATE(datetime_column) AS DateOnly,\n" +
            "       SUM(new_news) AS TotalNewNews,\n" +
            "       SUM(new_user) AS TotalNewUsers\n" +
            "FROM your_table_name\n" +
            "WHERE datetime_column >= CURDATE() - INTERVAL 30 DAY\n" +
            "GROUP BY DATE(datetime_column)\n" +
            "ORDER BY DateOnly DESC;\n")
    SystemInfo getSysInfo();
}
