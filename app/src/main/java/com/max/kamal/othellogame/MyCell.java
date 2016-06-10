package com.max.kamal.othellogame;

import android.content.Context;
import android.widget.Button;

/**
 * Created by kmax on 5/30/16.
 */
public class MyCell extends Button {

    private int row;
    private int column;
    private Mark mark;

    public MyCell(int row, int column, Context context) {
        super(context);
        this.column = column;
        this.row = row;

        setMark(Mark.UNMARKED);
    }

    public void setMark(Mark mark) {
        this.mark = mark;
        setCellBackground();
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Mark getMark() {
        return mark;
    }

    public boolean isUnmarked() {
        if (mark == Mark.UNMARKED)
            return true;
        return false;
    }

    private void setCellBackground() {
        if (mark == Mark.UNMARKED) {
            setBackgroundResource(R.drawable.grc);
        } else if (mark == Mark.PLAYER1) {
            setBackgroundResource(R.drawable.wcr);
        }else {
            setBackgroundResource(R.drawable.bcr);
        }
    }
}
