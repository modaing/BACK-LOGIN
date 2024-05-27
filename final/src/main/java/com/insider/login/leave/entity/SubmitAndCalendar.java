package com.insider.login.leave.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SUBMIT_AND_CALENDAR")
public class SubmitAndCalendar {

    @Id
    @Column(name = "SUBMIT_AND_CALENDAR_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int SubmitAndCalendarNo;

    @Column(name = "LEAVE_SUB_NO", nullable = false)
    private int leaveSubNo;

    @Column(name = "CALENDAR_NO", nullable = false)
    private int calendarNo;

    protected SubmitAndCalendar() {
    }

    public SubmitAndCalendar(int leaveSubNo, int calendarNo) {
        this.leaveSubNo = leaveSubNo;
        this.calendarNo = calendarNo;
    }

    public SubmitAndCalendar(int submitAndCalendarNo, int leaveSubNo, int calendarNo) {
        SubmitAndCalendarNo = submitAndCalendarNo;
        this.leaveSubNo = leaveSubNo;
        this.calendarNo = calendarNo;
    }

    public int getSubmitAndCalendarNo() {
        return SubmitAndCalendarNo;
    }

    public int getLeaveSubNo() {
        return leaveSubNo;
    }

    public int getCalendarNo() {
        return calendarNo;
    }

    @Override
    public String toString() {
        return "SubmitAndCalendar{" +
                "SubmitAndCalendarNo=" + SubmitAndCalendarNo +
                ", leaveSubNo=" + leaveSubNo +
                ", calendarNo=" + calendarNo +
                '}';
    }
}
