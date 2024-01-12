package com.football.player.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.football.player.Api.SearchGameApi;
import com.football.player.mapper.GameMapper;
import com.football.player.model.GameDetailInfo;
import com.football.player.model.GameList;
import com.football.player.model.GameSimpleInfo;
import com.football.player.model.PlayerList;
import com.football.player.service.intf.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    GameMapper gameMapper;
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

    private final SearchGameApi searchGameApi=new SearchGameApi();

    private String test="{\n" +
            "  \"get\": \"fixtures\",\n" +
            "  \"parameters\": {\n" +
            "    \"live\": \"all\"\n" +
            "  },\n" +
            "  \"errors\": [],\n" +
            "  \"results\": 4,\n" +
            "  \"paging\": {\n" +
            "    \"current\": 1,\n" +
            "    \"total\": 1\n" +
            "  },\n" +
            "  \"response\": [\n" +
            "    {\n" +
            "      \"fixture\": {\n" +
            "        \"id\": 239625,\n" +
            "        \"referee\": null,\n" +
            "        \"timezone\": \"UTC\",\n" +
            "        \"date\": \"2020-02-06T14:00:00+00:00\",\n" +
            "        \"timestamp\": 1580997600,\n" +
            "        \"periods\": {\n" +
            "          \"first\": 1580997600,\n" +
            "          \"second\": null\n" +
            "        },\n" +
            "        \"venue\": {\n" +
            "          \"id\": 1887,\n" +
            "          \"name\": \"Stade Municipal\",\n" +
            "          \"city\": \"Oued Zem\"\n" +
            "        },\n" +
            "        \"status\": {\n" +
            "          \"long\": \"Halftime\",\n" +
            "          \"short\": \"HT\",\n" +
            "          \"elapsed\": 45\n" +
            "        }\n" +
            "      },\n" +
            "      \"league\": {\n" +
            "        \"id\": 200,\n" +
            "        \"name\": \"Botola Pro\",\n" +
            "        \"country\": \"Morocco\",\n" +
            "        \"logo\": \"https://media.api-sports.io/football/leagues/115.png\",\n" +
            "        \"flag\": \"https://media.api-sports.io/flags/ma.svg\",\n" +
            "        \"season\": 2019,\n" +
            "        \"round\": \"Regular Season - 14\"\n" +
            "      },\n" +
            "      \"teams\": {\n" +
            "        \"home\": {\n" +
            "          \"id\": 967,\n" +
            "          \"name\": \"Rapide Oued ZEM\",\n" +
            "          \"logo\": \"https://media.api-sports.io/football/teams/967.png\",\n" +
            "          \"winner\": false\n" +
            "        },\n" +
            "        \"away\": {\n" +
            "          \"id\": 968,\n" +
            "          \"name\": \"Wydad AC\",\n" +
            "          \"logo\": \"https://media.api-sports.io/football/teams/968.png\",\n" +
            "          \"winner\": true\n" +
            "        }\n" +
            "      },\n" +
            "      \"goals\": {\n" +
            "        \"home\": 0,\n" +
            "        \"away\": 1\n" +
            "      },\n" +
            "      \"score\": {\n" +
            "        \"halftime\": {\n" +
            "          \"home\": 0,\n" +
            "          \"away\": 1\n" +
            "        },\n" +
            "        \"fulltime\": {\n" +
            "          \"home\": null,\n" +
            "          \"away\": null\n" +
            "        },\n" +
            "        \"extratime\": {\n" +
            "          \"home\": null,\n" +
            "          \"away\": null\n" +
            "        },\n" +
            "        \"penalty\": {\n" +
            "          \"home\": null,\n" +
            "          \"away\": null\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Override
    public GameList getAllGames(Integer page, Integer size, String league){
        Integer offset = (page - 1) * size;
        GameList gameList = new GameList();
        gameList.setCount(gameMapper.count(league));
        gameList.setGameSimpleInfos(gameMapper.selectGames(offset, size,league));
        return gameList;
    }

    @Override
    public List<GameSimpleInfo> getGamesByDateAndLeague(String date,String leagueName) {

        Integer leagueId=this.gameTypeMap.get(leagueName);
        if(leagueId==null) {
            System.out.println("leagueId==null");
            return null;
        }
        Integer season=getSeasonYear(date);
        // 调用API获取赛事数据
        ResponseEntity<?> responseEntity =searchGameApi.getGamesByDate(date,leagueId,season);

        List<GameSimpleInfo> games = new ArrayList<>();

        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            try {
                System.out.println("=================== 进行赛事检索 ===================");
                Map<String, Object> responseBody = new ObjectMapper().readValue(responseEntity.getBody().toString(), Map.class);
//                Map<String, Object> responseBody = new ObjectMapper().readValue(test, Map.class);
                List<Map<String, Object>> gamesData = (List<Map<String, Object>>) responseBody.get("response");

                System.out.println("=================== responseBody ===================");
                System.out.println(responseBody);
                System.out.println("=================== responseBody ===================");
                System.out.println("=================== gamesData ===================");
                System.out.println(gamesData);
                System.out.println("=================== gamesData ===================");
                for (Map<String, Object> gameData : gamesData) {

                    Map<String, Object> fixture = (Map<String, Object>) gameData.get("fixture");
                    Map<String, Object> teams = (Map<String, Object>) gameData.get("teams");
                    Map<String, Object> goals = (Map<String, Object>) gameData.get("goals");

                    GameSimpleInfo gameInfo = new GameSimpleInfo();

                    gameInfo.setId((Integer) fixture.get("id"));
                    gameInfo.setDate((String) fixture.get("date"));
                    gameInfo.setStatus((String) ((Map<String, Object>) fixture.get("status")).get("long"));

                    Map<String, Object> venue = (Map<String, Object>) fixture.get("venue");
                    if (venue != null) {
                        gameInfo.setVenueName((String) venue.get("name"));
                        gameInfo.setVenueCity((String) venue.get("city"));
                    }

                    Map<String, Object> homeTeam = (Map<String, Object>) teams.get("home");
                    Map<String, Object> awayTeam = (Map<String, Object>) teams.get("away");

                    gameInfo.setHomeTeamId((Integer) homeTeam.get("id"));
                    gameInfo.setHomeTeamName((String) homeTeam.get("name"));
                    gameInfo.setHomeTeamLogo((String) homeTeam.get("logo"));

                    gameInfo.setAwayTeamId((Integer) awayTeam.get("id"));
                    gameInfo.setAwayTeamName((String) awayTeam.get("name"));
                    gameInfo.setAwayTeamLogo((String) awayTeam.get("logo"));

                    gameInfo.setHomeGoal((Integer) goals.get("home"));
                    gameInfo.setAwayGoal((Integer) goals.get("away"));

                    gameMapper.updateGame(gameInfo,leagueName);
                    games.add(gameInfo);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        }

        System.out.println("检索赛事结果数量："+games.size());

        return games;
    }

    @Override
    public GameDetailInfo getGameDetailById(Integer id) {
        System.out.println("=================== 查询赛事详情 ===================");

        // 调用API获取赛事数据，这里应该有一个实际的URL和可能的查询参数
        ResponseEntity<?> responseEntity =searchGameApi.getGameDetailById(id);

        GameDetailInfo gameDetailInfo = new GameDetailInfo();
        System.out.println("=================== 先查询数据库 ===================");
        gameDetailInfo = gameMapper.getgameDetail(id);
        // 假设gameDetailInfo.getDate()返回的字符串是这样的
        if(gameDetailInfo != null ) {
            String dateString = gameDetailInfo.getDate();
            // 解析字符串为ZonedDateTime对象
            ZonedDateTime gameDateTime = ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
            // 获取当前时间（转换为相同的时区）
            ZonedDateTime now = ZonedDateTime.now(gameDateTime.getZone());
            // 比较时间
            // 当前时间已经超过gameDetailInfo.getDate()所表示的时间
            if (!(now.isAfter(gameDateTime) && gameDetailInfo.getStatus().equals("Not Started"))) {
                return gameDetailInfo;
            }
        }
        System.out.println("=================== 查询外部接口 ===================");
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
            try {
                Map<String, Object> responseBody = new ObjectMapper().readValue(responseEntity.getBody().toString(), Map.class);
//                Map<String, Object> responseBody = new ObjectMapper().readValue(test, Map.class);
                List<Map<String, Object>> gamesData = (List<Map<String, Object>>) responseBody.get("response");

                System.out.println(responseBody);

                for (Map<String, Object> gameData : gamesData) {
                    Map<String, Object> fixture = (Map<String, Object>) gameData.get("fixture");
                    Map<String, Object> teams = (Map<String, Object>) gameData.get("teams");
                    Map<String, Object> league = (Map<String, Object>) gameData.get("league");
                    Map<String, Object> goals = (Map<String, Object>) gameData.get("goals");


                    gameDetailInfo.setId((Integer) fixture.get("id"));
                    gameDetailInfo.setDate((String) fixture.get("date"));
                    gameDetailInfo.setStatus((String) ((Map<String, Object>) fixture.get("status")).get("long"));


                    gameDetailInfo.setLeagueName((String) league.get("name"));
                    gameDetailInfo.setLeagueLogo((String) league.get("logo"));
                    gameDetailInfo.setRound((String) league.get("round"));

                    Map<String, Object> venue = (Map<String, Object>) fixture.get("venue");
                    if (venue != null) {
                        gameDetailInfo.setVenueName((String) venue.get("name"));
                        gameDetailInfo.setVenueCity((String) venue.get("city"));
                    }

                    Map<String, Object> homeTeam = (Map<String, Object>) teams.get("home");
                    Map<String, Object> awayTeam = (Map<String, Object>) teams.get("away");

                    gameDetailInfo.setHomeTeamId((Integer) homeTeam.get("id"));
                    gameDetailInfo.setHomeTeamName((String) homeTeam.get("name"));
                    gameDetailInfo.setHomeTeamLogo((String) homeTeam.get("logo"));

                    gameDetailInfo.setAwayTeamId((Integer) awayTeam.get("id"));
                    gameDetailInfo.setAwayTeamName((String) awayTeam.get("name"));
                    gameDetailInfo.setAwayTeamLogo((String) awayTeam.get("logo"));

                    gameDetailInfo.setHomeGoal((Integer) goals.get("home"));
                    gameDetailInfo.setAwayGoal((Integer) goals.get("away"));

                    gameMapper.updateGameDetail(gameDetailInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        gameDetailInfo.show();

        return gameDetailInfo;



    }


    /**
     * 返回赛季编号的函数
     * 7月前为yyyy-1赛季，7月后为yyyy赛季
     * @param dateString date字符串
     * @return season-Integer类型
     */
    public static Integer getSeasonYear(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // 月份从0开始，所以要加1

            // 如果超过7月1日，返回当前年份，否则返回当前年份减1
            if (month >= 7) {
                return year;
            } else {
                return year - 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
