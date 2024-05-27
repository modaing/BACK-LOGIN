package com.insider.login.leave.dto;

public class SubmitAndCalendarDTO {
    private int leaveSubNo;
    private int calendarNo;

    public SubmitAndCalendarDTO() {
    }

    public SubmitAndCalendarDTO(int leaveSubNo, int calendarNo) {
        this.leaveSubNo = leaveSubNo;
        this.calendarNo = calendarNo;
    }

    public int getLeaveSubNo() {
        return leaveSubNo;
    }

    public void setLeaveSubNo(int leaveSubNo) {
        this.leaveSubNo = leaveSubNo;
    }

    public int getCalendarNo() {
        return calendarNo;
    }

    public void setCalendarNo(int calendarNo) {
        this.calendarNo = calendarNo;
    }

    @Override
    public String toString() {
        return "SubmitAndCalendarDTO{" +
                "leaveSubNo=" + leaveSubNo +
                ", calendarNo=" + calendarNo +
                '}';
    }
}

