package com.football.player.controller;

import com.football.common.utils.UserContext;
import com.football.mfapi.client.UserClient;
import com.football.player.model.GameDetailInfo;
import com.football.player.model.Result;
import com.football.player.service.impl.GameServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 查询Player相关的外部api
 */
@RestController
@Slf4j
@RequestMapping("/game")
public class SearchGameController {

    @Autowired
    private GameServiceImpl gameServiceImpl;
    @Autowired
    private UserClient userClient;

    @GetMapping()
    public Result getAllPlayers(@RequestParam(defaultValue = "1",required = false) Integer page,
                                @RequestParam(defaultValue = "10",required = false) Integer size,
                                @RequestParam(defaultValue = "全部赛事",required = false) String league
    ) {
        return Result.success(gameServiceImpl.getAllGames(page,size,league));
    }
    @GetMapping("/info")
    public Result getGamesByKeywordAndLeague(@RequestParam("date") String date,
                                               @RequestParam("leagueName") String leagueName){
        List<?> response= gameServiceImpl.getGamesByDateAndLeague(date,leagueName);
        return Result.success(response);
    }

    @GetMapping("/detail")
    public Result getGameDetail(@RequestParam("id") int id){
        GameDetailInfo response= gameServiceImpl.getGameDetailById(id);
        return Result.success(response);
    }

    @PostMapping("/subscribe-game")
    public Result subscribeGame(@RequestParam("gameId") Long gameId,
                                @RequestParam("startTime") String startTime){
        Long userId = UserContext.getUser();
        boolean result = userClient.subscribeGame(userId,gameId,startTime);
        if(result){
            return Result.success();
        } else{
            return Result.error("subscribeError");
        }
    }
}
