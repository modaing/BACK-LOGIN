package com.insider.login.member.service;

import com.insider.login.department.dto.DepartmentDTO;
import com.insider.login.department.entity.Department;
import com.insider.login.department.repository.DepartmentRepository;
import com.insider.login.member.dto.MemberDTO;
import com.insider.login.member.entity.Member;
import com.insider.login.member.repository.MemberRepository;
import com.insider.login.position.dto.PositionDTO;
import com.insider.login.position.entity.Position;
import io.micrometer.observation.ObservationFilter;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;

    public MemberService(MemberRepository userRepository, ModelMapper modelMapper, DepartmentRepository departmentRepository) {
        this.memberRepository = userRepository;
        this.modelMapper = modelMapper;
        this.departmentRepository = departmentRepository;
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
        int result = 0;

        try {
            System.out.println("specificMember setDepartment: " + specificMember);
            Member updatedMember = modelMapper.map(specificMember, Member.class);
            memberRepository.save(updatedMember);
            result = 1;
        } catch (Exception e) {
            result = 0;
        }
        return (result > 0) ? "성공" : "실패";
    }

    public String checkCurrentPassword(String currentPassword) {
        return null;
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


    public void resetPassword(MemberDTO memberInfo) {
        Member resetPasswordMember = modelMapper.map(memberInfo, Member.class);
        memberRepository.save(resetPasswordMember);

        System.out.println("비밀번호 초기화 성공!");
    }
}
