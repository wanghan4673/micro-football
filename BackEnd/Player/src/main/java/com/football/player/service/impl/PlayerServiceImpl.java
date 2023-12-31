package com.football.player.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.football.player.Api.SearchPlayerApi;
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


    @Override
    public List<PlayerSimpleInfo> getPlayersByKeywordAndLeague(String searchKey, Integer leagueId) {
        // 调用外部api获取对象
        ResponseEntity<?> responseEntity = searchPlayerApi.getResponseEntityByKey(searchKey, leagueId);


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
                playerDto.setId((Integer) player.get("id"));
                playerDto.setName((String) player.get("name"));
                playerDto.setFirstName((String) player.get("firstname"));
                playerDto.setLastName((String) player.get("lastname"));
                playerDto.setAge((Integer) player.get("age"));
                playerDto.setHeight((String) player.get("height"));
                playerDto.setWeight((String) player.get("weight"));
                playerDto.setCountry((String) ((Map<String, Object>)player.get("birth")).get("country"));
                playerDto.setPhoto((String) player.get("photo"));

                players.add(playerDto);
            }

            return players;
        }

        // 否则返回空列表
        return new ArrayList<>();
    }

}
