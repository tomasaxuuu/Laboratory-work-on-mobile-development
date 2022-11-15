package com.example.fourthlaboratory;

public class Names {

    public long id;
    public String date;
    public String time;
    public String points;
    public String steps;


    public Names(long id, String date, String time, String points, String steps) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.points = points;
        this.steps = steps;
    }

    public String getId() {
        return Long.toString(id);
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public String getPoints() {
        return points;
    }
    public String getSteps() {
        return steps;
    }
}
