package com.insider.login.leave.dto;

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

    private String leaveSubReason;              // 신청 사유

    public LeaveSubmitDTO() {
    }

    // 휴가 신청용
    public LeaveSubmitDTO(int leaveSubNo, String leaveSubApplicant, Date leaveSubStartDate, Date leaveSubEndDate, Date leaveSubApplyDate, String leaveSubReason) {
        this.leaveSubNo = leaveSubNo;
        this.leaveSubApplicant = leaveSubApplicant;
        this.leaveSubStartDate = leaveSubStartDate;
        this.leaveSubEndDate = leaveSubEndDate;
        this.leaveSubApplyDate = leaveSubApplyDate;
        this.leaveSubReason = leaveSubReason;
    }

    // 휴가 취소 요청용
    public LeaveSubmitDTO(String leaveSubApplicant, Date leaveSubStartDate, Date leaveSubEndDate, Date leaveSubApplyDate, String leaveSubType, String leaveSubReason) {
        this.leaveSubApplicant = leaveSubApplicant;
        this.leaveSubStartDate = leaveSubStartDate;
        this.leaveSubEndDate = leaveSubEndDate;
        this.leaveSubApplyDate = leaveSubApplyDate;
        this.leaveSubType = leaveSubType;
        this.leaveSubReason = leaveSubReason;
    }



    public LeaveSubmitDTO(int leaveSubNo, int refLeaveSubNo, String leaveSubApplicant, String applicantName, String approverName, Date leaveSubStartDate, Date leaveSubEndDate, Date leaveSubApplyDate, String leaveSubType, String leaveSubStatus, Date leaveSubProcessDate, String leaveSubReason) {
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
        this.leaveSubReason = leaveSubReason;
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

    public String getLeaveSubReason() {
        return leaveSubReason;
    }

    public void setLeaveSubReason(String leaveSubReason) {
        this.leaveSubReason = leaveSubReason;
    }

    @Override
    public String toString() {
        return "LeaveSubmitDTO{" +
                "leaveSubNo=" + leaveSubNo +
                ", refLeaveSubNo=" + refLeaveSubNo +
                ", leaveSubApplicant='" + leaveSubApplicant + '\'' +
                ", applicantName='" + applicantName + '\'' +
                ", approverName='" + approverName + '\'' +
                ", leaveSubStartDate=" + leaveSubStartDate +
                ", leaveSubEndDate=" + leaveSubEndDate +
                ", leaveSubApplyDate=" + leaveSubApplyDate +
                ", leaveSubType='" + leaveSubType + '\'' +
                ", leaveSubStatus='" + leaveSubStatus + '\'' +
                ", leaveSubProcessDate=" + leaveSubProcessDate +
                ", leaveSubReason='" + leaveSubReason + '\'' +
                '}';
    }
}
