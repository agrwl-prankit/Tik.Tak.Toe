package com.prankit.tiktaktoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0; // 0 = yellow, 1 = red
    boolean gameIsActive = true;
    int[] gameState = {2,2,2,2,2,2,2,2,2}; // 2 means nothing in slot or unplayed
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void dropIn(View view){

        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString()); // to get the id of tap image

        if (gameState[tappedCounter] == 2 && gameIsActive) { // to check if the slot is empty or already taken
            gameState[tappedCounter] = activePlayer; // because active player tell us who is currently playing
            counter.setTranslationY(-1000f); // when user touch the blank image it will move 1000 pixels up off the screen
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellowcircle); // it will set yellow image to image view
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.redcircle); // it will set red image to image view
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300); // it will animate it back down

            for (int i = 0; i < winningPositions.length; i++) {
                if (gameState[winningPositions[i][0]] == gameState[winningPositions[i][1]]
                        && gameState[winningPositions[i][1]] == gameState[winningPositions[i][2]]
                        && gameState[winningPositions[i][0]] != 2) {
                    // Someone has won
                    gameIsActive = false;
                    String winner = "Player 2";
                    if (gameState[winningPositions[i][0]] == 0) {
                        winner = "Player 1";
                    }
                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won!");
                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }
                else {
                    boolean gameIsOver = true;
                    for (int counterState = 0; counterState < gameState.length; counterState++) {
                        if (gameState[counterState] == 2) {
                            gameIsOver = false;
                        }
                    }
                    if (gameIsOver) {
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a draw!");
                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        gameIsActive = true;
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        // Set all the images back to 0
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < grid.getChildCount(); i++) {
            ((ImageView) grid.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}