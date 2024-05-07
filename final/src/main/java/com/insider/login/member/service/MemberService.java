package com.insider.login.member.service;

import com.insider.login.department.dto.DepartmentDTO;
import com.insider.login.department.entity.Department;
import com.insider.login.department.repository.DepartmentRepository;
import com.insider.login.member.dto.MemberDTO;
import com.insider.login.member.entity.Member;
import com.insider.login.member.repository.MemberRepository;
import com.insider.login.position.dto.PositionDTO;
import com.insider.login.position.entity.Position;
import com.insider.login.transferredHistory.dto.TransferredHistoryDTO;
import com.insider.login.transferredHistory.entity.TransferredHistory;
import com.insider.login.transferredHistory.repository.TransferredHistoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class MemberService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final TransferredHistoryRepository transferredHistoryRepository;

    public MemberService(MemberRepository userRepository, ModelMapper modelMapper, DepartmentRepository departmentRepository, TransferredHistoryRepository transferredHistoryRepository) {
        this.memberRepository = userRepository;
        this.modelMapper = modelMapper;
        this.departmentRepository = departmentRepository;
        this.transferredHistoryRepository = transferredHistoryRepository;
    }

    public Optional<MemberDTO> findMember(int id) {
        System.out.println("id: " + id);
        Optional<Member> memberOptional = memberRepository.findById(id);

        System.out.println("찾은 member는: " + memberOptional);

        if (memberOptional.isPresent()) { // 존재한다면
            Member member = memberOptional.get();
            System.out.println("찾은 member는: " + member);
            MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
            memberDTO.setDepartmentDTO(DepartmentDTO.mapToDTO(member.getDepartment()));
            memberDTO.setPositionDTO(PositionDTO.mapToDTO(member.getPosition()));
            return Optional.of(memberDTO);
        } else {
            System.out.println("Member not found with id: " + id);
            return Optional.empty();
        }
    }

    /* 구성원 등록 */
    @Transactional
    public Member saveMember(MemberDTO memberDTO) {

        System.out.println("memberDTO 정보들: " + memberDTO);
        Member newMember = modelMapper.map(memberDTO, Member.class);
        System.out.println("member에 들어간 정보: " + newMember);
        Department department = modelMapper.map(memberDTO.getDepartmentDTO(), Department.class);
        Position position = modelMapper.map(memberDTO.getPositionDTO(), Position.class);
        newMember.setDepartment(department);
        newMember.setPosition(position);
        System.out.println("newMember에 저장된 department : " + newMember.getDepartment() + ", and position: " + newMember.getPosition());
        Member savedMember = memberRepository.save(newMember);
        System.out.println("확인용: " + savedMember);
        return savedMember;
    }

    public boolean findExistingMemberId(int setMemberId) {
        return memberRepository.existsByMemberId(setMemberId);
    }

    public Member getSpecificMember(Integer memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }

