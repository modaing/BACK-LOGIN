package com.insider.login.commute.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NewCorrectionDTO {

    private int corrNo;                         // 정정 요청 번호
    private int commuteNo;                      // 출퇴근 번호
    private int memberId;                       // 회원 번호
    private LocalDate workingDate;              // 정정 대상 일자
    private String reqStartWork;                // 정정 요청 출근 시간
    private String reqEndWork;                  // 정정 요청 퇴근 시간
    private String reasonForCorr;               // 정정 사유
    private LocalDate corrRegistrationDate;     // 정정 등록 일자
    private String corrStatus;                  // 정정 상태
    private String reasonForRejection;          // 반려 사유
    private LocalDate corrProcessingDate;       // 정정 처리 일자

    public NewCorrectionDTO(int memberId, LocalDate workingDate, String reqStartWork, String reqEndWork, LocalDate corrRegistrationDate, String corrStatus, String reasonForCorr) {
        this.memberId = memberId;
        this.workingDate = workingDate;
        this.reqStartWork = reqStartWork;
        this.reqEndWork = reqEndWork;
        this.corrRegistrationDate = corrRegistrationDate;
        this.corrStatus = corrStatus;
        this.reasonForCorr = reasonForCorr;
    }
}
