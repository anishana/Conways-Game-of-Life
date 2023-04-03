package com.project.conways_game_of_life.model.request;

import lombok.Data;

@Data
public class InitializeBoardRequest {
    int rowSize;
    int columnSize;
    public InitializeBoardRequest(int rowSize, int columnSize){
        this.rowSize = rowSize;
        this.columnSize = columnSize;
    }
}
