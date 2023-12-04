package com.football.administrator.Mapper;

import com.football.administrator.model.PostReportInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PostReportInfoMapper {
    @Insert("INSERT INTO post_report_info (reported_post_id, reason, report_user_id, time) " +
            "VALUES (#{reportedPostId},#{reason},#{reportUserId},#{time})")
    void postReport(Integer reportedPostId, String reason, Integer reportUserId, LocalDateTime time);

    @Select("SELECT * FROM post_report_info")
    List<PostReportInfo> getReportList();

    @Delete("DELETE FROM post_report_info WHERE id = #{id}")
    void deleteReport(Integer id);
}
