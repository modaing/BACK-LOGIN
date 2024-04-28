package com.insider.login.leave.service;


import com.insider.login.common.CommonController;
import com.insider.login.leave.dto.LeaveAccrualDTO;
import com.insider.login.leave.dto.LeaveSubmitDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;
import java.util.List;

@SpringBootTest
@Transactional
public class LeaveServiceTests extends CommonController {

    @Autowired
    private LeaveService leaveService;

    @Test
    @DisplayName("휴가 신청 내역 조회")
    void testSelectLeaveSubmitListByMemberId() {
        // given
        int applicantId = 202401001;

        // when
        Page<LeaveSubmitDTO> results = leaveService.selectLeaveSubmitListByMemberId(applicantId);

        //then
        // 가져온 리스트가 비어있지 않아야 함
        Assertions.assertFalse(results.isEmpty());
        // 조회된 내역의 신청자 사번이 확인하려한 사번과 일치해야함
        for (LeaveSubmitDTO dto : results) {
            System.out.println(dto.toString());
            Assertions.assertEquals(dto.getLeaveSubApplicant(), applicantId);

        }
    }

    @Test
    @DisplayName(("휴가 신청"))
    void testInsertSubmit() {
        // given
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(202312003, Date.valueOf("2024-04-10"), Date.valueOf("2024-04-11"), nowDate(), "연차", "휴가 상신입니다.");

        // when
        Page<LeaveSubmitDTO> before = leaveService.selectLeaveSubmitListByMemberId(leaveSubmitDTO.getLeaveSubApplicant());

        String result = leaveService.insertSubmit(leaveSubmitDTO);

        Page<LeaveSubmitDTO> after = leaveService.selectLeaveSubmitListByMemberId(leaveSubmitDTO.getLeaveSubApplicant());

        //then
        // 등록했을 때, 해당 사번으로 조회되는 리스트의 크기가 등록 전보다 증가해야함
        Assertions.assertTrue(after.getTotalElements() > before.getTotalElements());
        // 성공 결과 메시지를 반환해야함
        Assertions.assertEquals(result, "신청 등록 성공");

    }

    @Test
    @DisplayName("휴가 신청 취소(삭제)")
    void testDeleteSubmit() {
        // given
        int leaveSubNo = 1;

        // when
        String result = leaveService.deleteSubmit(leaveSubNo);

        // then
        // 성공 결과 메시지를 반환해야함
        Assertions.assertEquals(result, "신청 취소 성공");
        // 해당 신청번호로 조회되는 결과가 없어야함
        Assertions.assertNull(leaveService.selectSubmitByLeaveSubNo(leaveSubNo));
    }

    @Test
    @DisplayName("휴가 취소 요청")
    void testInsertSubmitCancel() {
        // given
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(1, 202312003, Date.valueOf("2024-04-10"), Date.valueOf("2024-04-11"), "휴가 취소 상신입니다.");
        leaveSubmitDTO.setLeaveSubApplyDate(nowDate());

        //when

        Page<LeaveSubmitDTO> before = leaveService.selectLeaveSubmitListByMemberId(leaveSubmitDTO.getLeaveSubApplicant());

        String result = leaveService.insertSubmitCancel(leaveSubmitDTO);

        Page<LeaveSubmitDTO> after = leaveService.selectLeaveSubmitListByMemberId(leaveSubmitDTO.getLeaveSubApplicant());


        //then
        // 등록했을 때, 해당 사번으로 조회되는 리스트의 크기가 등록 전보다 증가해야함
        Assertions.assertTrue(after.getTotalElements() > before.getTotalElements());
        // 성공 결과 메시지를 반환해야함
        Assertions.assertEquals(result, "취소요청 등록 성공");
    }

    @Test
    @DisplayName("발생내역조회")
    void testSelectAccrualList() {

        //when
        Page<LeaveAccrualDTO> results = leaveService.selectAccrualList();

        //then

    }

    @Test
    @DisplayName("휴가 발생")
    void testInsertAccrual() {

        // given
        LeaveAccrualDTO accrualDTO = new LeaveAccrualDTO(202312003, 3, "예비군");
        accrualDTO.setGrantorId(20205001);  // 토큰에서 따로 뽑아서 담아줌

        //when
        Page<LeaveAccrualDTO> before = leaveService.selectAccrualList();

        String result = leaveService.insertAccrual(accrualDTO);

        Page<LeaveAccrualDTO> after = leaveService.selectAccrualList();

        //then
        // 등록했을 때, 해당 사번으로 조회되는 리스트의 크기가 등록 전보다 증가해야함
        Assertions.assertTrue(after.getTotalElements() > before.getTotalElements());
        // 성공 메시지를 반환해야 함
        Assertions.assertEquals(result, "휴가발생 등록 성공");

    }

    @Test
    @DisplayName("상세조회")
    void testSelectSubmitByLeaveSubNo() {
        // given
        int leaveSubNo = 3;

        // when
        LeaveSubmitDTO result = leaveService.selectSubmitByLeaveSubNo(leaveSubNo);

        // then
        // 조회 결과가 있어야 함
        Assertions.assertNotNull(result);
        // 조회해온 결과의 신청번호가 의도한 것과 같아야 함
        Assertions.assertEquals(result.getLeaveSubNo(), leaveSubNo);

    }

}
