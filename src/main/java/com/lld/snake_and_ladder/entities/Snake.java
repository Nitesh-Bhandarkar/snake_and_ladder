package com.lld.snake_and_ladder.entities;

import com.lld.snake_and_ladder.interfaces.PositionShifter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Snake implements PositionShifter {
    private final int head;
    private final int tail;

    @Override
    public int shiftPosition() {
        return tail;
    }
}
