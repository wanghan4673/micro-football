package com.football.administrator.Service.Impl;

import com.football.administrator.Mapper.AdminForumMapper;
import com.football.administrator.Service.Intf.AdminForumService;
import com.football.administrator.model.ForumReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminForumServiceImpl implements AdminForumService {
    @Autowired
    AdminForumMapper adminForumMapper;

    @Override
    public List<ForumReport> getReportList() {
        return adminForumMapper.getReportList();
    }

    @Override
    public void deleteReport(Integer id) {
        adminForumMapper.deleteReport(id);
    }

    @Override
    public void postReport(String reporterName, String reason, Integer postId) {
        LocalDateTime time = LocalDateTime.now();
        adminForumMapper.postReport(reporterName, reason, postId,time);
    }
}
