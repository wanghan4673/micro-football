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

}
