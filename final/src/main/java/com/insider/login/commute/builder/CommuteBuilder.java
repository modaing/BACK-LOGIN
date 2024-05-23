package com.insider.login.commute.builder;

import com.insider.login.commute.entity.Commute;

import java.time.LocalDate;
import java.time.LocalTime;

public class CommuteBuilder {

    private int commuteNo;
    private LocalDate workingDate;
    private LocalTime startWork;
    private LocalTime endWork;
    private String workingStatus;
    private int totalWorkingHours;

    public CommuteBuilder(int commuteNo, LocalDate workingDate, LocalTime startWork, LocalTime endWork, String workingStatus, int totalWorkingHours) {
        this.commuteNo = commuteNo;
        this.workingDate = workingDate;
        this.startWork = startWork;
        this.endWork = endWork;
        this.workingStatus = workingStatus;
        this.totalWorkingHours = totalWorkingHours;
    }

    public CommuteBuilder(LocalTime endWork, String workingStatus, int totalWorkingHours) {
        this.endWork = endWork;
        this.workingStatus = workingStatus;
        this.totalWorkingHours = totalWorkingHours;
    }

    public CommuteBuilder(Commute commute) {
    }

    public CommuteBuilder commuteNo(int val) {
        this.commuteNo = val;
        return this;
    }

    public CommuteBuilder workingDate(LocalDate val) {
        this.workingDate = val;
        return this;
    }

    public CommuteBuilder startWork(LocalTime val) {
        this.startWork = val;
        return this;
    }

    public CommuteBuilder endWork(LocalTime val) {
        this.endWork = val;
        return this;
    }

    public CommuteBuilder workingStatus(String val) {
        this.workingStatus = val;
        return this;
    }

    public CommuteBuilder totalWorkingHours(int val) {
        this.totalWorkingHours = val;
        return this;
    }

    public Commute builder() {
        return new Commute(commuteNo, workingDate, startWork, endWork, workingStatus, totalWorkingHours);
    }
}
