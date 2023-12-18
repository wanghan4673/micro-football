package com.football.administrator.Service.Impl;

import com.football.administrator.Mapper.AnnouncementMapper;
import com.football.administrator.Mapper.SystemInfoMapper;
import com.football.administrator.Service.Intf.AnnouncementService;
import com.football.mfapi.dto.AnnouncementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    AnnouncementMapper announcementmapper;
    @Autowired
    SystemInfoMapper systemInfoMapper;

    @Override
    public void postAnnouncement(String title, String detail,Integer receiver) {
        LocalDateTime time = LocalDateTime.now();
        LocalDate DateTime = LocalDate.now();
        systemInfoMapper.postNewAnnounce(DateTime);
        announcementmapper.postAnnouncement(title,detail,time,receiver);
    }

    @Override
    public List<AnnouncementDTO> getAnnouncementList(Long id) {
        return announcementmapper.getAnnouncementList(id);
    }
}
