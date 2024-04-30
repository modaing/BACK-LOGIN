package com.insider.login.leave.util;

import com.insider.login.leave.entity.LeaveSubmit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LeaveUtil {

    public int calculateLeaveDays(LeaveSubmit leaveSubmit) {
        LocalDate startDate = leaveSubmit.getLeaveSubStartDate();
        LocalDate endDate = leaveSubmit.getLeaveSubEndDate();

        // 두 날짜 사이의 일수 계산
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1; // 시작일과 종료일을 포함하여 계산
    }
}
