package com.example.goschedule20;

import java.util.Date;

public class Shift {

    private String Day;
    private String Name;
    private String StartTime;
    private String EndTime;
    private String Position;

    public Shift(String name, String pst, String begin, String end){
        this.Name = name;
        this.Position = pst;
        this.StartTime = begin;
        this.EndTime = end;

    }
    public String getDay(){
        return Day;
    }
    public void setDay(String day){
        Day = day;
    }
    public String getName(){
        return Name;
    }
    public void setName(String name){
        Name = name;
    }
    public String getStartTime(){
        return StartTime;
    }
    public void setStartTime(String startTime){
        StartTime = startTime;
    }
    public String getEndTime(){
        return EndTime;
    }
    public void setEndTime(String endTime){
        EndTime = endTime;
    }
    public String getPosition(){
        return Position;
    }
    public void setPosition(String position){
        Position = position;
    }
}
