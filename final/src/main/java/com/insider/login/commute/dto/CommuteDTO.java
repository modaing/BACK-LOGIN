package com.insider.login.commute.dto;

import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommuteDTO {

    private int commuteNo;                  // 출퇴근 번호
    private int memberId;                   // 사번
    private LocalDate workingDate;          // 근무 일자
    private LocalTime startWork;            // 출근 시간
    private LocalTime endWork;              // 퇴근 시간
    private String workingStatus;           // 근무 상태
    private Duration totalWorkingHours;     // 총 근무 시간
}
