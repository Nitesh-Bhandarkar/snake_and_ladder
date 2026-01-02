package com.lld.snake_and_ladder.entities;

import java.util.concurrent.ThreadLocalRandom;

public class DiceRoller {
    private final int numberOfDice;

    public DiceRoller(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }

    public int rollDice() {
        return ThreadLocalRandom.current().nextInt(numberOfDice, 6 * numberOfDice + 1);
    }
}
