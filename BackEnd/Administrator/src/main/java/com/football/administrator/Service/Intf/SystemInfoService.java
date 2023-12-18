package com.football.administrator.Service.Intf;

import com.football.administrator.model.SystemInfo;

public interface SystemInfoService {
    void postNewUser();

    void postNewNews();

    void postNewPost();

    void postNewAnnounce();

    SystemInfo getSysInfo();
}
