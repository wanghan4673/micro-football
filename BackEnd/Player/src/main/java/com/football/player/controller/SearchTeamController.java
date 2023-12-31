package com.football.player.controller;

import com.football.player.model.Result;
import com.football.player.service.impl.PlayerServiceImpl;
import com.football.player.service.impl.TeamServiceImpl;
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
@RequestMapping("/player/team")
public class SearchTeamController {


    @Autowired
    private TeamServiceImpl teamServiceImpl;


    @GetMapping("/getTeamsByKeyword")
    public Result getTeamsByKeyword(@RequestParam("searchKey") String searchKey) throws IOException {
        List<?> response= teamServiceImpl.getTeamsByKeyword(searchKey);
        return Result.success(response);
    }


}
