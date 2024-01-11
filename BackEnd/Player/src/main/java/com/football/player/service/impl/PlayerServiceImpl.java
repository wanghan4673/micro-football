package com.football.player.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.football.player.Api.SearchPlayerApi;
import com.football.player.mapper.PlayerMapper;
import com.football.player.model.PlayerDetailInfo;
import com.football.player.model.PlayerList;
import com.football.player.model.PlayerSimpleInfo;
import com.football.player.service.intf.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerMapper playerMapper;
    private HashMap<String,Integer> gameTypeMap=new HashMap<>(){
        {
            put("英超", 39);
            put("西甲", 107);
            put("意甲",71);
            put("德甲",78);
            put("法甲",61);
            put("中超",169);
        }
    };


    private final SearchPlayerApi searchPlayerApi=new SearchPlayerApi();

    /**
     * 根据关键字和联赛搜素球员
     * @param searchKey 关键字
     * @param leagueName 联赛名称
     * @return 球员列表
     */
    @Override
    public List<PlayerSimpleInfo> getPlayersByKeywordAndLeague(String searchKey, String leagueName) {

        System.out.println("=================== 进行球员检索 ===================");

//        System.out.println("=================== 通过外部接口检索 ===================");
        Integer leagueId=this.gameTypeMap.get(leagueName);
        if(leagueId==null) return null;

        // 调用外部api获取对象
        ResponseEntity<?> responseEntity = searchPlayerApi.getPlayersByKeyAndLeague(searchKey, leagueId);
        System.out.println(responseEntity);


        Map<String, Object> responseBody=new HashMap<>();
        if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            // 通过mapper映射到map
            try {
                responseBody = new ObjectMapper().readValue(responseEntity.getBody().toString(), Map.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            List<Map<String, Object>> playersData = (List<Map<String, Object>>) responseBody.get("response");
//            System.out.println(playersData);
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
                playerDto.setLeague(leagueName);

                Map<String, Object> birthInfo = (Map<String, Object>) player.get("birth");
                if (birthInfo != null) {
                    playerDto.setCountry(birthInfo.get("country") != null ? (String) birthInfo.get("country") : null);
                } else {
                    playerDto.setCountry(null);
                }

                playerDto.setPhoto(player.get("photo") != null ? (String) player.get("photo") : null);


                players.add(playerDto);
                //更新球员库
                playerMapper.updateplayer(playerDto);
            }
            System.out.println("球员检索结果数量："+players.size());

            return players;
        }

        // 否则返回空列表
        return new ArrayList<>();
    }

    /**
     * 分页返回数据库中的球员信息
     * @param page 页数
     * @param size 每页个数
     * @param league 联赛
     */
    @Override
    public PlayerList getAllPlayers(Integer page, Integer size, String league){
        Integer offset = (page - 1) * size;
        PlayerList playerList = new PlayerList();
        playerList.setCount(playerMapper.count(league));
        playerList.setPlayerSimpleInfos(playerMapper.selectPlayers(offset, size,league));
        return playerList;
    }

    /**
     * 根据球员id返回球员详细信息
     * @param playerId 球员id
     * @return 球员详细信息
     */
    @Override
    public PlayerDetailInfo getPlayerDetailById(Integer playerId){

        System.out.println("=================== 查询球员详情 ===================");
        PlayerDetailInfo playerDetailInfo=new PlayerDetailInfo();
        List<PlayerDetailInfo.SeasonInfo> seasonInfos=new ArrayList<>();
        PlayerSimpleInfo playerSimpleInfo = playerMapper.getPlayer(playerId);

        playerDetailInfo.setId(playerSimpleInfo.getId());
        playerDetailInfo.setAge(playerSimpleInfo.getAge());
        playerDetailInfo.setName(playerSimpleInfo.getName());
        playerDetailInfo.setFirstName(playerSimpleInfo.getFirstName());
        playerDetailInfo.setLastName(playerSimpleInfo.getLastName());
        playerDetailInfo.setHeight(playerSimpleInfo.getHeight());
        playerDetailInfo.setWeight(playerSimpleInfo.getWeight());
        playerDetailInfo.setCountry(playerSimpleInfo.getCountry());
        playerDetailInfo.setPhoto(playerSimpleInfo.getPhoto());
        playerDetailInfo.setBirth(playerSimpleInfo.getBirth());

//        playerMapper.updateplayerMore(playerDetailInfo);

        System.out.println("=================== 在数据库中寻找 ===================");
        seasonInfos = playerMapper.getSeasonInfo(playerId);
        if(seasonInfos != null && !seasonInfos.isEmpty()){
            playerDetailInfo.setSeasonInfos(seasonInfos);

            playerDetailInfo.show();
            return playerDetailInfo;
        }
        System.out.println("=================== 使用外部接口查询 ===================");


        // 限定范围为近五年
        for(int i =2019;i<=2023;i++){
            // 调用外部api获取对象
            ResponseEntity<?> responseEntity = searchPlayerApi.getPlayerInfoByIdAndSeason(playerId, i);

            Map<String, Object> responseBody=new HashMap<>();
            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                try {
                    responseBody = new ObjectMapper().readValue(responseEntity.getBody().toString(), Map.class);
                }catch (Exception e){
                    e.printStackTrace();
                }
//                System.out.println(responseBody);
                List<Map<String, Object>> responseList = (List<Map<String, Object>>) responseBody.get("response");

                if (responseList != null && !responseList.isEmpty()) {
                    Map<String, Object> playerData = responseList.get(0);
                    Map<String, Object> player = (Map<String, Object>) playerData.get("player");

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

                        //添加或更新数据库
                        playerMapper.updateplayerDetail(playerId,seasonInfo);
                        // 加入列表
                        seasonInfos.add(seasonInfo);


                    }
                }
            }

        }

        playerDetailInfo.setSeasonInfos(seasonInfos);
        playerDetailInfo.show();
        return playerDetailInfo;
    }

}
