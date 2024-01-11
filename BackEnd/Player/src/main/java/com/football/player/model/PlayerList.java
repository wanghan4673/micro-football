package com.football.player.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerList {
    Long Count;
    List<PlayerSimpleInfo> playerSimpleInfos;
}
