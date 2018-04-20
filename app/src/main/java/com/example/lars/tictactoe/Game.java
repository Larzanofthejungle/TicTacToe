package com.example.lars.tictactoe;

import java.io.Serializable;

import static com.example.lars.tictactoe.Tile.BLANK;
import static com.example.lars.tictactoe.Tile.INVALID;

public class Game implements Serializable {

    final private int BOARD_SIZE = 3;
    private Tile[][] board;
    private Boolean playerOneTurn;  // true if player 1's turn, false if player 2's turn
    private int movesPlayed;
    private Boolean gameOver;

    public Game() {
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        for(int i=0; i<BOARD_SIZE; i++)
            for(int j=0; j<BOARD_SIZE; j++)
                board[i][j] = BLANK;

        playerOneTurn = true;
        gameOver = false;
        movesPlayed = 0;

    }

    public Tile draw(int row, int column) {

        //check if the tile is blank and changes the tile to the new value
        if (board[row][column] == Tile.BLANK){
            movesPlayed = movesPlayed + 1;
            if (playerOneTurn) {
                playerOneTurn = false;
                board[row][column] = Tile.CROSS;
                return Tile.CROSS;
            }
            else{
                playerOneTurn = true;
                board[row][column] = Tile.CIRCLE;
                return Tile.CIRCLE;
            }
        }
        else {
            return Tile.INVALID;
        }
    }

    //checks if there is a row of three crosses or circles
    public boolean checkWon() {
        if (board[0][0] != Tile.BLANK) {
            if (board[0][0] == board[0][1] && board[0][0] == board[0][2]) {
                return true;
            } else if (board[0][0] == board[1][0] && board[0][0] == board[2][0]) {
                return true;
            } else if (board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
                return true;
            }
        }
        if (board[0][1] != Tile.BLANK) {
            if (board[0][1] == board[1][1] && board[0][1] == board[2][1]) {
                return true;
            }
        }
        if (board[0][2] != Tile.BLANK) {
            if (board[0][2] == board[1][2] && board[0][2] == board[2][2]) {
                return true;
            }
            else if (board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
                return true;
            }
        }
        if (board[1][0] != Tile.BLANK) {
            if (board[1][0] == board[1][1] && board[1][0] == board[1][2]) {
                return true;
            }
        }
        if (board[2][0] != Tile.BLANK) {
            if (board[2][0] == board[2][1] && board[2][0] == board[2][2]) {
                return true;
            }
        }
        return false;
    }

    //checks the current game state
    public GameState checkGameState() {
        //only after 4 moves there can be a row of three of the same symbols
        if (movesPlayed > 4) {
            if (checkWon()) {
                //because playerOneTurn is changed before it is checked it is used in reverse here
                if (playerOneTurn) {
                    return GameState.PLAYER_TWO;
                }
                else {
                    return GameState.PLAYER_ONE;
                }
            }
            //if the field is full and there is no row then it is a draw
            else if (movesPlayed == 9) {
                return GameState.DRAW;
            }
        }
        return GameState.IN_PROGRESS;
    }

    //gets the current state of a tile
    public Tile getState(int row, int column) {
        return board[row][column];
    }

    //since the boolean is private this is used to check if the game is over
    public boolean gameOver() {
        if (gameOver) {
            return true;
        }
        else {
            return false;
        }
    }

    //sets all tiles to INVALID
    public void stopGame() {
        gameOver = true;
        for(int i=0; i<BOARD_SIZE; i++)
            for(int j=0; j<BOARD_SIZE; j++)
                board[i][j] = INVALID;
    }
}
