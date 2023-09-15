package com.example.gridlayout;

import java.util.Random;

public class Game {

    public static final int ROW_COUNT = 12;
    public static final int COLUMN_COUNT = 10;
    public static final int MINE_COUNT = 4;

    private boolean[][] cellRevealed;
    private int[][] gameBoard;
    private boolean[][] mineLocations;

    private boolean gameWon;
    private boolean gameLost;


    public Game() {

        createGameboard();
        placeMines();
    }
    private void createGameboard() {
        //Created gameboard to match row and column count
        gameBoard = new int[ROW_COUNT][COLUMN_COUNT];
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                gameBoard[i][j] = 0;
            }
        }
    }
    private void placeMines(){
        //Random placed mines on the game board
        mineLocations = new boolean[ROW_COUNT][COLUMN_COUNT];
        Random random = new Random();
        int minesPlaced = 0;


        while (minesPlaced < MINE_COUNT){
            int row = random.nextInt(ROW_COUNT);
            int column = random.nextInt(COLUMN_COUNT);

            if(!mineLocations[row][column]){
                mineLocations[row][column] = true;
                minesPlaced++;
            }
        }
    }
    private void cellRevealed() {
        cellRevealed = new boolean[ROW_COUNT][COLUMN_COUNT];
    }

}
