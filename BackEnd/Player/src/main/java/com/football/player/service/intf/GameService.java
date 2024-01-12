package com.football.player.service.intf;

import com.football.player.model.GameDetailInfo;
import com.football.player.model.GameList;
import com.football.player.model.GameSimpleInfo;
import com.football.player.model.PlayerList;

import java.util.List;

public interface GameService {
    GameList getAllGames(Integer page, Integer size, String league);

    List<GameSimpleInfo> getGamesByDateAndLeague(String date, String leagueName);
    GameDetailInfo getGameDetailById(Integer id);
}
