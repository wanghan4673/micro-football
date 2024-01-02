package com.football.player.service.intf;

import com.football.player.model.GameDetailInfo;
import com.football.player.model.GameSimpleInfo;

import java.util.List;

public interface GameService {
    List<GameSimpleInfo> getGamesByDateAndLeague(String date,String leagueName);
    GameDetailInfo getGameDetailById(Integer id);
}
