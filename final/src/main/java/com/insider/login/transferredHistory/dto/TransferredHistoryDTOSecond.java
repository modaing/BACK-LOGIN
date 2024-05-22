package com.insider.login.transferredHistory.dto;

import java.time.LocalDate;

public class TransferredHistoryDTOSecond {

    private int transferredNo;                          // 식별자 번호
    private int newDepartNo;
    private String newDepartName;
    private String newPositionName;
    private LocalDate transferredDate;
    private int memberId;

    public TransferredHistoryDTOSecond() {
    }

    public TransferredHistoryDTOSecond(int transferredNo, int newDepartNo, String newDepartName, String newPositionName, LocalDate transferredDate, int memberId) {
        this.transferredNo = transferredNo;
        this.newDepartNo = newDepartNo;
        this.newDepartName = newDepartName;
        this.newPositionName = newPositionName;
        this.transferredDate = transferredDate;
        this.memberId = memberId;
    }

    public int getTransferredNo() {
        return transferredNo;
    }

    public void setTransferredNo(int transferredNo) {
        this.transferredNo = transferredNo;
    }

    public int getNewDepartNo() {
        return newDepartNo;
    }

    public void setNewDepartNo(int newDepartNo) {
        this.newDepartNo = newDepartNo;
    }

    public String getNewDepartName() {
        return newDepartName;
    }

    public void setNewDepartName(String newDepartName) {
        this.newDepartName = newDepartName;
    }

    public String getNewPositionName() {
        return newPositionName;
    }

    public void setNewPositionName(String newPositionName) {
        this.newPositionName = newPositionName;
    }

    public LocalDate getTransferredDate() {
        return transferredDate;
    }

    public void setTransferredDate(LocalDate transferredDate) {
        this.transferredDate = transferredDate;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "TransferredHistoryDTOSecond{" +
                "transferredNo=" + transferredNo +
                ", newDepartNo=" + newDepartNo +
                ", newDepartName='" + newDepartName + '\'' +
                ", newPositionName='" + newPositionName + '\'' +
                ", transferredDate=" + transferredDate +
                ", memberId=" + memberId +
                '}';
    }
}
