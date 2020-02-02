package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /*
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                //sdf
            }
        }
     */


    Button[][] grid = new Button[3][3];
    int[][] board = new int[3][3];

    final int BLANK = 0;
    final int XMOVE = 1;
    final int OMOVE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid[0][0] = (Button)this.findViewById(R.id.button1);
        grid[0][1] = (Button)this.findViewById(R.id.button2);
        grid[0][2] = (Button)this.findViewById(R.id.button3);
        grid[1][0] = (Button)this.findViewById(R.id.button4);
        grid[1][1] = (Button)this.findViewById(R.id.button5);
        grid[1][2] = (Button)this.findViewById(R.id.button6);
        grid[2][0] = (Button)this.findViewById(R.id.button7);
        grid[2][1] = (Button)this.findViewById(R.id.button8);
        grid[2][2] = (Button)this.findViewById(R.id.button9);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button b = (Button)v;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (b.equals(grid[x][y])) {
                    if (board[x][y] == BLANK) {
                        b.setText("X");
                        b.setEnabled(false);
                        board[x][y] = XMOVE;
                        compMove();
                    }
                }
            }
        }
    }

    public void compMove() {
        //win
        if (checkSingleBlank(OMOVE)) {
            return;
        }

        //block
        if (checkSingleBlank(XMOVE)){
            return;
        }

        //play randomly
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == BLANK) {
                    list.add(x*10+y);
                }
            }
        }
        int choice = (int)(Math.random() * list.size());
        board[list.get(choice / 10)][list.get(choice % 10)] = OMOVE;
        grid[list.get(choice / 10)][list.get(choice % 10)].setText("O");
        grid[list.get(choice / 10)][list.get(choice % 10)].setEnabled(false);
        return;
    }

    public void resetBoard() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                board[x][y] = BLANK;
                grid[x][y].setEnabled(true);
                grid[x][y].setText("");
            }
        }
    }

    public void checkOver() {
        if (checkWin(OMOVE) == true) {
            Toast.makeText(this, "Player O Won!", Toast.LENGTH_SHORT).show();
            resetBoard();
        } else if (checkWin(XMOVE)) {
            Toast.makeText(this, "You Won!", Toast.LENGTH_SHORT).show();
            resetBoard();
        }
    }

    public boolean checkWin(int player) {
        //rows
        if (board[0][0] == player && board[1][0] == player && board[2][0] == player) {
            return true;
        }
        if (board[0][1] == player && board[1][1] == player && board[2][1] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][2] == player && board[2][2] == player) {
            return true;
        }

        //cols
        if (board[0][0] == player && board[0][1] == player && board[0][2] == player) {
            return true;
        }
        if (board[1][0] == player && board[1][1] == player && board[1][2] == player) {
            return true;
        }
        if (board[2][0] == player && board[2][1] == player && board[2][2] == player) {
            return true;
        }
        //up-left to bottom-right
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }

        //up-right to bottom-left
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }

    public boolean checkSingleBlank(int player) {
        int playerCount = 0;
        int blankCount = 0;
        int blankX = 0;
        int blankY = 0;

        //check columns win
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == BLANK) {
                    blankCount++;
                    blankX = x;
                    blankY = y;
                }
                if (board[x][y] == player) {
                    playerCount++;
                }
            }
            if (playerCount == 2 && blankCount == 1) {
                board[blankX][blankY] = OMOVE;
                grid[blankX][blankY].setEnabled(false);
                grid[blankX][blankY].setText("O");
                return true;
            }
        }

        //check row win
        playerCount = 0;
        blankCount = 0;
        blankX = 0;
        blankY = 0;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (board[x][y] == BLANK) {
                    blankCount++;
                    blankX = x;
                    blankY = y;
                }
                if (board[x][y] == player) {
                    playerCount++;
                }
            }
            if (playerCount == 2 && blankCount == 1) {
                board[blankX][blankY] = OMOVE;
                grid[blankX][blankY].setEnabled(false);
                grid[blankX][blankY].setText("O");
                return true;
            }
        }

        //up-right to bottom-left
        playerCount = 0;
        blankCount = 0;
        blankX = 0;
        blankY = 0;
        if (board[0][0] == BLANK) {
            blankCount++;
            blankX = 0;
            blankY = 0;
        } else if (board[0][0] == player) {
            playerCount++;
        }
        if (board[1][1] == BLANK) {
            blankCount++;
            blankX = 0;
            blankY = 0;
        } else if (board[1][1] == player) {
            playerCount++;
        }
        if (board[2][2] == BLANK) {
            blankCount++;
            blankX = 0;
            blankY = 0;
        } else if (board[2][2] == player) {
            playerCount++;
        }
        if (playerCount == 2 && blankCount == 1) {
            board[blankX][blankY] = OMOVE;
            grid[blankX][blankY].setEnabled(false);
            grid[blankX][blankY].setText("O");
            return true;
        }

        //up-left to bottom-right
        playerCount = 0;
        blankCount = 0;
        blankX = 0;
        blankY = 0;
        if (board[0][2] == BLANK) {
            blankCount++;
            blankX = 0;
            blankY = 0;
        } else if (board[0][2] == player) {
            playerCount++;
        }
        if (board[1][1] == BLANK) {
            blankCount++;
            blankX = 0;
            blankY = 0;
        } else if (board[1][1] == player) {
            playerCount++;
        }
        if (board[2][0] == BLANK) {
            blankCount++;
            blankX = 0;
            blankY = 0;
        } else if (board[2][0] == player) {
            playerCount++;
        }
        if (playerCount == 2 && blankCount == 1) {
            board[blankX][blankY] = OMOVE;
            grid[blankX][blankY].setEnabled(false);
            grid[blankX][blankY].setText("O");
            return true;
        }

        return false;
    }
}
