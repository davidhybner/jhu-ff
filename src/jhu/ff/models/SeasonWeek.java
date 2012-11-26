package jhu.ff.models;

public class SeasonWeek {
    private int year;
    private int week;

    public SeasonWeek(int year, int week) {
        this.year = year;
        this.week = week;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    @Override
    public String toString() {
        return "SeasonWeek{" +
                "year=" + year +
                ", week=" + week +
                '}';
    }
}