//    public Member updateMember(MemberDTO memberDTOInfos) {
//        Member updatedMember = memberRepository.updateMemberInfo(memberDTOInfos);
//        return updatedMember;
//    }

    public MemberDTO findSpecificMember(int memberId) {
        Member memberDetails = memberRepository.findById(memberId).orElse(null);
        MemberDTO specificMember = modelMapper.map(memberDetails, MemberDTO.class);
//        System.out.println("mapping하고 나서의 구성원: " + specificMember);
        return specificMember;
    }

    @Transactional
    public String updateMember(MemberDTO specificMember) {
        MemberDTO checkPositionOrDepartment = findSpecificMember(specificMember.getMemberId());
        System.out.println("position name: " + checkPositionOrDepartment.getPositionDTO().getPositionName());
        System.out.println("department name: " + checkPositionOrDepartment.getDepartmentDTO().getDepartName());
        int result = 0;

        try {
            Member updatedMember = modelMapper.map(specificMember, Member.class);
            Member savedInfo = memberRepository.save(updatedMember);
            System.out.println("바뀐 정보들: " + savedInfo);

            if (specificMember.getMemberStatus().equals("퇴직")) {
                scheduleMemberDeletion(updatedMember.getMemberId());
                System.out.println("현시간으로 3년뒤에 구성원 정보가 탈퇴됩니다");
            }

            if (!specificMember.getDepartmentDTO().getDepartName().equals(checkPositionOrDepartment.getDepartmentDTO().getDepartName()) && !specificMember.getPositionDTO().getPositionName().equals(checkPositionOrDepartment.getPositionDTO().getPositionName())) {
                TransferredHistoryDTO transferredHistoryDTO = new TransferredHistoryDTO();
                transferredHistoryDTO.setNewDepartNo(updatedMember.getDepartment().getDepartNo());
                transferredHistoryDTO.setNewPositionName(updatedMember.getPosition().getPositionName());
                transferredHistoryDTO.setMemberId(updatedMember.getMemberId());
                transferredHistoryDTO.setTransferredDate(LocalDate.now());
                TransferredHistory updatedTransferredHistoryRecord = modelMapper.map(transferredHistoryDTO, TransferredHistory.class);
                transferredHistoryRepository.save(updatedTransferredHistoryRecord);
                System.out.println("transferred history saved");
            }
//            if ()
            result = 1;
        } catch (Exception e) {
            result = 0;
        }
        return (result > 0) ? "success" : "failed";
    }

    private void scheduleMemberDeletion(int memberId) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        LocalDate currentDate = LocalDate.now();
        LocalDate deleteDate = currentDate.plus(3, ChronoUnit.YEARS); // 퇴직으로 변경하는 시점으로 3년 뒤
        long delay = ChronoUnit.DAYS.between(currentDate, deleteDate);

        executorService.schedule(() -> {
            deleteMemberById(memberId);
            System.out.println("Member (" + memberId + ") will be deleted 3 years from now");
        }, delay, TimeUnit.DAYS);

        executorService.shutdown();
    }

    public MemberDTO findPasswordByMemberId(int currentMemberId) {
        Member getMemberDetails = getSpecificMember(currentMemberId);
        MemberDTO getMemberDetailsInDTO = modelMapper.map(getMemberDetails, MemberDTO.class);
        System.out.println("구성원의 정보: " + getMemberDetailsInDTO);
//        memberRepository.findAll();

        return getMemberDetailsInDTO;
    }

    @Transactional
    public String changePassword(String newPassword2, int currentMemberId) {
        int result = 0;

        try {
            Member getMemberDetails = getSpecificMember(currentMemberId);
            getMemberDetails.setPassword(newPassword2);
            Member finalDetails = memberRepository.save(getMemberDetails);
            System.out.println("바뀐 비밀번호: " + finalDetails);
            result = 1;
        } catch (Exception e) {
            result = 0;
        }
        return (result > 0) ? "성공" : "failed to change password";
    }

    public List<MemberDTO> showAllMembers() {
        System.out.println("all members: " + memberRepository.findAll());
        List<Member> memberLists = memberRepository.findAll();
        System.out.println("memberLists service: " + memberLists);
        Type listType = new TypeToken<List<MemberDTO>>() {}.getType();
        List<MemberDTO> memberDTOList = modelMapper.map(memberLists, listType);

        return memberDTOList;
    }

    public int findNoOfMembers(int departNo) {
        List<Member> members = memberRepository.findByDepartment_DepartNo(departNo);
        return members.size();
    }


    @Transactional
    public void resetPassword(MemberDTO memberInfo) {
        Member resetPasswordMember = modelMapper.map(memberInfo, Member.class);
        memberRepository.save(resetPasswordMember);

        System.out.println("비밀번호 초기화 성공!");
    }

    @Transactional
    public void deleteMemberById(int memberId) {
        memberRepository.deleteById(memberId);
    }

    public List<Member> downloadAllMembers() {
        List<Member> downloadMemberList = memberRepository.findAll();
        return downloadMemberList;
    }
}