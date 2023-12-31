package com.football.player.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.football.player.Api.SearchTeamApi;
import com.football.player.model.PlayerSimpleInfo;
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

    @Override
    public List<TeamSimpleInfo> getTeamsByKeyword(String searchKey){
        // 调用外部api获取对象
        ResponseEntity<?> responseEntity = searchTeamApi.getResponseEntityByKey(searchKey);


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

}
