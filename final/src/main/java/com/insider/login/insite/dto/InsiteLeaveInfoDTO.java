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


}
