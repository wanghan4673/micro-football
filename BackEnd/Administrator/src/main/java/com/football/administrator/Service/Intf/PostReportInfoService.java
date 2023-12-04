package com.football.administrator.Service.Intf;

import com.football.administrator.model.PostReportInfo;

import java.util.List;

public interface PostReportInfoService {
    void postReport(Integer reportedPostId, String reason, Integer reportUserId);

    List<PostReportInfo> getReportList();

    void deleteReport(Integer id);
}
