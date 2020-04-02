package com.example.goschedule20;

public class Week {
    private String day;
    private String  type;
    private String  available;

    public Week() {
    }

    public Week(String day, String type, String available) {
        this.day = day;
        this.type = type;
        this.available = available;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Work{" +
                "Day='" + day + '\'' +
                ", Type='" + type + '\'' +
                ", Availability='" + available + '\'' +
                '}';
    }
}
