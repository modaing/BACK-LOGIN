package com.insider.leave.dto;

import java.sql.Date;

public class LeaveSubmitDTO {

    private int leaveSubNo;                     // 신청 번호

    private int refLeaveSubNo;                  // 상위 신청 번호 (취소 요청 시 사용)

    private String leaveSubApplicant;           // 신청자 사번

    private String applicantName;               // 신청자 사원명

    private String approverName;                // 승인자 사원명

    private Date leaveSubStartDate;             // 휴가 시작일

    private Date leaveSubEndDate;               // 휴가 종료일

    private Date leaveSubApplyDate;             // 신청 일자

    private String leaveSubType;                // 휴가 유형

    private String leaveSubStatus;              // 처리 상태

    private Date leaveSubProcessDate;           // 처리 일자

    public LeaveSubmitDTO() {
    }

    public LeaveSubmitDTO(String leaveSubApplicant, Date leaveSubStartDate, Date leaveSubEndDate, Date leaveSubApplyDate, String leaveSubType) {
        this.leaveSubApplicant = leaveSubApplicant;
        this.leaveSubStartDate = leaveSubStartDate;
        this.leaveSubEndDate = leaveSubEndDate;
        this.leaveSubApplyDate = leaveSubApplyDate;
        this.leaveSubType = leaveSubType;
    }

    public LeaveSubmitDTO(String leaveSubApplicant, String applicantName, Date leaveSubStartDate, Date leaveSubEndDate, Date leaveSubApplyDate, String leaveSubType) {
        this.leaveSubApplicant = leaveSubApplicant;
        this.applicantName = applicantName;
        this.leaveSubStartDate = leaveSubStartDate;
        this.leaveSubEndDate = leaveSubEndDate;
        this.leaveSubApplyDate = leaveSubApplyDate;
        this.leaveSubType = leaveSubType;
    }

    public LeaveSubmitDTO(int leaveSubNo, int refLeaveSubNo, String leaveSubApplicant, String applicantName, String approverName, Date leaveSubStartDate, Date leaveSubEndDate, Date leaveSubApplyDate, String leaveSubType, String leaveSubStatus, Date leaveSubProcessDate) {
        this.leaveSubNo = leaveSubNo;
        this.refLeaveSubNo = refLeaveSubNo;
        this.leaveSubApplicant = leaveSubApplicant;
        this.applicantName = applicantName;
        this.approverName = approverName;
        this.leaveSubStartDate = leaveSubStartDate;
        this.leaveSubEndDate = leaveSubEndDate;
        this.leaveSubApplyDate = leaveSubApplyDate;
        this.leaveSubType = leaveSubType;
        this.leaveSubStatus = leaveSubStatus;
        this.leaveSubProcessDate = leaveSubProcessDate;
    }

    public int getLeaveSubNo() {
        return leaveSubNo;
    }

    public void setLeaveSubNo(int leaveSubNo) {
        this.leaveSubNo = leaveSubNo;
    }

    public int getRefLeaveSubNo() {
        return refLeaveSubNo;
    }

    public void setRefLeaveSubNo(int refLeaveSubNo) {
        this.refLeaveSubNo = refLeaveSubNo;
    }

    public String getLeaveSubApplicant() {
        return leaveSubApplicant;
    }

    public void setLeaveSubApplicant(String leaveSubApplicant) {
        this.leaveSubApplicant = leaveSubApplicant;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public Date getLeaveSubStartDate() {
        return leaveSubStartDate;
    }

    public void setLeaveSubStartDate(Date leaveSubStartDate) {
        this.leaveSubStartDate = leaveSubStartDate;
    }

    public Date getLeaveSubEndDate() {
        return leaveSubEndDate;
    }

    public void setLeaveSubEndDate(Date leaveSubEndDate) {
        this.leaveSubEndDate = leaveSubEndDate;
    }

    public Date getLeaveSubApplyDate() {
        return leaveSubApplyDate;
    }

    public void setLeaveSubApplyDate(Date leaveSubApplyDate) {
        this.leaveSubApplyDate = leaveSubApplyDate;
    }

    public String getLeaveSubType() {
        return leaveSubType;
    }

    public void setLeaveSubType(String leaveSubType) {
        this.leaveSubType = leaveSubType;
    }

    public String getLeaveSubStatus() {
        return leaveSubStatus;
    }

    public void setLeaveSubStatus(String leaveSubStatus) {
        this.leaveSubStatus = leaveSubStatus;
    }

    public Date getLeaveSubProcessDate() {
        return leaveSubProcessDate;
    }

    public void setLeaveSubProcessDate(Date leaveSubProcessDate) {
        this.leaveSubProcessDate = leaveSubProcessDate;
    }

    @Override
    public String toString() {
        return "LeaveSubmitDTO{" +
                "leaveSubNo=" + leaveSubNo +
                ", refLeaveSubNo=" + refLeaveSubNo +
                ", applicantId='" + leaveSubApplicant + '\'' +
                ", applicantName='" + applicantName + '\'' +
                ", approverName='" + approverName + '\'' +
                ", leaveSubStartDate=" + leaveSubStartDate +
                ", leaveSubEndDate=" + leaveSubEndDate +
                ", leaveSubApplyDate=" + leaveSubApplyDate +
                ", leaveSubType='" + leaveSubType + '\'' +
                ", leaveSubStatus='" + leaveSubStatus + '\'' +
                ", leaveSubProcessDate=" + leaveSubProcessDate +
                '}';
    }
}
