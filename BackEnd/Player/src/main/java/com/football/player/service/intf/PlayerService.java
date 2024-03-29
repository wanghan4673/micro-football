package com.football.player.service.intf;

import com.football.player.model.PlayerDetailInfo;
import com.football.player.model.PlayerList;
import com.football.player.model.PlayerSimpleInfo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PlayerService {

    List<PlayerSimpleInfo> getPlayersByKeywordAndLeague(String searchKey, String leagueName);

    PlayerList getAllPlayers(Integer page, Integer size, String league);

    PlayerDetailInfo getPlayerDetailById(Integer playerId);
}
