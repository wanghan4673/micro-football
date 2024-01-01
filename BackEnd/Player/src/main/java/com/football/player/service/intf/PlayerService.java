package com.football.player.service.intf;

import com.football.player.model.PlayerDetailInfo;
import com.football.player.model.PlayerSimpleInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlayerService {

    List<PlayerSimpleInfo> getPlayersByKeywordAndLeague(String searchKey, Integer leagueId);

    PlayerDetailInfo getPlayerDetailById(Integer playerId);
}
