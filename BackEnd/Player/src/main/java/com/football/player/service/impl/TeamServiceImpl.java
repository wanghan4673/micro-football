package com.football.player.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.football.player.Api.SearchSquadsApi;
import com.football.player.Api.SearchTeamApi;
import com.football.player.mapper.TeamMapper;
import com.football.player.model.*;
import com.football.player.service.intf.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private TeamMapper teamMapper;
    @Override
    public List<TeamSimpleInfo> getTeamsByKeyword(String searchKey){
        System.out.println("=================== 检索队伍 ===================");
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
                teamMapper.updateTeam(teamDto);
                teams.add(teamDto);
            }

            System.out.println("检索队伍结果数量："+teams.size());

            return teams;
        }

        // 否则返回空列表
        return new ArrayList<>();
    }

    /**
     * 分页返回数据库中的球队信息
     * @param page 页数
     * @param size 每页个数
     */
    @Override
    public TeamList getAllTeams(Integer page, Integer size){
        Integer offset = (page - 1) * size;
        TeamList teamList = new TeamList();
        teamList.setCount(teamMapper.count());
        teamList.setTeamSimpleInfos(teamMapper.selectTeams(offset, size));
        return teamList;
    }

    @Override
    public TeamDetailInfo getTeamDetailById(Integer id){
        System.out.println("=================== 查询队伍详情 ===================");
        TeamDetailInfo teamDetailInfo=new TeamDetailInfo();
        System.out.println("=================== 检查数据库中是否存在 ===================");
        teamDetailInfo = teamMapper.getTeam(id);
        //若无获取详情，venueName等为NULL
        if(teamDetailInfo!=null && teamDetailInfo.getVenueName()!=null) {
            List<TeamDetailInfo.PlayerInfo> playersDB = new ArrayList<>();
            playersDB = teamMapper.getMember(id);
            teamDetailInfo.setPlayers(playersDB);
            return teamDetailInfo;
        }
        System.out.println("=================== 调用外部api获取对象 ===================");
        // 调用外部api获取对象

        // 首先是基本信息
        ResponseEntity<?> responseEntityFirst = searchTeamApi.getTeamSimpleInfoById(id);
        Map responseBody=new HashMap<>();
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

                teamMapper.updateTeamDetail(teamDetailInfo);
            }

        }

        System.out.println("开始获取阵容...");

        //接下来获取阵容
        List<TeamDetailInfo.PlayerInfo> playerInfos = new ArrayList<>();
        ResponseEntity<?> responseEntitySecond = searchSquadsApi.getTeamMembersById(id);
        if (responseEntitySecond.getStatusCode().is2xxSuccessful() && responseEntitySecond.getBody() != null) {
            try {
                responseBody = new ObjectMapper().readValue(responseEntitySecond.getBody().toString(), Map.class);
                System.out.println(responseBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Map<String, Object>> responseList = (List<Map<String, Object>>) responseBody.get("response");
            if (!responseList.isEmpty()) { // 检查 responseList 是否非空
                List<Map<String, Object>> playersList = (List<Map<String, Object>>) responseList.get(0).get("players");
                if (playersList != null && !playersList.isEmpty()) { // 检查 playersList 是否非空
                    for (Map<String, Object> playerMap : playersList) {
                        TeamDetailInfo.PlayerInfo playerInfo = new TeamDetailInfo.PlayerInfo();

                        System.out.println(playerMap);

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
                        //更新team member
                        teamMapper.updateMember(playerInfo, id);
                        playerInfos.add(playerInfo);
                    }
                }
            }
        }
        teamDetailInfo.setPlayers(playerInfos);
        teamDetailInfo.show();

        return teamDetailInfo;
    }

}
