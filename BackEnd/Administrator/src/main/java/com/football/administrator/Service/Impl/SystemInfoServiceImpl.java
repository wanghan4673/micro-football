package com.football.administrator.Service.Impl;

import com.football.administrator.Mapper.SystemInfoMapper;
import com.football.administrator.Service.Intf.SystemInfoService;
import com.football.administrator.model.SystemInfo;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SystemInfoServiceImpl implements SystemInfoService {

    @Autowired
    SystemInfoMapper systemInfoMapper;
    @Override
    public void postNewUser() {
        LocalDate time = LocalDate.now();
        List<LocalDate> allTime = systemInfoMapper.getAllTime();
        if(allTime.contains(time)){
            systemInfoMapper.postNewUser(time);
        }else {
            systemInfoMapper.addNewRow(time);
            systemInfoMapper.postNewUser(time);
        }
    }

    @Override
    public void postNewNews() {
        LocalDate time = LocalDate.now();
        List<LocalDate> allTime = systemInfoMapper.getAllTime();
        if(allTime.contains(time)){
            systemInfoMapper.postNewNews(time);
        }else {
            systemInfoMapper.addNewRow(time);
            systemInfoMapper.postNewNews(time);
        }
    }

    @Override
    public void postNewPost() {
        LocalDate time = LocalDate.now();
        List<LocalDate> allTime = systemInfoMapper.getAllTime();
        if(allTime.contains(time)){
            systemInfoMapper.postNewPost(time);
        }else {
            systemInfoMapper.addNewRow(time);
            systemInfoMapper.postNewPost(time);
        }
    }

    @Override
    public void postNewAnnounce() {
        LocalDate time = LocalDate.now();
        List<LocalDate> allTime = systemInfoMapper.getAllTime();
        if(allTime.contains(time)){
            systemInfoMapper.postNewAnnounce(time);
        }else {
            systemInfoMapper.addNewRow(time);
            systemInfoMapper.postNewAnnounce(time);
        }
    }

    @Override
    public SystemInfo getSysInfo() {
        return systemInfoMapper.getSysInfo();
    }
}
