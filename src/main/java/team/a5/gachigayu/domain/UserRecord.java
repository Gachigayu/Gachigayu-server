package team.a5.gachigayu.domain;

import lombok.Builder;

public class UserRecord {

    private static final int KILOMETER_TO_METER = 1_000;
    private static final String HOURS_UNIT = "시간 ";
    private static final String MINUTES_UNIT = "분";

    private double weekLength;

    private int weekTime;

    private int top;

    @Builder
    public UserRecord(double weekLength, int weekTime, int top) {
        this.weekLength = weekLength;
        this.weekTime = weekTime;
        this.top = top;
    }

    public String getWeekLength() {
        return (weekLength / KILOMETER_TO_METER) + "km";
    }

    public String getWeekTime() {
        int hours = weekTime / 60;
        int minutes = weekTime % 60;

        if (hours != 0) {
            return hours + HOURS_UNIT + minutes + MINUTES_UNIT;
        }
        return minutes + MINUTES_UNIT;
    }

    public String getTop() {
        return top + "%";
    }
}
