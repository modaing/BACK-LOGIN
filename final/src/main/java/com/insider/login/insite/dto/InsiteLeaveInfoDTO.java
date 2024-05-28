package com.insider.login.insite.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InsiteLeaveInfoDTO implements java.io.Serializable {

    private int memberId;                     // 사번

    private int totalDays;                    // 총 부여 일수

    private int consumedDays;                 // 소진 일수

    private String name;                      // 이름

    private int annualLeave;                  // 연차

    private int specialLeave;                 // 특별휴가

    private int remainingDays;                // 잔여 일수

    public InsiteLeaveInfoDTO(int memberId, int totalDays, int consumedDays) {
        this.memberId = memberId;
        this.totalDays = totalDays;
        this.consumedDays = consumedDays;
    }
}
