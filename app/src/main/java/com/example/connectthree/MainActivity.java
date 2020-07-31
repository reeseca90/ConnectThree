package com.example.connectthree;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.gridlayout.widget.GridLayout;

public class MainActivity extends AppCompatActivity {

    boolean isRedTurn = true;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; //0 for red, 1 for yellow, 2 for empty

    public void dropIn (View view) {
        ImageView piece = (ImageView) view;
        TextView textViewTurn = (TextView) findViewById(R.id.textViewTurn);

        int pieceTag = Integer.parseInt(piece.getTag().toString()); //used to set gamestate array

        if (piece.getDrawable() == null) { //child drawable is null on launch/new game
            if (isRedTurn) {
                piece.setImageResource(R.drawable.red);
                textViewTurn.setText("It is Yellow's Turn");
                gameState[pieceTag] = 0;
                checkWinnerRed();
                isRedTurn = false;
            } else {
                piece.setImageResource(R.drawable.yellow);
                textViewTurn.setText("It is Red's Turn");
                gameState[pieceTag] = 1;
                checkWinnerYellow();
                isRedTurn = true;
            }

            //drops pieces in from the top
            piece.setTranslationY(-2000);
            piece.animate().translationYBy(2000).setDuration(500);
        }
    }

    //each checkWinner checks gameState[] for the respective color on every click. This was
    //originally one large method, but I wanted to split it out to run less code on each click.
    public void checkWinnerRed() {
        if ((gameState[0] == 0 && gameState[1] == 0 && gameState[2] == 0) ||
                (gameState[3] == 0 && gameState[4] == 0 && gameState[5] == 0) ||
                (gameState[6] == 0 && gameState[7] == 0 && gameState[8] == 0) ||
                (gameState[0] == 0 && gameState[4] == 0 && gameState[8] == 0) ||
                (gameState[2] == 0 && gameState[4] == 0 && gameState[6] == 0)) {
            Toast.makeText(this, "Red wins!", Toast.LENGTH_LONG).show();
            setClickableFalse();
        }
    }

    public void checkWinnerYellow() {
        if ((gameState[0] == 1 && gameState[1] == 1 && gameState[2] == 1) ||
                (gameState[3] == 1 && gameState[4] == 1 && gameState[5] == 1) ||
                (gameState[6] == 1 && gameState[7] == 1 && gameState[8] == 1) ||
                (gameState[0] == 1 && gameState[4] == 1 && gameState[8] == 1) ||
                (gameState[2] == 1 && gameState[4] == 1 && gameState[6] == 1)) {
            Toast.makeText(this, "Yellow wins!", Toast.LENGTH_LONG).show();
            setClickableFalse();
        }
    }

    //setClickableFalse runs when checkWinner sees a winner. setClickableTrue runs when the New Game
    //button is pressed to reset the board. setClickable gets each gameBoard child individually to
    //change it's clickable attribute to false.
    public void setClickableFalse() {
        GridLayout gameBoard = (GridLayout) findViewById(R.id.gameBoard);
        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            gameBoard.getChildAt(i).setClickable(false);
        }
    }

    public void setClickableTrue() {
        GridLayout gameBoard = (GridLayout) findViewById(R.id.gameBoard);
        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            gameBoard.getChildAt(i).setClickable(true);
        }
    }

    public void newGame (View view) {
        GridLayout gameBoard = (GridLayout) findViewById(R.id.gameBoard);
        TextView textViewTurn = (TextView) findViewById(R.id.textViewTurn);
        isRedTurn = true;
        setClickableTrue();

        //clears the drawable from each gameBoard space
        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            ((ImageView)gameBoard.getChildAt(i)).setImageResource(0);
        }
        textViewTurn.setText("It is Red's Turn");
        //resets gameState[] to it's default configuration
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}