package com.football.player.service.intf;

import com.football.player.model.PlayerSimpleInfo;
import com.football.player.model.TeamDetailInfo;
import com.football.player.model.TeamList;
import com.football.player.model.TeamSimpleInfo;

import java.util.List;

public interface TeamService {

    List<TeamSimpleInfo> getTeamsByKeyword(String searchKey);

    TeamList getAllTeams(Integer page, Integer size);

    TeamDetailInfo getTeamDetailById(Integer id);
}
