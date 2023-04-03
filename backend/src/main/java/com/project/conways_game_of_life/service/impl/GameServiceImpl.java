package com.project.conways_game_of_life.service.impl;

import com.project.conways_game_of_life.controller.GameController;
import com.project.conways_game_of_life.model.Cell;
import com.project.conways_game_of_life.model.State;
import com.project.conways_game_of_life.model.request.CellRequest;
import com.project.conways_game_of_life.model.request.InitializeBoardRequest;
import com.project.conways_game_of_life.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GameServiceImpl.class);
    private Cell[][] board;

    @Override
    public void initializeBoard(InitializeBoardRequest initializeBoardRequest) {
        this.board = new Cell[initializeBoardRequest.getRowSize()][initializeBoardRequest.getColumnSize()];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    @Override
    public Cell[][] updateBoard(List<CellRequest> cellRequestList) {
        Cell[][] newBoard = new Cell[board.length][board[0].length];
        setAlive(cellRequestList);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);
                LOGGER.info("State: "+board[i][j].getState()+", i: "+i+", j: "+j+", liveNeighbors: "+liveNeighbors);
                if (board[i][j].getState() == State.ALIVE) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        newBoard[i][j] = new Cell();
                    } else {
                        newBoard[i][j] = board[i][j];
                    }
                } else {
                    if (liveNeighbors == 3) {
                        newBoard[i][j] = new Cell();
                        newBoard[i][j].setState(State.ALIVE);
                    } else {
                        newBoard[i][j] = board[i][j];
                    }
                }
                LOGGER.info("newBoard[i][j]: "+newBoard[i][j]);
            }
        }

        board = newBoard;
        return board;
    }

    @Override
    public int countLiveNeighbors(int x, int y) {
        int count = 0;
        int height = board.length;
        int width = board[0].length;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (!(i == x && j == y) && board[(i + height) % height][(j + width) % width].getState() == State.ALIVE) {
                    count++;
                }
            }
        }

        return count;
    }

    @Override
    public Cell[][] getBoard() {
        return board;
    }

    @Override
    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    @Override
    public void setAlive(List<CellRequest> cellRequest) {
        for(CellRequest cell : cellRequest){
            this.board[cell.getRow()][cell.getColumn()].setState(State.ALIVE);
        }
    }


}
