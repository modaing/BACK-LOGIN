package com.insider.leave.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "LEAVE_ACCRUAL")
public class LeaveAccrual {

    @Id
    @Column(name = "LEAVE_ACCRUAL_NO", nullable = false, columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveAccrualNo;                 // 발생 번호

    @Column(name = "RECIPIENT_ID", nullable = false, columnDefinition = "VARCHAR(20)")
    private String recipientId;                 // 대상자 사번 ( 사번으로 DTO에 사원명, 부서 담기)

    @Column(name = "LEAVE_ACCRUAL_GRANTOR", nullable = false, columnDefinition = "VARCHAR(30)")
    private String leaveAccrualGrantor;         // 처리자 사번 (사번으로 DTO에 사원명 담기)

    @Column(name = "LEAVE_ACCRUAL_REASON", nullable = false, columnDefinition = "VARCHAR(60)")
    private String leaveAccrualReason;          // 발생 사유

    @Column(name = "LEAVE_ACCRUAL_DAYS", nullable = false, columnDefinition = "INT")
    private int leaveAccrualDays;               // 발생 일수



    protected LeaveAccrual() {}

    public LeaveAccrual(int leaveAccrualNo, String recipientId, String leaveAccrualReason, int leaveAccrualDays, String leaveAccrualGrantor) {
        this.leaveAccrualNo = leaveAccrualNo;
        this.recipientId = recipientId;
        this.leaveAccrualReason = leaveAccrualReason;
        this.leaveAccrualDays = leaveAccrualDays;
        this.leaveAccrualGrantor = leaveAccrualGrantor;
    }

    public int getLeaveAccrualNo() {
        return leaveAccrualNo;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getLeaveAccrualReason() {
        return leaveAccrualReason;
    }

    public int getLeaveAccrualDays() {
        return leaveAccrualDays;
    }

    public String getLeaveAccrualGrantor() {
        return leaveAccrualGrantor;
    }

    @Override
    public String toString() {
        return "LeaveAccrual{" +
                "leaveAccrualNo=" + leaveAccrualNo +
                ", recipientId='" + recipientId + '\'' +
                ", leaveAccrualReason='" + leaveAccrualReason + '\'' +
                ", leaveAccrualDays=" + leaveAccrualDays +
                ", leaveAccrualGrantor='" + leaveAccrualGrantor + '\'' +
                '}';
    }
}
