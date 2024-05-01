package com.insider.login.calendar.dto;

import java.time.LocalDateTime;

public class CalendarDTO {

    private int calendarNo;
    private String calendarName;
    private LocalDateTime calendarStart;
    private LocalDateTime calendarEnd;
    private String color;
    private int registrantId;

    public CalendarDTO() {
    }

    public CalendarDTO(String calendarName, LocalDateTime calendarStart, LocalDateTime calendarEnd, String color, int registrantId) {
        this.calendarName = calendarName;
        this.calendarStart = calendarStart;
        this.calendarEnd = calendarEnd;
        this.color = color;
        this.registrantId = registrantId;
    }

    public CalendarDTO(int calendarNo, String calendarName, LocalDateTime calendarStart, LocalDateTime calendarEnd, String color, int registrantId) {
        this.calendarNo = calendarNo;
        this.calendarName = calendarName;
        this.calendarStart = calendarStart;
        this.calendarEnd = calendarEnd;
        this.color = color;
        this.registrantId = registrantId;
    }

    public int getCalendarNo() {
        return calendarNo;
    }

    public void setCalendarNo(int calendarNo) {
        this.calendarNo = calendarNo;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public LocalDateTime getCalendarStart() {
        return calendarStart;
    }

    public void setCalendarStart(LocalDateTime calendarStart) {
        this.calendarStart = calendarStart;
    }

    public LocalDateTime getCalendarEnd() {
        return calendarEnd;
    }

    public void setCalendarEnd(LocalDateTime calendarEnd) {
        this.calendarEnd = calendarEnd;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRegistrantId() {
        return registrantId;
    }

    public void setRegistrantId(int registrantId) {
        this.registrantId = registrantId;
    }

    @Override
    public String toString() {
        return "CalendarDTO{" +
                "calendarNo=" + calendarNo +
                ", calendarName='" + calendarName + '\'' +
                ", calendarStart=" + calendarStart +
                ", calendarEnd=" + calendarEnd +
                ", color='" + color + '\'' +
                ", registrantId=" + registrantId +
                '}';
    }
}
