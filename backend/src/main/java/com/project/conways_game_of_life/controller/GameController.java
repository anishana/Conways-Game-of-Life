package com.project.conways_game_of_life.controller;

import com.project.conways_game_of_life.model.Cell;
import com.project.conways_game_of_life.model.request.CellRequest;
import com.project.conways_game_of_life.model.request.InitializeBoardRequest;
import com.project.conways_game_of_life.service.impl.GameServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameController {

    private final static Logger LOGGER = LoggerFactory.getLogger(GameController.class);
    @Autowired
    private GameServiceImpl gameService;

    @PostMapping("/initializeBoard")
    public ResponseEntity<String> initializeBoard(@RequestBody InitializeBoardRequest initializeBoardRequest){
        try {
            gameService.initializeBoard(initializeBoardRequest);
            return new ResponseEntity<>("Initialisation Completed",HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error("initializeBoard.e: ",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/board")
    public ResponseEntity<Cell[][]> getBoard() {

        try {
            return new ResponseEntity<>(gameService.getBoard(),HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error("board.e: ",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateBoard")
    public ResponseEntity<Cell [][]> updateBoard(@RequestBody List<CellRequest> cellRequest) {
        try {
            Cell [][] board = gameService.updateBoard(cellRequest);
            return new ResponseEntity<>(board,HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error("update.e: ",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/setAlive")
    public ResponseEntity<String> setAlive(@RequestBody List<CellRequest> cellRequest){
        try {
            gameService.setAlive(cellRequest);
            return new ResponseEntity<>("Set Alive Completed",HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error("setAlive.e: ",e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
