package com.insider.login.leave.dto;

public class LeaveAccrualDTO {

    private int leaveAccrualNo;                 // 발생 번호

    private String recipientId;                 // 대상자 사번

    private String recipientName;               // 대상자 사원명

    private String recipientDepart;             // 대상자 부서

    private String leaveAccrualReason;          // 발생 사유

    private int leaveAccrualDays;               // 발생 일수

    private String leaveAccrualGrantor;         // 처리자 사원명

    public LeaveAccrualDTO() {
    }

    public LeaveAccrualDTO(int leaveAccrualNo, String recipientId, String recipientName, String recipientDepart, String leaveAccrualReason, int leaveAccrualDays, String leaveAccrualGrantor) {
        this.leaveAccrualNo = leaveAccrualNo;
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.recipientDepart = recipientDepart;
        this.leaveAccrualReason = leaveAccrualReason;
        this.leaveAccrualDays = leaveAccrualDays;
        this.leaveAccrualGrantor = leaveAccrualGrantor;
    }

    public int getLeaveAccrualNo() {
        return leaveAccrualNo;
    }

    public void setLeaveAccrualNo(int leaveAccrualNo) {
        this.leaveAccrualNo = leaveAccrualNo;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientDepart() {
        return recipientDepart;
    }

    public void setRecipientDepart(String recipientDepart) {
        this.recipientDepart = recipientDepart;
    }

    public String getLeaveAccrualReason() {
        return leaveAccrualReason;
    }

    public void setLeaveAccrualReason(String leaveAccrualReason) {
        this.leaveAccrualReason = leaveAccrualReason;
    }

    public int getLeaveAccrualDays() {
        return leaveAccrualDays;
    }

    public void setLeaveAccrualDays(int leaveAccrualDays) {
        this.leaveAccrualDays = leaveAccrualDays;
    }

    public String getLeaveAccrualGrantor() {
        return leaveAccrualGrantor;
    }

    public void setLeaveAccrualGrantor(String leaveAccrualGrantor) {
        this.leaveAccrualGrantor = leaveAccrualGrantor;
    }

    @Override
    public String toString() {
        return "LeaveAccrualDTO{" +
                "leaveAccrualNo=" + leaveAccrualNo +
                ", recipientId='" + recipientId + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", recipientDepart='" + recipientDepart + '\'' +
                ", leaveAccrualReason='" + leaveAccrualReason + '\'' +
                ", leaveAccrualDays=" + leaveAccrualDays +
                ", leaveAccrualGrantor='" + leaveAccrualGrantor + '\'' +
                '}';
    }
}
