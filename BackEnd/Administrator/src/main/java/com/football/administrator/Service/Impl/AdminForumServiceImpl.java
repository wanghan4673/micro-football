package com.football.administrator.Service.Impl;

import com.football.administrator.Mapper.AdminForumMapper;
import com.football.administrator.Service.Intf.AdminForumService;
import com.football.administrator.model.PostReportInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminForumServiceImpl implements AdminForumService {
    @Autowired
    AdminForumMapper adminForumMapper;
    @Override
    public void postReport(Integer reportedPostId, String reason, Integer reportUserId) {
        LocalDateTime time = LocalDateTime.now();
        adminForumMapper.postReport(reportedPostId,reason,reportUserId,time);
    }

    @Override
    public List<PostReportInfo> getReportList() {
        return adminForumMapper.getReportList();
    }

    @Override
    public void deleteReport(Integer id) {
        adminForumMapper.deleteReport(id);
    }
}
