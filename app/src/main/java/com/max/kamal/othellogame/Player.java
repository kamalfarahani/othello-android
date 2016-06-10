package com.max.kamal.othellogame;


/**
 * Created by kmax on 5/30/16.
 */

public class Player {

    private String name;
    private int score;
    Mark mark;

    public Player(String name, Mark mark){
        this.name = name;
        this.score = 0;
        this.mark = mark;
    }

    public void setScore(int score){
        this.score = score;
    }

    public String getName(){
        return name;
    }

    public int getScore() {
        return score;
    }

    public Mark getMark() {
        return mark;
    }
}
