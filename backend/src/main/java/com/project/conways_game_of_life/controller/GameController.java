package com.project.conways_game_of_life.controller;

import com.project.conways_game_of_life.model.Cell;
import com.project.conways_game_of_life.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/board")
    public Cell[][] getBoard() {
        return gameService.getBoard();
    }

    @PostMapping("/update")
    public void updateBoard() {
        gameService.updateBoard();
    }
}
