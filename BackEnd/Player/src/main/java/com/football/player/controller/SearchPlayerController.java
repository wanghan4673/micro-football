package com.football.player.controller;

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


@RestController
@Slf4j
@RequestMapping("/player/player")
public class SearchPlayerController {

    @Autowired
    private PlayerServiceImpl playerServiceImpl;


    @GetMapping("/getPlayersByKeywordAndLeague")
    public Result getPlayersByKeywordAndLeague(@RequestParam("searchKey") String searchKey,
                                                  @RequestParam("leagueId") int leagueId) throws IOException {
        List<?> response= playerServiceImpl.getPlayersByKeywordAndLeague(searchKey,leagueId);
        return Result.success(response);

    }




}
