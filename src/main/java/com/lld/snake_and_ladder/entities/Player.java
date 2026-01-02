package com.lld.snake_and_ladder.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;

@AllArgsConstructor
@Getter
public class Player {
    @Generated("")
    private int id;
    private String name;

    @Override
    public int hashCode() {
        return String.format("%s_%s",id, name).hashCode();
    }
}
