package com.football.administrator.Mapper;

import com.football.administrator.model.Announcement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AnnouncementMapper {
    @Insert("INSERT INTO announcement (title,detail,time) VALUES (#{title},#{detail},#{time})")
    void postAnnouncement(String title, String detail, LocalDateTime time);

    @Select("SELECT * FROM announcement")
    List<Announcement> getAnnouncementList();
}
