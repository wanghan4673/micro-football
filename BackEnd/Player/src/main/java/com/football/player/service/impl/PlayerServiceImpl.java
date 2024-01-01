package com.football.player.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.football.player.Api.SearchPlayerApi;
import com.football.player.model.PlayerDetailInfo;
import com.football.player.model.PlayerSimpleInfo;
import com.football.player.service.intf.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlayerServiceImpl implements PlayerService {


    private final SearchPlayerApi searchPlayerApi=new SearchPlayerApi();

    /**
     * 根据关键字和联赛搜素球员
     * @param searchKey 关键字
     * @param leagueId 联赛id
     * @return 球员列表
     */
    @Override
    public List<PlayerSimpleInfo> getPlayersByKeywordAndLeague(String searchKey, Integer leagueId) {
        // 调用外部api获取对象
        ResponseEntity<?> responseEntity = searchPlayerApi.getPlayersByKeyAndLeague(searchKey, leagueId);


        Map<String, Object> responseBody=new HashMap<>();
        if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            // 通过mapper映射到map
            try {
                responseBody = new ObjectMapper().readValue(responseEntity.getBody().toString(), Map.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            List<Map<String, Object>> playersData = (List<Map<String, Object>>) responseBody.get("response");
            // 开始添加信息
            List<PlayerSimpleInfo> players = new ArrayList<>();
            for(Map<String, Object> playerData : playersData) {
                Map<String, Object> player = (Map<String, Object>) playerData.get("player");
                PlayerSimpleInfo playerDto = new PlayerSimpleInfo();
                playerDto.setId(player.get("id") != null ? (Integer) player.get("id") : null);
                playerDto.setName(player.get("name") != null ? (String) player.get("name") : null);
                playerDto.setFirstName(player.get("firstname") != null ? (String) player.get("firstname") : null);
                playerDto.setLastName(player.get("lastname") != null ? (String) player.get("lastname") : null);
                playerDto.setAge(player.get("age") != null ? (Integer) player.get("age") : null);
                playerDto.setHeight(player.get("height") != null ? (String) player.get("height") : null);
                playerDto.setWeight(player.get("weight") != null ? (String) player.get("weight") : null);

                Map<String, Object> birthInfo = (Map<String, Object>) player.get("birth");
                if (birthInfo != null) {
                    playerDto.setCountry(birthInfo.get("country") != null ? (String) birthInfo.get("country") : null);
                } else {
                    playerDto.setCountry(null);
                }

                playerDto.setPhoto(player.get("photo") != null ? (String) player.get("photo") : null);


                players.add(playerDto);
            }

            return players;
        }

        // 否则返回空列表
        return new ArrayList<>();
    }


    /**
     * 根据球员id返回球员详细信息
     * @param playerId 球员id
     * @return 球员详细信息
     */
    @Override
    public PlayerDetailInfo getPlayerDetailById(Integer playerId){
        PlayerDetailInfo playerDetailInfo=new PlayerDetailInfo();
        List<PlayerDetailInfo.SeasonInfo> seasonInfos=new ArrayList<>();

        // 限定范围为2023-2013
        for(int i =2013;i<=2023;i++){
            // 调用外部api获取对象
            ResponseEntity<?> responseEntity = searchPlayerApi.getPlayerInfoByIdAndSeason(playerId, i);

            Map<String, Object> responseBody=new HashMap<>();
            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                try {
                    responseBody = new ObjectMapper().readValue(responseEntity.getBody().toString(), Map.class);
                }catch (Exception e){
                    e.printStackTrace();
                }
                List<Map<String, Object>> responseList = (List<Map<String, Object>>) responseBody.get("response");

                if (responseList != null && !responseList.isEmpty()) {
                    Map<String, Object> playerData = responseList.get(0);
                    Map<String, Object> player = (Map<String, Object>) playerData.get("player");

                    // 在此更新一下基本数据
                    if (player != null && !player.isEmpty()) {
                        if (playerDetailInfo.getId()==null) playerDetailInfo.setId(player.get("id") != null ? (Integer) player.get("id") : null);
                        if (playerDetailInfo.getName()==null) playerDetailInfo.setName(player.get("name") != null ? (String) player.get("name") : null);
                        if (playerDetailInfo.getFirstName()==null) playerDetailInfo.setFirstName(player.get("firstname") != null ? (String) player.get("firstname") : null);
                        if (playerDetailInfo.getLastName()==null) playerDetailInfo.setLastName(player.get("lastname") != null ? (String) player.get("lastname") : null);
                        if (playerDetailInfo.getAge()==null) playerDetailInfo.setAge(player.get("age") != null ? (Integer) player.get("age") : null);
                        if (playerDetailInfo.getHeight()==null) playerDetailInfo.setHeight(player.get("height") != null ? (String) player.get("height") : null);
                        if (playerDetailInfo.getWeight()==null) playerDetailInfo.setWeight(player.get("weight") != null ? (String) player.get("weight") : null);
                        if (playerDetailInfo.getPhoto()==null) playerDetailInfo.setPhoto(player.get("photo") != null ? (String) player.get("photo") : null);
                        Map<String, Object> temp = (Map<String, Object>) player.get("birth");
                        if (playerDetailInfo.getBirth()==null) playerDetailInfo.setBirth(temp.get("date") != null ? (String) temp.get("date") : null);
                        if (playerDetailInfo.getCountry()==null) playerDetailInfo.setCountry(temp.get("country") != null ? (String) temp.get("country") : null);


                    }


                    List<Map<String, Object>> statisticsList = (List<Map<String, Object>>) playerData.get("statistics");

                    if (statisticsList != null && !statisticsList.isEmpty()) {
                        Map<String, Object> statistics = statisticsList.get(0);
                        // team信息
                        Map<String, Object> team = (Map<String, Object>) statistics.get("team");
                        String teamName = team.get("name") != null ? (String) team.get("name") : null;
                        String teamLogo = team.get("logo") != null ? (String) team.get("logo") : null;

                        // league
                        Map<String, Object> league = (Map<String, Object>) statistics.get("league");
                        String leagueName = league.get("name") != null ? (String) league.get("name") : null;
                        String leagueLogo = league.get("logo") != null ? (String) league.get("logo") : null;

                        // games
                        Map<String, Object> games = (Map<String, Object>) statistics.get("games");
                        String position = games.get("position") != null ? (String) games.get("position") : null;

                        // goals
                        Map<String, Object> goalsObj = (Map<String, Object>) statistics.get("goals");
                        Integer goals = goalsObj.get("total") != null ? (Integer) goalsObj.get("total") : null;
                        Integer assists = goalsObj.get("assists") != null ? (Integer) goalsObj.get("assists") : null;

                        // passes
                        Map<String, Object> passesObj = (Map<String, Object>) statistics.get("passes");
                        Integer passes = passesObj.get("total") != null ? (Integer) passesObj.get("total") : null;

                        // tackles
                        Map<String, Object> tacklesObj = (Map<String, Object>) statistics.get("tackles");
                        Integer tackles = tacklesObj.get("total") != null ? (Integer) tacklesObj.get("total") : null;

                        // cards
                        Map<String, Object> cards = (Map<String, Object>) statistics.get("cards");
                        Integer yellow = cards.get("yellow") != null ? (Integer) cards.get("yellow") : null;
                        Integer red = cards.get("red") != null ? (Integer) cards.get("red") : null;

                        // set
                        PlayerDetailInfo.SeasonInfo seasonInfo = new PlayerDetailInfo.SeasonInfo();
                        seasonInfo.setSeasonNum(String.valueOf(i));
                        seasonInfo.setTeamName(teamName);
                        seasonInfo.setTeamLogo(teamLogo);
                        seasonInfo.setLeagueName(leagueName);
                        seasonInfo.setLeagueLogo(leagueLogo);
                        seasonInfo.setPosition(position);
                        seasonInfo.setGoals(goals);
                        seasonInfo.setAssists(assists);
                        seasonInfo.setPasses(passes);
                        seasonInfo.setTackles(tackles);
                        seasonInfo.setYellow(yellow);
                        seasonInfo.setRed(red);

                        // 加入列表
                        seasonInfos.add(seasonInfo);
                    }
                }
            }

        }

        playerDetailInfo.setSeasonInfos(seasonInfos);
        return playerDetailInfo;
    }

}
