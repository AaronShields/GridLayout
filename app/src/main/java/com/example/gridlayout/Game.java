package com.example.gridlayout;


import java.util.ArrayList;
import java.util.Random;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import java.util.LinkedList;
import java.util.Queue;
import android.widget.TextView;

public class Game {

    public static final int ROW_COUNT = 12;
    public static final int COLUMN_COUNT = 10;
    public static final int MINE_COUNT = 4;

    private boolean[][] cellRevealed;
    private int[][] gameBoard;
    private boolean[][] mineLocations;

    private int COLOR_VISIBLE;
    private int revealedTiles;
    private ArrayList<TextView> cell_tvs;
    private int revealedTilesWithColor;


    public Game(ArrayList<TextView> cell_tvs, int COLOR_VISIBLE) {
        this.cell_tvs = cell_tvs;
        this.COLOR_VISIBLE = COLOR_VISIBLE;
        createGameboard();
        placeMines();
        revealedTiles = 0;
        revealedTilesWithColor = 0;
        cellRevealed = new boolean[ROW_COUNT][COLUMN_COUNT];
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

            System.out.println(row + "," + column);
            if(!mineLocations[row][column]){
                mineLocations[row][column] = true;
                minesPlaced++;
            }
        }
    }

    public boolean isValid(int row, int col) {
        return row >= 0 && row < ROW_COUNT && col >= 0 && col < COLUMN_COUNT;
    }

    public int countAdjacentMines(int row, int col) {
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        int count = 0;

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            // Check if new coordinates are within bounds
            if (isValid(newRow, newCol) && isMineAt(newRow, newCol)) {
                count++;
            }
        }

        return count;
    }


    // BFS Function
    public void revealCellBFS(int row, int col) {
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        boolean[][] visited = new boolean[ROW_COUNT][COLUMN_COUNT];

        queue.add(new Pair<>(row, col));
        visited[row][col] = true;

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> cell = queue.poll();
            int currentRow = cell.first;
            int currentCol = cell.second;

            // Check if the cell is next to a mine
            int adjacentMines = countAdjacentMines(currentRow, currentCol);

            if (adjacentMines > 0) {
                // Cell has adjacent mines, set text and background color
                TextView currentTextView = cell_tvs.get(currentRow * COLUMN_COUNT + currentCol);
                currentTextView.setText(String.valueOf(adjacentMines));
                currentTextView.setBackgroundColor(COLOR_VISIBLE);
            } else {
                // Cell is empty, reveal it, queue other cells
                TextView currentTextView = cell_tvs.get(currentRow * COLUMN_COUNT + currentCol);
                currentTextView.setBackgroundColor(COLOR_VISIBLE);
                currentTextView.setText("");

                int[][] directions = {
                        {-1, 0}, {1, 0}, {0, -1}, {0, 1}
                };

                for (int[] dir : directions) {
                    int newRow = currentRow + dir[0];
                    int newCol = currentCol + dir[1];

                    if (isValid(newRow, newCol) && !visited[newRow][newCol]) {
                        visited[newRow][newCol] = true;

                        if (!isMineAt(newRow, newCol)) {
                            // If not a mine, put in queue
                            queue.add(new Pair<>(newRow, newCol));
                        }
                    }
                }
            }
        }
    }
    public boolean cellRevealed(int row, int col) {
        return cellRevealed[row][col];
    }

    public void revealCell(int row, int col) {
        if(!cellRevealed[row][col]){
            cellRevealed[row][col] = true;
        }
        revealedTiles++;
    }


    public boolean isMineAt(int row, int col) {
        // Check if there is a mine at the specified row and column
        return mineLocations[row][col];
    }

}
