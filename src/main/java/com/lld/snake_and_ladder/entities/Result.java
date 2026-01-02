package com.lld.snake_and_ladder.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Result {
    private int gameId;
    private int playerId;
    private String playerName;
    private int position;
}
