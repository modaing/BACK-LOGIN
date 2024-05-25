package com.insider.login.leave.service;

import com.insider.login.department.entity.Department;
import com.insider.login.department.repository.DepartmentRepository;
import com.insider.login.leave.dto.LeaveInfoDTO;
import com.insider.login.leave.dto.LeaveMemberDTO;
import com.insider.login.leave.entity.LeaveMember;
import com.insider.login.leave.entity.Leaves;
import com.insider.login.leave.repository.*;
import com.insider.login.leave.util.LeaveUtil;
import com.insider.login.leave.dto.LeaveAccrualDTO;
import com.insider.login.leave.dto.LeaveSubmitDTO;
import com.insider.login.leave.entity.LeaveAccrual;
import com.insider.login.leave.entity.LeaveSubmit;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LeaveService extends LeaveUtil {

    private final LeaveAccrualRepository leaveAccrualRepository;
    private final LeaveRepository leaveRepository;
    private final LeaveSubmitRepository leaveSubmitRepository;
    private final ModelMapper modelMapper;
    private final LeaveMemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;

    public LeaveService(LeaveAccrualRepository leaveAccrualRepository, LeaveRepository leaveRepository, LeaveSubmitRepository leaveSubmitRepository, ModelMapper modelMapper, LeaveMemberRepository memberRepository, DepartmentRepository departmentRepository) {
        this.leaveAccrualRepository = leaveAccrualRepository;
        this.leaveRepository = leaveRepository;
        this.leaveSubmitRepository = leaveSubmitRepository;
        this.modelMapper = modelMapper;
        this.memberRepository = memberRepository;
        this.departmentRepository = departmentRepository;
    }

    public Page<LeaveSubmitDTO> selectLeaveSubmitList(int applicantId, Pageable pageable) {
        try {
            // 조건에 따라 휴가신청내역을 가져옴
            Page<LeaveSubmit> submitList = null;
            if (applicantId > 0) {
                submitList = leaveSubmitRepository.findByMemberId(applicantId, pageable);
            } else {
                submitList = leaveSubmitRepository.findAll(pageable);
            }

            List<LeaveSubmitDTO> DTOList = new ArrayList<>();
            for (LeaveSubmit submit : submitList) {
                LeaveSubmitDTO leaveSubmitDTO = modelMapper.map(submit, LeaveSubmitDTO.class);

//                사번으로 사원명 조회해서 DTO에 넣기
                leaveSubmitDTO.setApplicantName(memberRepository.findNameByMemberId(submit.getLeaveSubApplicant()));

//                승인자 사번이 존재할 경우 승인자 사번으로 사원명을 조회해서 DTO에 넣기
                if (submit.getLeaveSubApprover() != 0) {
                    leaveSubmitDTO.setApproverName(memberRepository.findNameByMemberId(submit.getLeaveSubApprover()));
                }

                DTOList.add(leaveSubmitDTO);
            }

            // DTOList를 기존 pageable 정보를 가진 새로운 페이지로 만들어서 반환
            return new PageImpl<>(DTOList, submitList.getPageable(), submitList.getTotalElements());
        } catch (Exception e) {
            return Page.empty();
        }
    }

    @Transactional
    public String insertSubmit(LeaveSubmitDTO DTO) {
        try {
            // 휴가 신청 번호를 클라이언트에서 받아왔으면 취소 신청 요청임
            if (DTO.getLeaveSubNo() != 0) {
                int LeaveSubNo = DTO.getLeaveSubNo();
                DTO.setRefLeaveSubNo(DTO.getLeaveSubNo());
                DTO.setLeaveSubNo(0);

                // 취소 신청 요청을 보낸 기존 신청의 처리 상태 변경 후 업데이트
                LeaveSubmit leaveSubmit = leaveSubmitRepository.findById(LeaveSubNo);
                LeaveSubmitDTO updateDTO = modelMapper.map(leaveSubmit, LeaveSubmitDTO.class);
                updateDTO.setLeaveSubStatus("취소신청");
                leaveSubmitRepository.save(modelMapper.map(updateDTO, LeaveSubmit.class));
            }

            DTO.setLeaveSubStatus("대기");
            LeaveSubmit leaveSubmit = modelMapper.map(DTO, LeaveSubmit.class);

            leaveSubmitRepository.save(leaveSubmit);

            return "신청 등록 성공";
        } catch (Exception e) {
            return "신청 등록 실패";
        }
    }

    public String insertSubmitCancel(LeaveSubmitDTO DTO) {
        try {
            // 화면에서 받아온 정보를 취소 요청 등록 폼으로 수정
            LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO(DTO.getLeaveSubNo(), DTO.getLeaveSubApplicant(), DTO.getLeaveSubStartDate(), DTO.getLeaveSubEndDate(), DTO.getLeaveSubApplyDate(), "취소", "대기", DTO.getLeaveSubReason());

            LeaveSubmit leaveSubmit = modelMapper.map(leaveSubmitDTO, LeaveSubmit.class);

            leaveSubmitRepository.save(leaveSubmit);

            return "취소요청 등록 성공";
        } catch (Exception e) {
            return "취소요청 등록 실패";
        }
    }

    public Page<LeaveAccrualDTO> selectAccrualList(Pageable pageable) {
        try {
            Page<LeaveAccrual> accrualList = leaveAccrualRepository.findAll(pageable);

            List<LeaveAccrualDTO> DTOList = new ArrayList<>();
            if (accrualList != null) {

                for (LeaveAccrual accrual : accrualList) {
                    LeaveAccrualDTO DTO = modelMapper.map(accrual, LeaveAccrualDTO.class);
                    DTO.setRecipientName(memberRepository.findNameByMemberId(accrual.getRecipientId()));
                    DTOList.add(DTO);
                }

                // DTOList를 기존 pageable 정보를 가진 새로운 페이지로 만들어서 반환
                return new PageImpl<>(DTOList, accrualList.getPageable(), accrualList.getTotalElements());
            } else {
                return Page.empty();
            }
        } catch (Exception e) {
            return Page.empty();
        }
    }

    @Transactional
    public String insertAccrual(LeaveAccrualDTO accrualDTO) {
        try {
            LeaveAccrual leaveAccrual = modelMapper.map(accrualDTO, LeaveAccrual.class);

            LeaveAccrual ett = leaveAccrualRepository.save(leaveAccrual);

            // 발생 등록한 정보에서 휴가 정보에 필요한 정보 추출 후 save
            Leaves leaves = new Leaves(ett.getRecipientId(), ett.getLeaveAccrualDays(), "특별휴가");
            leaveRepository.save(leaves);

            return "휴가발생 등록 성공";
        } catch (Exception e) {
            return "휴가발생 등록 실패";
        }
    }

    public List<LeaveMemberDTO> selectMemberList(String name) {
        try {
            List<LeaveMember> leaveMembers = memberRepository.findByName(name);

            List<LeaveMemberDTO> DTOList = memberRepository.findByName(name).stream()
                    .map(leaveMember -> {
                        LeaveMemberDTO dto = modelMapper.map(leaveMember, LeaveMemberDTO.class);
                        return dto;
                    }).toList();

            // 조회된 부서 번호로 부서 이름 찾기
            for (LeaveMemberDTO DTO : DTOList) {
                Optional<Department> departmentOptional = departmentRepository.findById(DTO.getDepartNo());
                if (departmentOptional.isPresent()) {
                    Department department = departmentOptional.get();
                    DTO.setDepartment(department.getDepartName());
                } else {
                    DTO.setDepartment("없음");
                }
            }

            return DTOList;
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public String deleteSubmit(int leaveSubNo) {
        try {
            leaveSubmitRepository.deleteById(leaveSubNo);

            return "신청 취소 성공";
        } catch (Exception e) {
            return "신청 취소 실패";
        }
    }

    @Transactional
    public String updateSubmit(LeaveSubmitDTO leaveSubmitDTO) {
        try {
            // 엔티티를 dto로 변환 후 변경할 정보 삽입
            LeaveSubmitDTO newSubmit = modelMapper.map(leaveSubmitRepository.findById(leaveSubmitDTO.getLeaveSubNo()), LeaveSubmitDTO.class);
            newSubmit.setLeaveSubApprover(leaveSubmitDTO.getLeaveSubApprover());
            newSubmit.setLeaveSubStatus(leaveSubmitDTO.getLeaveSubStatus());
            newSubmit.setLeaveSubProcessDate(leaveSubmitDTO.getLeaveSubProcessDate());

                // 반려시 반려 사유 세팅
            if ("반려".equals(leaveSubmitDTO.getLeaveSubStatus())) {
                newSubmit.setLeaveSubReason(leaveSubmitDTO.getLeaveSubReason());
            }

            if (newSubmit.getRefLeaveSubNo() != 0) {

                LeaveSubmitDTO refSubmit = modelMapper.map(leaveSubmitRepository.findById(newSubmit.getRefLeaveSubNo()), LeaveSubmitDTO.class);

                if ("승인".equals(leaveSubmitDTO.getLeaveSubStatus())) {
                    // 취소 신청 승인 시 상위 신청 번호의 처리 상태를 취소로 변경
                    refSubmit.setLeaveSubStatus("취소승인");
                } else {
                    // 취소 신청 반려 시 상위 신청 번호의 처리 상태를 승인으로 복구
                    refSubmit.setLeaveSubStatus("취소반려");
                }
                leaveSubmitRepository.save(modelMapper.map(refSubmit, LeaveSubmit.class));
            }

            //신청 업데이트
            leaveSubmitRepository.save(modelMapper.map(newSubmit, LeaveSubmit.class));

            return "휴가처리 성공";
        } catch (Exception e) {
            return "휴가처리 실패";
        }
    }

    public Page<LeaveInfoDTO> selectLeavesList(Pageable pageable) {
        try {
            Page<Integer> leavesPage = leaveRepository.findDistinctMemberIds(pageable);

            List<LeaveInfoDTO> infoDTOList = leavesPage.stream()
                    .map(this::getLeaveInfoById)
                    .collect(Collectors.toList());

            return new PageImpl<>(infoDTOList, pageable, leavesPage.getTotalElements());
        } catch (Exception e) {
            return null;
        }
    }

    // 사번을 매개변수로 주면 해당 사원의 휴가 정보를 DTO로 반환하는 메소드
    public LeaveInfoDTO getLeaveInfoById(int memberId) {

        List<Leaves> leavesList = leaveRepository.findByMemberId(memberId);

        LeaveInfoDTO info = leaveInfoCalc(leavesList);

        String name = memberRepository.findNameByMemberId(memberId);
        info.setName(name);

        return addLeaveInfo(info);
    }

    // leaveInfo DTO에 소진 일수와 잔여 일수를 추가해서 다시 반환하는 메소드
    public LeaveInfoDTO addLeaveInfo(LeaveInfoDTO DTO) {

        // 소진 일수 (사번으로 신청내역을 조회해서 신청일수를 모두 더함)
        List<LeaveSubmit> submitList = leaveSubmitRepository.findByMemberId(DTO.getMemberId());

        int consumedDays = submitList.stream()
                // 제외할 대상 필터링
                .filter(submit -> !"취소".equals(submit.getLeaveSubType()))
                .filter(submit -> !"취소".equals(submit.getLeaveSubStatus()))
                .filter(submit -> !"반려".equals(submit.getLeaveSubStatus()))
                // 스트림의 각 요소마다 leaveDayCalc 메소드 실행 후 intStream으로 반환
                .mapToInt(this::leaveDaysCalc)
                // 스트림의 모든 요소를 더함
                .sum();

        // 잔여 일수 (총 부여 수에서 소진 일수를 뺌)
        int remainingDays = DTO.getTotalDays() - consumedDays;

        DTO.setConsumedDays(consumedDays);
        DTO.setRemainingDays(remainingDays);

        return DTO;
    }
}
