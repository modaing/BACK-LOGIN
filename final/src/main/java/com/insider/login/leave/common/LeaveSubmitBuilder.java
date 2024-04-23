package com.insider.login.leave.common;


import com.insider.login.leave.entity.LeaveSubmit;

import java.sql.Date;

public class LeaveSubmitBuilder {
    private int leaveSubNo;
    private int refLeaveSubNo;
    private String leaveSubApplicant;
    private String leaveSubApprover;
    private Date leaveSubStartDate;
    private Date leaveSubEndDate;
    private Date leaveSubApplyDate;
    private String leaveSubType;
    private String leaveSubStatus;
    private Date leaveSubProcessDate;

    public LeaveSubmitBuilder setLeaveSubNo(int leaveSubNo) {
        this.leaveSubNo = leaveSubNo;
        return this;
    }

    public LeaveSubmitBuilder setRefLeaveSubNo(int refLeaveSubNo) {
        this.refLeaveSubNo = refLeaveSubNo;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubApplicant(String leaveSubApplicant) {
        this.leaveSubApplicant = leaveSubApplicant;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubApprover(String leaveSubApprover) {
        this.leaveSubApprover = leaveSubApprover;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubStartDate(Date leaveSubStartDate) {
        this.leaveSubStartDate = leaveSubStartDate;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubEndDate(Date leaveSubEndDate) {
        this.leaveSubEndDate = leaveSubEndDate;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubApplyDate(Date leaveSubApplyDate) {
        this.leaveSubApplyDate = leaveSubApplyDate;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubType(String leaveSubType) {
        this.leaveSubType = leaveSubType;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubStatus(String leaveSubStatus) {
        this.leaveSubStatus = leaveSubStatus;
        return this;
    }

    public LeaveSubmitBuilder setLeaveSubProcessDate(Date leaveSubProcessDate) {
        this.leaveSubProcessDate = leaveSubProcessDate;
        return this;
    }

    public LeaveSubmit build() {
        return new LeaveSubmit(leaveSubNo, refLeaveSubNo, leaveSubApplicant, leaveSubApprover, leaveSubStartDate, leaveSubEndDate, leaveSubApplyDate, leaveSubType, leaveSubStatus, leaveSubProcessDate);
    }
}
