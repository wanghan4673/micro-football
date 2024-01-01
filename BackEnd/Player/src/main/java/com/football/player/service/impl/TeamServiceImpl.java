package com.football.player.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.football.player.Api.SearchSquadsApi;
import com.football.player.Api.SearchTeamApi;
import com.football.player.model.PlayerSimpleInfo;
import com.football.player.model.TeamDetailInfo;
import com.football.player.model.TeamSimpleInfo;
import com.football.player.service.intf.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamServiceImpl implements TeamService {
    private final SearchTeamApi searchTeamApi=new SearchTeamApi();
    private final SearchSquadsApi searchSquadsApi=new SearchSquadsApi();

    @Override
    public List<TeamSimpleInfo> getTeamsByKeyword(String searchKey){
        // 调用外部api获取对象
        ResponseEntity<?> responseEntity = searchTeamApi.getTeamsByKey(searchKey);


        Map<String, Object> responseBody=new HashMap<>();
        if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            // 通过mapper映射到map
            try {
                responseBody = new ObjectMapper().readValue(responseEntity.getBody().toString(), Map.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            List<Map<String, Object>> teamsData = (List<Map<String, Object>>) responseBody.get("response");
            // 开始添加信息
            List<TeamSimpleInfo> teams = new ArrayList<>();
            for(Map<String, Object> teamData : teamsData) {
                Map<String, Object> team = (Map<String, Object>) teamData.get("team");
                TeamSimpleInfo teamDto = new TeamSimpleInfo();
                if (team.get("id") != null) {
                    teamDto.setId((Integer) team.get("id"));
                }
                if (team.get("name") != null) {
                    teamDto.setName((String) team.get("name"));
                }
                if (team.get("country") != null) {
                    teamDto.setCountry((String) team.get("country"));
                }
                if (team.get("logo") != null) {
                    teamDto.setLogo((String) team.get("logo"));
                }
                if (team.get("founded") != null) {
                    teamDto.setFounded(team.get("founded").toString());
                }

                teams.add(teamDto);
            }

            return teams;
        }

        // 否则返回空列表
        return new ArrayList<>();
    }



    @Override
    public TeamDetailInfo getTeamDetailById(Integer id){
        TeamDetailInfo teamDetailInfo=new TeamDetailInfo();
        // 调用外部api获取对象

        // 首先是基本信息
        ResponseEntity<?> responseEntityFirst = searchTeamApi.getTeamSimpleInfoById(id);
        Map<String, Object> responseBody=new HashMap<>();
        if(responseEntityFirst.getStatusCode().is2xxSuccessful() && responseEntityFirst.getBody() != null) {
            // 通过mapper映射到map
            try {
                responseBody = new ObjectMapper().readValue(responseEntityFirst.getBody().toString(), Map.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            List<Map<String, Object>> teamsData = (List<Map<String, Object>>) responseBody.get("response");
            // 开始添加信息
            List<TeamSimpleInfo> teams = new ArrayList<>();
            for(Map<String, Object> teamData : teamsData) {
                Map<String, Object> team = (Map<String, Object>) teamData.get("team");
                if (team.get("id") != null) {
                    teamDetailInfo.setId((Integer) team.get("id"));
                }
                if (team.get("name") != null) {
                    teamDetailInfo.setName((String) team.get("name"));
                }
                if (team.get("country") != null) {
                    teamDetailInfo.setCountry((String) team.get("country"));
                }
                if (team.get("logo") != null) {
                    teamDetailInfo.setLogo((String) team.get("logo"));
                }
                if (team.get("founded") != null) {
                    teamDetailInfo.setFounded(team.get("founded").toString());
                }

                Map<String, Object> venue = (Map<String, Object>) teamData.get("venue");
                if (venue.get("name") != null) {
                    teamDetailInfo.setVenueName(venue.get("name").toString());
                }
                if (venue.get("address") != null) {
                    teamDetailInfo.setVenueAddress((String) venue.get("address"));
                }
                if (venue.get("city") != null) {
                    teamDetailInfo.setVenueCity((String) venue.get("city"));
                }
                if (venue.get("capacity") != null) {
                    teamDetailInfo.setVenueCapacity((Integer) venue.get("capacity"));
                }
                if (venue.get("image") != null) {
                    teamDetailInfo.setVenueImage(venue.get("image").toString());
                }


            }

        }



        System.out.println("开始获取阵容...");

        //接下来获取阵容
        List<TeamDetailInfo.PlayerInfo> playerInfos=new ArrayList<>();
        ResponseEntity<?> responseEntitySecond = searchSquadsApi.getTeamMembersById(id);
        if(responseEntityFirst.getStatusCode().is2xxSuccessful() && responseEntityFirst.getBody() != null){
            // 通过mapper映射到map
            try {
                responseBody = new ObjectMapper().readValue(responseEntitySecond.getBody().toString(), Map.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            List<Map<String, Object>> playersList = (List<Map<String, Object>>)((List<Map<String, Object>>) responseBody.get("response")).get(0).get("players");
//            List<Map<String, Object>> playerList = (List<Map<String, Object>>) playersList.get(0).get("players");

            for (Map<String, Object> playerMap : playersList) {
                TeamDetailInfo.PlayerInfo playerInfo = new TeamDetailInfo.PlayerInfo();

                if (playerMap.get("id") != null) {
                    playerInfo.setId((Integer) playerMap.get("id"));
                }
                if (playerMap.get("name") != null) {
                    playerInfo.setName((String) playerMap.get("name"));
                }
                if (playerMap.get("number") != null) {
                    playerInfo.setNumber((Integer) playerMap.get("number"));
                }
                if (playerMap.get("age") != null) {
                    playerInfo.setAge((Integer) playerMap.get("age"));
                }
                if (playerMap.get("position") != null) {
                    playerInfo.setPosition((String) playerMap.get("position"));
                }
                if (playerMap.get("photo") != null) {
                    playerInfo.setPhoto((String) playerMap.get("photo"));
                }

                playerInfos.add(playerInfo);
            }

        }

        teamDetailInfo.setPlayers(playerInfos);

        return teamDetailInfo;
    }

}
