package com.football.player.service.intf;

import com.football.player.model.PlayerSimpleInfo;
import com.football.player.model.TeamSimpleInfo;

import java.util.List;

public interface TeamService {

    List<TeamSimpleInfo> getTeamsByKeyword(String searchKey);
}
