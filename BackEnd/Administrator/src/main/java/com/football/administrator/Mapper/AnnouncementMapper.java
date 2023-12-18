package com.football.administrator.Mapper;

import com.football.administrator.model.Announcement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AnnouncementMapper {
    @Insert("INSERT INTO announcement (title,detail,time,receiver) VALUES (#{title},#{detail},#{time}, #{receiver})")
    void postAnnouncement(String title, String detail, LocalDateTime time,Integer receiver);

    @Select("SELECT * FROM announcement WHERE receiver = #{id}")
    List<Announcement> getAnnouncementList(Integer id);
}
