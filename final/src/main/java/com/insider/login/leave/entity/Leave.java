package com.insider.leave.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "LEAVE")
public class Leave {

    @Id
    @Column(name = "LEAVE_NO", nullable = false, columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveNo;            // 휴가 번호

    @Column(name = "MEMBER_ID", nullable = false, columnDefinition = "VARCHAR(20)")
    private String memberId;        // 사번

    @Column(name = "LEVAE_DAYS", nullable = false, columnDefinition = "INT")
    private int leaveDays;          // 휴가 일수

    @Column(name = "LEAVE=_TYPE", nullable = false, columnDefinition = "VARCHAR(12)")   // 연차, 특별휴가, 공가, 경조사
    private String leaveType;       // 휴가 유형

    protected Leave() {
    }

    public Leave(int leaveNo, String memberId, int leaveDays, String leaveType) {
        this.leaveNo = leaveNo;
        this.memberId = memberId;
        this.leaveDays = leaveDays;
        this.leaveType = leaveType;
    }

    public int getLeaveNo() {
        return leaveNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public int getLeaveDays() {
        return leaveDays;
    }

    public String getLeaveType() {
        return leaveType;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "leaveNo=" + leaveNo +
                ", memberId='" + memberId + '\'' +
                ", leaveDays=" + leaveDays +
                ", leaveType='" + leaveType + '\'' +
                '}';
    }
}
