package com.lobxy.tictactoe.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lobxy.tictactoe.R;

public class Main extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "main";
    public Button[][] buttons = new Button[3][3];

    private TextView player1;
    private TextView player2;

    public boolean player1Turn = true;

    public int player1Score = 0;
    public int player2Score = 0;
    public int roundCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button reset = findViewById(R.id.resetButton);

        player1 = findViewById(R.id.player1Tv);
        player2 = findViewById(R.id.player2Tv);

        player1.setText("Player 1: " + player1Score);
        player2.setText("Player 2: " + player2Score);

        //initialize buttons.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonId = "button_" + i + j;
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });


    }

    private void reset() {

        //set the value of buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;

    }

    @Override
    public void onClick(View view) {
        Log.i(TAG, "onClick: player turn" + player1Turn);

        //if they button has a value "O/X", return;
        if (!(((Button) view).getText().toString().equals(""))) {
            return;
        } else if (player1Turn) {
            ((Button) view).setText("X");
        } else {
            ((Button) view).setText("O");
        }

        roundCount++;
        Log.i(TAG, "onClick: roundCount " + roundCount);

        if (roundCount == 9) {
            if (hasWon()) {
                if (player1Turn) {
                    player1Won();
                } else {
                    player2Won();
                }
            } else {
                Toast.makeText(this, "Match Draw", Toast.LENGTH_SHORT).show();
                reset();
                return;
            }
        } else if (hasWon()) {
            Log.i(TAG, "onClick: check for won");
            if (player1Turn) {
                player1Won();
            } else {
                player2Won();
            }
        } else {
            player1Turn = !player1Turn;
        }

    }

    public boolean hasWon() {

        boolean returnValue = false;
        String buttonValues[][] = new String[3][3];

        //get the value of buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonValues[i][j] = buttons[i][j].getText().toString();
                //  Log.i(TAG, "hasWon: values " + buttons[i][j].getId() + " - " + buttonValues[i][j]);
            }
        }

        for (int i = 0; i < 3; i++) {
            if (buttonValues[i][0].equals(buttonValues[i][1]) && buttonValues[i][0].equals(buttonValues[i][2]) && !buttonValues[i][0].equals("")) {
                Log.i(TAG, "hasWon: Row condition true");
                returnValue = true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (buttonValues[0][i].equals(buttonValues[1][i]) && buttonValues[0][i].equals(buttonValues[2][i]) && !buttonValues[0][i].equals("")) {
                Log.i(TAG, "hasWon: Coloumn condition true");
                returnValue = true;
            }
        }

        if (buttonValues[0][0].equals(buttonValues[1][1]) && buttonValues[0][0].equals(buttonValues[2][2]) && !buttonValues[0][0].equals("")) {
            Log.i(TAG, "hasWon: Coloumn condition true");
            returnValue = true;
        }
        if (buttonValues[0][2].equals(buttonValues[1][1]) && buttonValues[0][2].equals(buttonValues[2][0]) && !buttonValues[0][2].equals("")) {
            Log.i(TAG, "hasWon: Coloumn condition true");
            returnValue = true;
        }

        return returnValue;
    }

    public void player1Won() {
        //player1 is the winner.
        player1Score++;
        player1.setText("Player 1: " + player1Score);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulations")
                .setMessage("Player 1 won!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reset();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void player2Won() {
        //player2 is the winner.
        player2Score++;
        player2.setText("Player 2: " + player2Score);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulations")
                .setMessage("Player 2 won!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reset();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


}