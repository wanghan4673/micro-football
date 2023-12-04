package com.football.administrator.Service.Impl;

import com.football.administrator.Mapper.AnnouncementMapper;
import com.football.administrator.Service.Intf.AnnouncementService;
import com.football.administrator.model.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    AnnouncementMapper announcementmapper;

    @Override
    public void postAnnouncement(String title, String detail) {
        LocalDateTime time = LocalDateTime.now();
        announcementmapper.postAnnouncement(title,detail,time);
    }

    @Override
    public List<Announcement> getAnnouncementList() {
        return announcementmapper.getAnnouncementList();
    }
}
