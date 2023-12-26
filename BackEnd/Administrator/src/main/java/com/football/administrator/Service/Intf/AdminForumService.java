package com.football.administrator.Service.Intf;

import com.football.administrator.model.ForumReport;

import java.util.List;

public interface AdminForumService {

    List<ForumReport> getReportList();

    void deleteReport(Integer id);

    void postReport(String reporterName, String reason, Integer postId);
}
