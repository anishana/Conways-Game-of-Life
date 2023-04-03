package com.project.conways_game_of_life.model.request;

import lombok.Data;

@Data
public class CellRequest {
    int row;
    int column;

    public CellRequest(int row, int column){
        this.row = row;
        this.column = column;
    }
}
