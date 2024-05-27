package com.insider.login.member.service;

import com.insider.login.auth.model.dto.LoginDTO;
import com.insider.login.department.dto.DepartmentDTO;
import com.insider.login.department.repository.DepartmentRepository;
import com.insider.login.member.dto.MemberDTO;
import com.insider.login.member.entity.Member;
import com.insider.login.member.repository.MemberRepository;
import com.insider.login.position.dto.PositionDTO;
import com.insider.login.transferredHistory.dto.TransferredHistoryDTO;
import com.insider.login.transferredHistory.entity.TransferredHistory;
import com.insider.login.transferredHistory.repository.TransferredHistoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberService(MemberRepository userRepository, ModelMapper modelMapper, DepartmentRepository departmentRepository, TransferredHistoryRepository transferredHistoryRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = userRepository;
        this.modelMapper = modelMapper;
        this.departmentRepository = departmentRepository;
        this.transferredHistoryRepository = transferredHistoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<MemberDTO> findMember(int id) {
        Optional<Member> memberOptional = memberRepository.findById(id);

        if (memberOptional.isPresent()) { // 존재한다면
            Member member = memberOptional.get();
            System.out.println("찾은 member는: " + member);
            MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
            memberDTO.setDepartmentDTO(DepartmentDTO.mapToDTO(member.getDepartment()));
            memberDTO.setPositionDTO(PositionDTO.mapToDTO(member.getPosition()));
            return Optional.of(memberDTO);
        } else {
            System.out.println("Member with " + id + " is not valid");
            return Optional.empty();
        }
    }

    /* 구성원 등록 */
    @Transactional
    public Member saveMember(MemberDTO memberDTO) {
        Member newMember = modelMapper.map(memberDTO, Member.class);
        System.out.println("new member: " + newMember);
//        Department department = modelMapper.map(memberDTO.getDepartmentDTO(), Department.class);
//        Position position = modelMapper.map(memberDTO.getPositionDTO(), Position.class);
//        newMember.setDepartment(department);
//        newMember.setPosition(position);
        Member savedMember = memberRepository.save(newMember);
        System.out.println("구성원 등록 성공: " + savedMember);
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
        System.out.println("position name of 기존 구성원: " + checkPositionOrDepartment.getPositionDTO().getPositionName());
        System.out.println("department name of 기존 구성원: " + checkPositionOrDepartment.getDepartmentDTO().getDepartName());

        System.out.println("position name of specificMember: " + specificMember.getPositionDTO().getPositionName());
        System.out.println("department name of specificMember: " + specificMember.getDepartmentDTO().getDepartName());
        int result = 0;

        try {
            Member updatedMember = modelMapper.map(specificMember, Member.class);
            System.out.println("updatedMember: " + updatedMember);
            if (specificMember.getMemberStatus().equals("퇴직")) {
                scheduleMemberDeletion(updatedMember.getMemberId());
                System.out.println("3년뒤에 구성원 정보가 탈퇴됩니다");
            }

            /* 부서와 직급이 다를 때 */
            if (!specificMember.getDepartmentDTO().getDepartName().equals(checkPositionOrDepartment.getDepartmentDTO().getDepartName()) || !specificMember.getPositionDTO().getPositionName().equals(checkPositionOrDepartment.getPositionDTO().getPositionName())) {
                TransferredHistoryDTO transferredHistoryDTO = new TransferredHistoryDTO();
                transferredHistoryDTO.setNewDepartNo(updatedMember.getDepartment().getDepartNo());
                transferredHistoryDTO.setNewPositionName(updatedMember.getPosition().getPositionName());
                transferredHistoryDTO.setMemberId(updatedMember.getMemberId());
                transferredHistoryDTO.setTransferredDate(LocalDate.now());
                TransferredHistory updatedTransferredHistoryRecord = modelMapper.map(transferredHistoryDTO, TransferredHistory.class);
                transferredHistoryRepository.save(updatedTransferredHistoryRecord);

                /* update를 하기 위해서 구성원의 정보를 가져와서 setting하는 logic */
                Member memberDetails = memberRepository.findById(specificMember.getMemberId()).orElse(null);
                MemberDTO originDetails = modelMapper.map(memberDetails, MemberDTO.class);
                modelMapper.map(updatedMember, originDetails);
                System.out.println("origin details: " + originDetails);
                System.out.println("details to be updatted: " + updatedMember);
//                detailsToChange.setDepartmentDTO(specificMember.getDepartmentDTO());
//                detailsToChange.setPositionDTO(specificMember.getPositionDTO());
                Member detailsToSave = modelMapper.map(updatedMember, Member.class);
                System.out.println("😭😭😭😭😭😭");
                System.out.println("updatedMember: " + updatedMember);
                System.out.println("detailsToChange information: " + updatedMember);
                memberRepository.save(detailsToSave);
                //                memberDetails.setDepartment(specificMember.getDepartmentDTO());
                System.out.println("transferred history saved and changed department or position data");
            } else {
                Member savedInfo = memberRepository.save(updatedMember);
                System.out.println("바뀐 정보들: " + savedInfo);
            }

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

    public int findNoOfMembersInDepart(int departNo) {
        List<Member> members = memberRepository.findByDepartment_DepartNo(departNo);
        return members.size();
    }

    public int findNoOfMembersInPosition(String positionLevel) {
        List<Member> members = memberRepository.findByPosition_PositionLevel(positionLevel);
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

    public List<MemberDTO> selectMemberList() {

        List<Member> member = memberRepository.findAll();

        List<MemberDTO> memberList = new ArrayList<>();

        for (Member members : member) {
            MemberDTO memberDTO = modelMapper.map(members, MemberDTO.class);
            memberList.add(memberDTO);
        }

        return memberList;
    }

    public void loggedInMember(MemberDTO memberDTO) {
        System.out.println("memberService 도착");
        Member loggedInMember = modelMapper.map(memberDTO, Member.class);

        Optional<Member> optimalMember = memberRepository.findById(loggedInMember.getMemberId());

        if (optimalMember.isPresent()) {
            Member member = optimalMember.get();
            if (passwordEncoder.matches(memberDTO.getPassword(), member.getPassword())) {
                System.out.println("login successfull");
            } else {
                System.out.println("incorrect password");
            }
        } else {
            System.out.println("member not found");
        }
    }

    public MemberDTO checkLoggedMemberInfo(int memberId) {
        System.out.println("checkedLoggedMemberInfo 도착");
        Member loggedInMember = memberRepository.findById(memberId).orElse(null);
        if (loggedInMember != null) {
            return modelMapper.map(loggedInMember, MemberDTO.class);
        } else {
            return null;
        }
    }

    public MemberDTO checkLoggedInfo(LoginDTO loginDTO) {
        System.out.println("checkedLoggedInfo: " + loginDTO);
        Member foundMember = memberRepository.findById(loginDTO.getMemberId()).orElse(null);
        if (foundMember != null) {
            return modelMapper.map(foundMember, MemberDTO.class);
        } else {
            return null;
        }

    }

    public MemberDTO getProfilePicture(int memberId) {
        Member memberDetail = memberRepository.findById(memberId).orElse(null);
        MemberDTO currentMember = modelMapper.map(memberDetail, MemberDTO.class);
        return currentMember;
    }

//    public List<MemberDTO> findMemberList(String findByDepartNo) {
//        MemberDTO memberDTO = new MemberDTO();
//        memberDTO.setDepartmentDTO();
//        memberRepository.findBy()
//    }
}