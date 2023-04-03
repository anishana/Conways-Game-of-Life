package com.project.conways_game_of_life.service;

import com.project.conways_game_of_life.model.Cell;
import com.project.conways_game_of_life.model.request.CellRequest;
import com.project.conways_game_of_life.model.request.InitializeBoardRequest;

import java.util.List;

public interface GameService {
    public void initializeBoard(InitializeBoardRequest initializeBoardRequest);

    public Cell[][] updateBoard(List<CellRequest> cellRequest);

    public int countLiveNeighbors(int x, int y) ;

    public Cell[][] getBoard();

    public void setBoard(Cell[][] board);

    public void setAlive(List<CellRequest> cellRequest) ;
}
