package com.football.administrator.Service.Intf;

import com.football.administrator.model.Announcement;

import java.util.List;

public interface AnnouncementService {
    void postAnnouncement(String title, String detail);

    List<Announcement> getAnnouncementList();
}
