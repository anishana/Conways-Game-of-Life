package com.project.conways_game_of_life;

import com.project.conways_game_of_life.constants.CellConstants;
import com.project.conways_game_of_life.controller.GameController;
import com.project.conways_game_of_life.model.Cell;
import com.project.conways_game_of_life.model.State;
import com.project.conways_game_of_life.model.request.CellRequest;
import com.project.conways_game_of_life.model.request.InitializeBoardRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ConwaysGameOfLifeApplicationTests {
	private final static Logger LOGGER = LoggerFactory.getLogger(ConwaysGameOfLifeApplicationTests.class);
	@Autowired
	private GameController controller;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		InitializeBoardRequest initializeBoardRequest = new InitializeBoardRequest(CellConstants.ROWS, CellConstants.COLUMNS);
		ResponseEntity<String> response = controller.initializeBoard(initializeBoardRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Initialisation Completed", response.getBody());
	}

	@Test
	void testInitializeGrid(){
		InitializeBoardRequest initializeBoardRequest = new InitializeBoardRequest(CellConstants.ROWS, CellConstants.COLUMNS);
		ResponseEntity<String> response = controller.initializeBoard(initializeBoardRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Initialisation Completed", response.getBody());
	}
	@Test
	void testGetInitialGrid() {

		ResponseEntity<Cell[][]> response = controller.getBoard();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(CellConstants.ROWS, response.getBody().length);
		assertEquals(CellConstants.COLUMNS, response.getBody()[0].length);
	}

	@Test
	void testAllAlive() {
		Cell[][] board = new Cell[CellConstants.ROWS][CellConstants.COLUMNS];
		for(int i=0; i<CellConstants.ROWS; i++){
			for(int j=0; j<CellConstants.COLUMNS;j++){
				board[i][j] = new Cell();
			}
		}

		List<CellRequest> cellRequest = new ArrayList<>();
		for(int i=0; i<CellConstants.ROWS; i++){
			for(int j=0; j<CellConstants.COLUMNS;j++){
				cellRequest.add(new CellRequest(i,j));
			}
		}

		ResponseEntity<Cell[][]> response = controller.updateBoard(cellRequest);
		assertThat(response.getBody()).isEqualTo(board);
	}


	@Test
	void testAllDead() {
		Cell[][] board = new Cell[CellConstants.ROWS][CellConstants.COLUMNS];
		for(int i=0; i<CellConstants.ROWS; i++){
			for(int j=0; j<CellConstants.COLUMNS;j++){
				board[i][j] = new Cell();
			}
		}

		ResponseEntity<Cell[][]> response = controller.updateBoard(new ArrayList<>());
		assertThat(response.getBody()).isEqualTo(board);
	}


	@Test
	public void testBlinker() {
		Cell[][] board = new Cell[CellConstants.ROWS][CellConstants.COLUMNS];
		for(int i=0; i<CellConstants.ROWS; i++){
			for(int j=0; j<CellConstants.COLUMNS;j++){
				board[i][j] = new Cell();
			}
		}


		board[2][1].setState(State.ALIVE);
		board[2][2].setState(State.ALIVE);
		board[2][3].setState(State.ALIVE);

		Cell[][] expectedBoard = new Cell[CellConstants.ROWS][CellConstants.COLUMNS];
		for(int i=0; i<CellConstants.ROWS; i++){
			for(int j=0; j<CellConstants.COLUMNS;j++){
				expectedBoard[i][j] = new Cell();
			}
		}

		expectedBoard[1][2].setState(State.ALIVE);
		expectedBoard[2][2].setState(State.ALIVE);
		expectedBoard[3][2].setState(State.ALIVE);

		List<CellRequest> cellRequestList = new ArrayList<>();
		cellRequestList.add(new CellRequest(2,1));
		cellRequestList.add(new CellRequest(2,2));
		cellRequestList.add(new CellRequest(2,3));

		ResponseEntity<Cell[][]> response = controller.updateBoard(cellRequestList);
		assertThat(expectedBoard).isEqualTo(response.getBody());
	}
}
