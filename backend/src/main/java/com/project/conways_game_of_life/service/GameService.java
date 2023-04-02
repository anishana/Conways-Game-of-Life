package com.project.conways_game_of_life.service;

import com.project.conways_game_of_life.model.Cell;
import com.project.conways_game_of_life.model.State;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private int width;
    private int height;
    private Cell[][] board;

    public GameService(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new Cell[height][width];
        initializeBoard();
    }

    public void initializeBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    public void updateBoard() {
        Cell[][] newBoard = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);

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
            }
        }

        board = newBoard;
    }

    public int countLiveNeighbors(int x, int y) {
        int count = 0;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i == x && j == y) {
                    continue;
                }
                if (i >= 0 && i < height && j >= 0 && j < width) {
                    if (board[i][j].getState() == State.ALIVE) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }
}
