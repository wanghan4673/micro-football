package com.football.administrator.Service.Intf;

import com.football.mfapi.dto.AnnouncementDTO;

import java.util.List;

public interface AnnouncementService {
    void postAnnouncement(String title, String detail,Integer receiver);

    List<AnnouncementDTO> getAnnouncementList(Long id);
}
