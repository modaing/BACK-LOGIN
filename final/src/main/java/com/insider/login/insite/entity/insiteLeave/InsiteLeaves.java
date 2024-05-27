package com.insider.login.insite.entity.insiteLeave;

import jakarta.persistence.*;
@Entity(name = "insiteLeaves")
@Table(name = "LEAVES")
public class InsiteLeaves {

        @Id
        @Column(name = "LEAVE_NO", nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int leaveNo;            // 휴가 번호

        @Column(name = "MEMBER_ID", nullable = false)
        private int memberId;        // 사번

        @Column(name = "LEVAE_DAYS", nullable = false)
        private int leaveDays;          // 휴가 일수

}
