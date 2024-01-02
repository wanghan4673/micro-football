package com.football.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameSubscription {
    Long id;
    Long userId;
    Integer gameId;
    String startTime;
}
