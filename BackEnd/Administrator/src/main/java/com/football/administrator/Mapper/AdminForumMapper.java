package com.football.administrator.Mapper;

import com.football.administrator.model.ForumReport;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AdminForumMapper {

    @Select("SELECT * FROM forum_report")
    List<ForumReport> getReportList();

    @Delete("DELETE FROM forum_report WHERE id = #{id}")
    void deleteReport(Integer id);

    @Insert("INSERT INTO forum_report (reporter_name,reason,post_id,time)VALUES (#{reporterName},#{reason},#{postId},#{time}) ")
    void postReport(String reporterName, String reason, Integer postId, LocalDateTime time);
}
