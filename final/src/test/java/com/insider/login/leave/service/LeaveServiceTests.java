package com.insider.login.leave.service;

import com.insider.login.leave.dto.LeaveSubmitDTO;
import com.insider.login.leave.entity.LeaveSubmit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@SpringBootTest
public class LeaveServiceTests {

    @Autowired
    private LeaveService leaveService;

    @Test
    @DisplayName("휴가 신청 내역 조회")
    void testSelectLeaveSubmitListByMemberId() {
        // given
        String applicantId = "2023123-XXXXX";

        // when
        List<LeaveSubmitDTO> leaveSubmits = leaveService.selectLeaveSubmitListByMemberId(applicantId);

        //then
        Assertions.assertFalse(leaveSubmits.isEmpty());
        leaveSubmits.forEach(System.out::println);
    }

    @Test
    @Transactional
    @DisplayName(("휴가 신청"))
    void testInsertSubmit() {
        // given
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO("2023123-XXXXX", Date.valueOf("2024-04-10"), Date.valueOf("2024-04-11"), Date.valueOf("2024-04-05"), "연차", "휴가 상신입니다.");

        // when
        String result = leaveService.insertSubmit(leaveSubmitDTO);

        //then
        Assertions.assertFalse(leaveService.selectLeaveSubmitListByMemberId(leaveSubmitDTO.getLeaveSubApplicant()).isEmpty());
        leaveService.selectLeaveSubmitListByMemberId(leaveSubmitDTO.getLeaveSubApplicant()).forEach(System.out::println);
        Assertions.assertEquals(result, "신청 등록 성공");

    }

    @Test
    @Transactional
    @DisplayName("휴가 신청 취소")
    void testDeleteSubmit(){
        // given
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO("2023123-XXXXX", Date.valueOf("2024-04-10"), Date.valueOf("2024-04-11"), Date.valueOf("2024-04-05"), "연차", "휴가 상신입니다.");
        int leaveSubNo = 1;

        // when
        leaveService.insertSubmit(leaveSubmitDTO);
        String result = leaveService.deleteSubmit(leaveSubNo);


        // then
        Assertions.assertEquals(result, "신청 취소 성공");
    }

    @Test
    @Transactional
    @DisplayName("휴가 취소 요청")
    void testInsertSubmitCancel() {
        // given
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(1, "2023123-XXXXX", Date.valueOf("2024-04-10"), Date.valueOf("2024-04-11"), Date.valueOf("2024-04-09"),  "휴가 취소 상신입니다.");

    }

    @Test
    @DisplayName("발생내역조회")
    void testSelectAccrualList() {

    }

    @Test
    @Transactional
    @DisplayName("휴가 발생")
    void testInsertAccrual() {

    }

    @Test
    @Transactional
    @DisplayName("휴가신청내역조회")
    void testSelectSubmitList() {

    }




}
