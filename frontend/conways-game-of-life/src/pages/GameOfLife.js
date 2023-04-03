import React, { useEffect, useState, useRef } from 'react';
import produce from 'immer';
import { initializeBoard, updateBoard } from '../api/gameApi';
import Button from '@mui/material/Button';

const rowSize = 20;
const columnSize = 20;

// create a function to initialize the grid with random values
const createEmptyGrid = () => {
    const rows = [];
    for (let i = 0; i < rowSize; i++) {
        rows.push(Array.from(Array(columnSize), () => 0));
    }
    return rows;
}


const GameOfLife = () => {
    const [grid, setGrid] = useState(() => {
        return createEmptyGrid();
    });

    const [running, setRunning] = useState(false);
    const runningRef = useRef(running);
    runningRef.current = running;

    useEffect(() => {
        let req = {
            rowSize: rowSize,
            columnSize: columnSize
        }
        initializeBoard(req);
    }, [])

    const runSimulation = () => {
        if (!runningRef.current) {
            return;
        }
        let req = [];
        console.log(grid);
        for (var i = 0; i < rowSize; i++) {
            for (var j = 0; j < columnSize; j++) {
                if (grid[i][j] === 1) {
                    req.push({ "row": i, "column": j });
                }
            }
        }
        updateBoard(req).then((res) => {
            console.log(res);
            if (res.data) {
                let data = res.data;
                let newGrid = [];
                for (var i = 0; i < data.length; i++) {
                    let rows = [];
                    for (var j = 0; j < data[0].length; j++) {
                        rows.push(data[i][j].state === 'ALIVE' ? 1 : 0);
                    }
                    newGrid.push(rows);
                }
                setGrid(newGrid);
            }
        }).catch((error) => {
            console.log(error);
        })
    }

    const setDead = (i, j) => {

        console.log("i: " + i + ", j: " + j);
        const newGrid = produce(grid, gridCopy => {
            gridCopy[i][j] = grid[i][j] ? 0 : 1;
        });
        setGrid(newGrid);
    }
    function sleep(ms) {
        return new Promise(resolve => { setTimeout(resolve, ms); });
    }
    useEffect(() => {
        if (running) {
            sleep(200).then(() => { runSimulation() });
        }
    }, [grid])

    const clear = () => {
        let req = {
            rowSize: rowSize,
            columnSize: columnSize
        }
        initializeBoard(req).then((res) => {
            if (res.data) {
                setRunning(false);
                setGrid(createEmptyGrid());
            }
        }).catch((error) => {
            console.log(error);
        })
    }



    const stepUpdate = () => {
        let req = [];
        for (var i = 0; i < rowSize; i++) {
            for (var j = 0; j < columnSize; j++) {
                if (grid[i][j] === 1) {
                    req.push({ "row": i, "column": j });
                }
            }
        }
        updateBoard(req).then((res) => {
            if (res.data) {
                let data = res.data;
                let newGrid = [];
                for (var i = 0; i < data.length; i++) {
                    let rows = [];
                    for (var j = 0; j < data[0].length; j++) {
                        rows.push(data[i][j].state === 'ALIVE' ? 1 : 0);
                    }
                    newGrid.push(rows);
                }
                setGrid(newGrid);
            }
        }).catch((error) => {
            console.log(error);
        })
    }

    return (
        <>

            <Button variant="contained" sx={{ "m": 2 }} onClick={() => {

                setRunning(!running);
                if (!running) {
                    runningRef.current = true;
                    runSimulation();
                }
            }}>
                {running ? 'Stop' : 'Start'}
            </Button>
            <Button
                variant="contained" sx={{ "m": 2 }} onClick={() => {
                    stepUpdate();
                }}>
                One step Advance
            </Button>


            <Button variant="contained" sx={{ "m": 2 }} onClick={() => { clear(); }}>
                Clear
            </Button>
            <div
                style={{
                    display: 'grid',
                    gridTemplateColumns: `repeat(${columnSize}, 20px)`,
                    margin: '1%'
                }}
            >
                {
                    grid.map((rows, i) =>
                        rows.map((col, j) => (
                            <div
                                key={`${i}-${j}`}
                                onClick={() => {
                                    setDead(i, j);
                                }}
                                style={{
                                    width: 20,
                                    height: 20,
                                    backgroundColor: grid[i][j] ? 'black' : undefined,
                                    border: 'solid 1px black'
                                }}
                            />
                        ))
                    )
                }
            </div>
        </>
    );
}

export default GameOfLife;