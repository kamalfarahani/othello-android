package com.max.kamal.othellogame.activities;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.max.kamal.othellogame.Mark;
import com.max.kamal.othellogame.MyCell;
import com.max.kamal.othellogame.Player;
import com.max.kamal.othellogame.R;

public class GameBoardActivity extends AppCompatActivity {

    private MyCell[][] boardCircles = new MyCell[8][8];
    private Player[] players = new Player[2];
    private GridLayout gridLayout;
    private int turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPlayers();
        setTurn();
        setupLayout();
    }

    private void initPlayers() {
        players[0] = new Player(
                getIntent().getStringExtra("pl1"), Mark.PLAYER1);
        players[1] = new Player(
                getIntent().getStringExtra("pl2"), Mark.PLAYER2);
    }

    private void setupLayout() {
        setContentView(R.layout.activity_game_board);
        initGridLayout();
        addCells();
        updateGameTextView();
    }

    private void updateGameTextView() {
        TextView gameTextView = (TextView) findViewById(R.id.players_score);

        String text = "Turn : " + players[turn - 1].getName()
                + '\n' + players[0].getName() + " : " + players[0].getScore()
                + '\n' + players[1].getName() + " : " + players[1].getScore();

        gameTextView.setText(text);
    }

    private void initGridLayout() {
        gridLayout = (GridLayout)findViewById(R.id.game_grid_layout);
        gridLayout.setBackgroundColor(Color.GRAY);
    }

    private void addCells() {
        int cellSize = getCellWidthAndHeight();

        for (int i = 0; i < boardCircles.length; i++){
            for (int j = 0; j < boardCircles.length; j++){
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.height = cellSize;
                layoutParams.width = cellSize;

                MyCell cell = new MyCell(i, j, this);
                cell.setLayoutParams(layoutParams);
                setCellListener(cell);

                boardCircles[i][j] = cell;
                gridLayout.addView(cell);
            }
        }

        boardCircles[3][3].setMark(players[0].getMark());
        boardCircles[4][4].setMark(players[0].getMark());
        boardCircles[4][3].setMark(players[1].getMark());
        boardCircles[3][4].setMark(players[1].getMark());
    }

    private void setCellListener(MyCell cell){
        View.OnClickListener cellClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCell tmpCell1 = (MyCell) v;

                if (tmpCell1.isUnmarked())
                    lawyer(tmpCell1.getRow(), tmpCell1.getColumn(), true);
            }
        };

        cell.setOnClickListener(cellClickListener);
    }

    private boolean lawyer(int row , int column , boolean isMove){
        Mark changeMark = players[turn-1].getMark();
        boolean allowedMove = false;

        for(int i = -1 ; i <= 1 ; i++) {
            for(int j = -1 ; j <= 1 ; j++){

                if(i == 0  && j == 0)
                    continue;

                for(int k = 1 ; isInBoard(row, column, i, j, k) ; k++) {

                    if(boardCircles[row + k*i][column + k*j].isUnmarked()) {
                        break;
                    }

                    if(boardCircles[row + k*i][column + k*j].getMark() == changeMark && k == 1) {
                        break;
                    }

                    if( boardCircles[row + k*i][column + k*j].getMark() == changeMark ) {
                        if(isMove) {
                            for(int m = 1 ; m <= k ; m++) {
                                boardCircles[row + m*i][column + m*j].setMark(changeMark);
                            }
                        }
                        allowedMove = true;
                        break;
                    }
                }
            }
        }

        if (allowedMove && isMove) {
            boardCircles[row][column].setMark(changeMark);
            setTurn();
            setScore();
            updateGameTextView();
            checkEnd();
        }

        return allowedMove;
    }

    private int getCellWidthAndHeight() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        return (width / 9);
    }

    private void setTurn(){
        turn++;

        if(turn > players.length)
            turn = 1;
    }

    private void setScore(){
        int score1Tmp = 0;
        int score2Tmp = 0;

        for(int i = 0 ; i < boardCircles.length ; i++){
            for(int j = 0 ; j < boardCircles.length ; j++){
                if(!boardCircles[i][j].isUnmarked()){
                    if( boardCircles[i][j].getMark() == players[0].getMark() ){
                        score1Tmp++;
                    }else{
                        score2Tmp++;
                    }
                }
            }
        }

        players[0].setScore(score1Tmp);
        players[1].setScore(score2Tmp);
    }

    private void checkEnd() {
        if (isEnd()) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setTitle("Game ended");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);

            if(players[0].getScore() < players[1].getScore()){
                dlgAlert.setMessage(players[1].getName() + " won!");
                dlgAlert.create().show();
            }else{
                dlgAlert.setMessage(players[0].getName() + " won!");
                dlgAlert.create().show();
            }
        }
    }

    private boolean isEnd() {
        boolean ended = true;

        for(int i = 0 ; i < boardCircles.length ; i++){
            for(int j = 0 ; j < boardCircles.length ; j++){
                if(boardCircles[i][j].isUnmarked()){
                    if(lawyer(i, j, false)){
                        ended = false;
                    }
                }
            }
        }

        return ended;
    }

    private boolean isInBoard(int row, int column, int i, int j, int k) {
        boolean condition1 = ((row + k*i) < boardCircles.length) && ((column + k*j) < boardCircles.length);
        boolean condition2 = ((row + k*i) >= 0) && ((column + k*j) >= 0);

        return (condition1 && condition2);
    }

}
