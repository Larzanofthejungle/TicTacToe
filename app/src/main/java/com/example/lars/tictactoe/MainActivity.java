package com.example.lars.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Game game;
    int[] button_ids = {R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
    int row;
    int column;
    View tempview;
    TextView currentPlayer;
    TextView winCount;
    int winCountPlayerOne = 0;
    int winCountPlayerTwo = 0;
    int drawCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game();
        currentPlayer = findViewById(R.id.textCurrentPlayer);
        winCount = findViewById(R.id.textWinCount);
    }

    public void tileClicked(View view) {

        //when a tile is clicked first it gets its position from its id
        int id = view.getId();
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if (button_ids[3 * i + j] == id) {
                    row = i;
                    column = j;
                }

        //then the tile is drawn
        Tile tile = game.draw(row, column);

        //dependent on its case it is changed to a CROSS, CIRCLE or it is INVALID
        switch(tile) {
            case CROSS:
                view.setBackgroundResource(R.drawable.tic_tac_toe_cross);
                currentPlayer.setText("Player Two");
                break;
            case CIRCLE:
                view.setBackgroundResource(R.drawable.tic_tac_toe_circle);
                currentPlayer.setText("Player One");
                break;
            case INVALID:
                // do something different
                break;
        }
        //checks the current game state
        GameState gameState = game.checkGameState();

        //increases the winner or draw counter
        if (gameState != GameState.IN_PROGRESS && !game.gameOver()) {
            switch (gameState) {
                case PLAYER_ONE:
                    winCountPlayerOne = winCountPlayerOne + 1;
                    break;
                case PLAYER_TWO:
                    winCountPlayerTwo = winCountPlayerTwo + 1;
                    break;
                case DRAW:
                    drawCount = drawCount + 1;
                    break;
            }
            game.stopGame();
            //changes the text for all the counters
            winCount.setText("Wins Player One:  " + winCountPlayerOne + "\nDraws:  " + drawCount + "\nWins Player Two:  " + winCountPlayerTwo);

        }
    }

    //if reset is clicked a new game is started and every necessary view is set to their default setting
    public void resetClicked(View view) {
        game = new Game();
        for (int i = 0; i < (button_ids.length); i++) {
            tempview = findViewById(button_ids[i]);
            tempview.setBackgroundResource(android.R.drawable.btn_default);
            currentPlayer.setText("Player One");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("game", game);
        outState.putInt("playerOne", winCountPlayerOne);
        outState.putInt("playerTwo", winCountPlayerTwo);
        outState.putInt("draw", drawCount);
        outState.putCharSequence("currentPlayer", currentPlayer.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        game = (Game) savedInstanceState.getSerializable("game");
        winCountPlayerOne = savedInstanceState.getInt("playerOne");
        winCountPlayerTwo = savedInstanceState.getInt("playerTwo");
        drawCount = savedInstanceState.getInt("draw");
        winCount.setText("Wins Player One:  " + winCountPlayerOne + "\nDraws:  " + drawCount + "\nWins Player Two:  " + winCountPlayerTwo);
        currentPlayer.setText(savedInstanceState.getCharSequence("currentPlayer"));
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if (game.getState(i, j) == Tile.CROSS) {
                    tempview = findViewById(button_ids[3 * i + j]);
                    tempview.setBackgroundResource(R.drawable.tic_tac_toe_cross);
                }
                else if (game.getState(i, j) == Tile.CIRCLE) {
                    tempview = findViewById(button_ids[3 * i + j]);
                    tempview.setBackgroundResource(R.drawable.tic_tac_toe_circle);
                }
    }
}
