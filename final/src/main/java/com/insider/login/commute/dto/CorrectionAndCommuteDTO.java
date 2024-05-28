package com.insider.login.commute.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CorrectionAndCommuteDTO {

    private int commuteNo;                  // 출퇴근 번호
    private int memberId;                   // 사번
    private LocalDate workingDate;          // 근무 일자
    private LocalTime startWork;            // 출근 시간
    private LocalTime endWork;              // 퇴근 시간
    private String workingStatus;           // 근무 상태
    private int totalWorkingHours;          // 총 근무 시간
    private int corrNo;                         // 정정 요청 번호
    private String reqStartWork;                // 정정 요청 출근 시간
    private String reqEndWork;                  // 정정 요청 퇴근 시간
    private String reasonForCorr;               // 정정 사유
    private LocalDate corrRegistrationDate;     // 정정 등록 일자
    private String corrStatus;                  // 정정 상태
    private String reasonForRejection;          // 반려 사유
    private LocalDate corrProcessingDate;       // 정정 처리 일자

    public CorrectionAndCommuteDTO(int memberId, LocalDate workingDate, LocalTime startWork, LocalTime endWork, String workingStatus, int totalWorkingHours) {
        this.memberId = memberId;
        this.workingDate = workingDate;
        this.startWork = startWork;
        this.endWork = endWork;
        this.workingStatus = workingStatus;
        this.totalWorkingHours = totalWorkingHours;
    }
}
