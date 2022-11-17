package com.example.fourthlaboratory;

public class Names {

    public long id;
    public String date;
    public int time;
    public int points;
    public int steps;


    public Names(long id, String date, int time, int points, int steps) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.points = points;
        this.steps = steps;
    }

    // getters
    public String getId() {
        return Long.toString(id);
    }
    public String getDate() {
        return date;
    }
    public int getTime() {
        return time;
    }
    public int getPoints() {
        return points;
    }
    public int getSteps() {
        return steps;
    }
}
