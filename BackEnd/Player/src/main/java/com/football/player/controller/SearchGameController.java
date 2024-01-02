package com.football.player.controller;

import com.football.player.model.GameDetailInfo;
import com.football.player.model.PlayerDetailInfo;
import com.football.player.model.Result;
import com.football.player.service.impl.GameServiceImpl;
import com.football.player.service.impl.PlayerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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


    @GetMapping("")
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



}
