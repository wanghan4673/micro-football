package com.football.administrator.Mapper;

import com.football.mfapi.dto.AnnouncementDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AnnouncementMapper {
    @Insert("INSERT INTO announcement (title,detail,time,receiver) VALUES (#{title},#{detail},#{time}, #{receiver})")
    void postAnnouncement(String title, String detail, LocalDateTime time,Long receiver);

    @Select("SELECT * FROM announcement WHERE receiver = #{id} or receiver = 0")
    List<AnnouncementDTO> getAnnouncementList(Long id);
}
