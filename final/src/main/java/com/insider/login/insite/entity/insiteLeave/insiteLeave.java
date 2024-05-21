package com.insider.login.insite.entity.insiteLeave;

import com.insider.login.insite.entity.InsiteMember;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "LEAVE_SUBMIT")
public class insiteLeave {

    @Id
    @Column(name = "LEAVE_SUB_NO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveSubNo;                     // 신청 번호

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "member_id", nullable = false)
    private InsiteMember leaveMember;

    @Column(name = "LEAVE_SUB_START_DATE", nullable = false) // YYYY-MM-DD
    private LocalDate leaveSubStartDate;             // 휴가 시작일

    @Column(name = "LEAVE_SUB_END_DATE", nullable = false)  // YYYY-MM-DD
    private LocalDate  leaveSubEndDate;               // 휴가 종료일

    @Column(name = "LEAVE_SUB_STATUS", nullable = false, columnDefinition = "VARCHAR(6)") // 승인, 반려, 대기
    private String leaveSubStatus;              // 처리 상태








    protected insiteLeave() {
    }

}
