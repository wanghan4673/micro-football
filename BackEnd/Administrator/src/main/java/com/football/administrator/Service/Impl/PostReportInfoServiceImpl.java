package com.football.administrator.Service.Impl;

import com.football.administrator.Mapper.PostReportInfoMapper;
import com.football.administrator.Service.Intf.PostReportInfoService;
import com.football.administrator.model.PostReportInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostReportInfoServiceImpl implements PostReportInfoService {
    @Autowired
    PostReportInfoMapper postReportInfoMapper;
    @Override
    public void postReport(Integer reportedPostId, String reason, Integer reportUserId) {
        LocalDateTime time = LocalDateTime.now();
        postReportInfoMapper.postReport(reportedPostId,reason,reportUserId,time);
    }

    @Override
    public List<PostReportInfo> getReportList() {
        return postReportInfoMapper.getReportList();
    }

    @Override
    public void deleteReport(Integer id) {
        postReportInfoMapper.deleteReport(id);
    }
}
