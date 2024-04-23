package com.insider.login.commute.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CorrectionDTO {

    private int corrNo;                         // 정정 요청 번호
    private int commuteNo;                      // 출퇴근 번호
    private String reqStartWork;                // 정정 요청 출근 시간
    private String reqEndWork;                  // 정정 요청 퇴근 시간
    private String reasonForCorr;               // 정정 사유
    private LocalDate corrRegistrationDate;     // 정정 등록 일자
    private String corrStatus;                  // 정정 상태
    private String reasonForRejection;          // 반려 사유
    private LocalDate corrProcessingDate;       // 정정 처리 일자
}
