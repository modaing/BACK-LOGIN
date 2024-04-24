package com.insider.login.leave.service;

import com.insider.login.leave.common.LeaveSubmitBuilder;
import com.insider.login.leave.dto.LeaveSubmitDTO;
import com.insider.login.leave.entity.LeaveSubmit;
import com.insider.login.leave.repository.LeaveAccrualRepository;
import com.insider.login.leave.repository.LeaveRepository;
import com.insider.login.leave.repository.LeaveSubmitRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LeaveService {

    private LeaveAccrualRepository leaveAccrualRepository;
    private LeaveRepository leaveRepository;
    private LeaveSubmitRepository leaveSubmitRepository;
    private ModelMapper modelMapper;

    public LeaveService(LeaveAccrualRepository leaveAccrualRepository, LeaveRepository leaveRepository, LeaveSubmitRepository leaveSubmitRepository, ModelMapper modelMapper) {
        this.leaveAccrualRepository = leaveAccrualRepository;
        this.leaveRepository = leaveRepository;
        this.leaveSubmitRepository = leaveSubmitRepository;
        this.modelMapper = modelMapper;
    }

    public List<LeaveSubmitDTO> selectLeaveSubmitListByMemberId(String applicantId) {

        log.info("휴가 신청 내역 확인 시작 ======================================================");

        // 사번으로 해당 사원의 휴가신청내역을 가져옴
        List<LeaveSubmit> submitList = leaveSubmitRepository.findByMemberId(applicantId);

        log.info("엔티티 확인 ==========================", submitList);
        List<LeaveSubmitDTO> DTOList = new ArrayList<>();

        for (LeaveSubmit submit : submitList) {
            LeaveSubmitDTO leaveSubmitDTO = modelMapper.map(submit, LeaveSubmitDTO.class);

            // 사번으로 사원명 조회해서 DTO에 넣기 ( member 머지되면 추가 예정 )
//            leaveSubmitDTO.setApplicantName(leaveSubmitRepository.findMemberNameByMemberId(applicantId));
            DTOList.add(leaveSubmitDTO);
        }

        for (LeaveSubmitDTO DTO : DTOList) {
            log.info("휴가 내역 조회 =================================", DTO);

        }

        return DTOList;

    }

    @Transactional
    public String insertSubmit(LeaveSubmitDTO leaveSubmitDTO) {

        log.info("[LeaveService] insertLeave Start ================================");

        int result = 0;

        try {
            LeaveSubmit leaveSubmit = new LeaveSubmitBuilder()
                    .setLeaveSubApplicant(leaveSubmitDTO.getLeaveSubApplicant())
                    .setLeaveSubStartDate(leaveSubmitDTO.getLeaveSubStartDate())
                    .setLeaveSubEndDate(leaveSubmitDTO.getLeaveSubEndDate())
                    .setLeaveSubApplyDate(leaveSubmitDTO.getLeaveSubApplyDate())
                    .setLeaveSubType(leaveSubmitDTO.getLeaveSubType())
                    .setLeaveSubStatus("대기")
                    .setLeaveSubReason(leaveSubmitDTO.getLeaveSubReason())
                    .build();

            log.info("[LeaveService] LeaveSubmit", leaveSubmit);
            System.out.println(leaveSubmit);


            leaveSubmitRepository.save(leaveSubmit);
            result = 1;

        } catch (Exception e) {
            log.info("[Leave] Exception!");

            result = 0;
        }

        return (result > 0) ? "신청 등록 성공" : "신청 등록 실패";
    }

    @Transactional
    public String deleteSubmit(int leaveSubNo) {

        int result = 0;

        try {

            leaveSubmitRepository.deleteById(leaveSubNo);

            result = 1;

        } catch (Exception e) {
            log.info("[Leave] Exception!");

            result = 0;
        }

        return (result > 0) ? "신청 취소 성공" : "신청 취소 실패";
    }
}
