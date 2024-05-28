package com.insider.login.commute.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Entity(name = "newCorrection")
@Table(name = "commute_corr")
@AllArgsConstructor
@Getter
@ToString
public class NewCorrection {

    @Id
    @Column(name = "corr_no", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int corrNo;                         // 정정 요청 번호

    @Column(name = "req_start_work")
    private String reqStartWork;                // 정정 요청 출근 시간

    @Column(name = "req_end_work")
    private String reqEndWork;                  // 정정 요청 퇴근 시간

    @Column(name = "reason_for_corr", nullable = false)
    private String reasonForCorr;               // 정정 사유

    @Column(name = "corr_registration_date", nullable = false)
    private LocalDate corrRegistrationDate;     // 정정 등록 일자

    @Column(name = "corr_status", nullable = false)
    private String corrStatus;                  // 정정 상태

    @Column(name = "reason_for_rejection")
    private String reasonForRejection;          // 반려 사유

    @Column(name = "corr_processing_date")
    private LocalDate corrProcessingDate;       // 정정 처리 일자

    protected NewCorrection() {}
}
