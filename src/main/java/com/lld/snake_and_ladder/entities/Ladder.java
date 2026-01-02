package com.lld.snake_and_ladder.entities;

import com.lld.snake_and_ladder.interfaces.PositionShifter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Ladder implements PositionShifter {
    private final int top;
    private final int bottom;

    @Override
    public int shiftPosition() {
        return top;
    }
}
