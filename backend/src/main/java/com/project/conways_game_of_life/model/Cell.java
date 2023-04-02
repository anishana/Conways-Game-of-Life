package com.project.conways_game_of_life.model;

import lombok.Data;

@Data
public class Cell {
    private State state;

    public Cell() {
        this.state = State.DEAD;
    }
}
