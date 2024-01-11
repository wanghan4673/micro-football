package com.football.player.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameList {
    Long Count;
    List<GameSimpleInfo> gameSimpleInfos;
}
