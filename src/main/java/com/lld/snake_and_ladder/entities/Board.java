package com.lld.snake_and_ladder.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    private int totalSquare;
    private int gameId;
    private Map<Integer, Snake> snakeMap;
    private Map<Integer, Ladder> ladderMap;
    private Map<Integer, Integer> playerPositionMap;

    public Board(int totalSquare, int gameId, int numberOfSnakes, int numberOfLadders) {
        this.totalSquare = totalSquare;
        this.gameId = gameId;
        loadSnakesAndLadders(numberOfSnakes, numberOfLadders);
        playerPositionMap = new HashMap<>();
    }

    private void loadSnakesAndLadders(int numberOfSnakes, int numberOfLadders) {
        Set<Integer> consideredSquares = new HashSet<>();
        snakeMap = new HashMap<>();
        ladderMap = new HashMap<>();
        for (int i = 0; i < numberOfSnakes; i++) {
            int tail = ThreadLocalRandom.current().nextInt(2, totalSquare - 3);
            while (consideredSquares.contains(tail)) {
                tail = ThreadLocalRandom.current().nextInt(2, totalSquare - 3);
            }
            consideredSquares.add(tail);

            int head = ThreadLocalRandom.current().nextInt(tail + 1, totalSquare - 3);
            while (consideredSquares.contains(head)) {
                head = ThreadLocalRandom.current().nextInt(2, totalSquare - 3);
            }
            consideredSquares.add(head);
            snakeMap.put(head, new Snake(head, tail));
        }


        for (int i = 0; i < numberOfLadders; i++) {
            int bottom = ThreadLocalRandom.current().nextInt(2, totalSquare - 3);
            while (consideredSquares.contains(bottom)) {
                bottom = ThreadLocalRandom.current().nextInt(2, totalSquare - 2);
            }
            consideredSquares.add(bottom);

            int top = ThreadLocalRandom.current().nextInt(bottom + 1, totalSquare - 3);
            while (consideredSquares.contains(top)) {
                top = ThreadLocalRandom.current().nextInt(2, totalSquare - 2);
            }
            consideredSquares.add(top);
            ladderMap.put(bottom, new Ladder(top, bottom));
        }
    }

    /***
     * 0 - No win / loss
     * 1 - Retains the turn
     * -1 - Loses turn
     * 2 - Won the game
     * -2 - Lost
     */


    public int updatePlayerPositionAndCheckIfWon(int playerId, int diceValue, int numberOfDice) {
        int currentPosition = playerPositionMap.getOrDefault(playerId, 0);
        int updatedPosition = currentPosition + diceValue;
        int result = 0;

        if (updatedPosition == totalSquare) {
            result = 2;
        } else if (totalSquare - updatedPosition < numberOfDice) {
            result = -2;
        }

        if (snakeMap.containsKey(updatedPosition)) {
            updatedPosition = snakeMap.get(updatedPosition).shiftPosition();
            System.out.print("\n Sorry.. Bit by a snake");
            result = -1;
        }

        if (ladderMap.containsKey(updatedPosition)) {
            updatedPosition = ladderMap.get(updatedPosition).shiftPosition();
            System.out.print("\n Congrats got a ladder");
            result = 1;
        }

        if (updatedPosition > totalSquare) {
            updatedPosition = currentPosition;
            result = 0;
        }

        System.out.printf("\nMoved from %s to %s%n", currentPosition, updatedPosition);

        playerPositionMap.put(playerId, updatedPosition);

        return result;
    }

}
