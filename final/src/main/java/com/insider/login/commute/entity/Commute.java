package com.insider.login.commute.entity;

import com.insider.login.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "commute")
@AllArgsConstructor
@Getter
@ToString
public class Commute {

    @Id
    @Column(name = "commute_no", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commuteNo;                                  // 출퇴근 번호

    @Column(name = "member_id", nullable = false)
    private int memberId;                                   // 사번

    @Column(name = "working_date", nullable = false)
    private LocalDate workingDate;                          // 근무 일자

    @Column(name = "start_work", nullable = false)
    private LocalTime startWork;                            // 출근 시간

    @Column(name = "end_work")
    private LocalTime endWork;                              // 퇴근 시간

    @Column(name = "working_status", nullable = false)
    private String workingStatus;                           // 근무 상태

    @Column(name = "total_working_hours")
    private int totalWorkingHours;                          // 총 근무 시간

    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;                                  // 구성원


    protected Commute() {}

    public Commute(int memberId, LocalDate workingDate, LocalTime startWork, LocalTime endWork, String workingStatus, int totalWorkingHours) {
        this.memberId = memberId;
        this.workingDate = workingDate;
        this.startWork = startWork;
        this.endWork = endWork;
        this.workingStatus = workingStatus;
        this.totalWorkingHours = totalWorkingHours;
    }

    public Commute(LocalTime endWork, String workingStatus, int totalWorkingHours) {
        this.endWork = endWork;
        this.workingStatus = workingStatus;
        this.totalWorkingHours = totalWorkingHours;
    }

    public Commute(int memberId, LocalDate workingDate, LocalTime startWork, LocalTime endWork, String workingStatus, int totalWorkingHours, Member member) {
        this.memberId = memberId;
        this.workingDate = workingDate;
        this.startWork = startWork;
        this.endWork = endWork;
        this.workingStatus = workingStatus;
        this.totalWorkingHours = totalWorkingHours;
        this.member = member;
    }

    public void setEndWork(LocalTime endWork) {
        this.endWork = endWork;
    }

    public void setWorkingStatus(String workingStatus) {
        this.workingStatus = workingStatus;
    }

    public void setTotalWorkingHours(int totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }
}
