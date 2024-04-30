package com.insider.login.member.service;

import com.insider.login.common.AuthenticationFacade;
import com.insider.login.department.dto.DepartmentDTO;
import com.insider.login.department.entity.Department;
import com.insider.login.department.repository.DepartmentRepository;
import com.insider.login.member.dto.MemberDTO;
import com.insider.login.member.entity.Member;
import com.insider.login.member.repository.MemberRepository;
import com.insider.login.position.dto.PositionDTO;
import com.insider.login.position.entity.Position;
import com.insider.login.position.repository.PositionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.Optional;

@Service
public class MemberService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final AuthenticationFacade authenticationFacade;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;

    public MemberService(MemberRepository userRepository, AuthenticationFacade authenticationFacade, ModelMapper modelMapper, DepartmentRepository departmentRepository, PositionRepository positionRepository) {
        this.memberRepository = userRepository;
        this.authenticationFacade = authenticationFacade;
        this.modelMapper = modelMapper;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
    }

    public Optional<Member> findMember(int id) {
        Optional<Member> member = memberRepository.findByMemberId(id);
        System.out.println("찾은 member는: " + member);

        /*
        * 별도의 검증 로직 작성
        * 예) DB 문제로 구성원의 정보를 받지 못했다거나
        *    또는 여러 구성원들의 정보들을 반환을 했다거나...
        * 추가적인 로직을 작성을 해야한다.... no...
        * */
        return member;
    }

    /* 구성원 등록 */
    @Transactional
    public MemberDTO saveMember(MemberDTO memberDTO) {
        Member newMember = modelMapper.map(memberDTO, Member.class);
        Member savedMember = memberRepository.save(newMember);
        return modelMapper.map(savedMember, MemberDTO.class);
    }

    @Transactional
    public void insertDepart(DepartmentDTO departmentDTO) {
        Department department = modelMapper.map(departmentDTO, Department.class);

        departmentRepository.save(department);
    }

    public void insertPosition(PositionDTO positionDTO) {
        Position position = modelMapper.map(positionDTO, Position.class);

        positionRepository.save(position);
    }
}
