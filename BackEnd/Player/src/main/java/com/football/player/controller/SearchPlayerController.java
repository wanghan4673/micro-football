package com.football.player.controller;

import com.football.player.model.PlayerDetailInfo;
import com.football.player.model.Result;
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
@RequestMapping("/player")
public class  SearchPlayerController {

    @Autowired
    private PlayerServiceImpl playerServiceImpl;


    @GetMapping("")
    public Result getPlayersByKeywordAndLeague(@RequestParam("searchKey") String searchKey,
                                                  @RequestParam("leagueName") String leagueName){
        List<?> response= playerServiceImpl.getPlayersByKeywordAndLeague(searchKey,leagueName);
        return Result.success(response);

    }

    @GetMapping("/detail")
    public Result getPlayersByKeywordAndLeague(@RequestParam("playerId") int playerId){
        PlayerDetailInfo response= playerServiceImpl.getPlayerDetailById(playerId);
        return Result.success(response);

    }




}
