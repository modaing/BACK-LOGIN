package com.insider.login.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.insider.login.auth.DetailsMember;
import com.insider.login.common.utils.TokenUtils;
import com.insider.login.department.dto.DepartmentDTO;
import com.insider.login.department.service.DepartmentService;
import com.insider.login.member.dto.MemberDTO;
import com.insider.login.member.dto.UpdatePasswordRequestDTO;
import com.insider.login.member.entity.Member;
import com.insider.login.member.service.MemberService;
import com.insider.login.position.dto.PositionDTO;
import com.insider.login.position.service.PositionService;
import com.insider.login.transferredHistory.service.TransferredHistoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.insider.login.common.utils.TokenUtils.getTokenInfo;

@RestController
@RequestMapping
public class MemberController {

    @Value("${jwt.key}")
    private String jwtSecret;
    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TransferredHistoryService transferredHistoryService;
    private final PositionService positionService;
    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoders;

    public MemberController(MemberService memberService, BCryptPasswordEncoder passwordEncoder, TransferredHistoryService transferredHistoryService, PositionService positionService, DepartmentService departmentService, ModelMapper modelMapper, PasswordEncoder passwordEncoders) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.transferredHistoryService = transferredHistoryService;
        this.positionService = positionService;
        this.departmentService = departmentService;
        this.modelMapper = modelMapper;
        this.passwordEncoders = passwordEncoders;
    }

    /** 구성원 등록 */
    @PostMapping("/signUp")
    public String signUp(@RequestBody MemberDTO memberDTO) {

        // 2자릿수 년도
        String twoDigitYear = Year.now().format(DateTimeFormatter.ofPattern("yy"));
        System.out.println("2자릿수 년도: " + twoDigitYear);

        // 2자릿수 월
        String twoDigitMonth = String.format("%02d", YearMonth.now().getMonthValue());
        System.out.println("2자릿수 월: " + twoDigitMonth);

        // 2자릿수 부서번호
        String departNo = String.valueOf(memberDTO.getDepartmentDTO().getDepartNo());
        if (departNo.length() == 1) {
            departNo = "0" + departNo;
        }

        // unique memberId 생성 (중복 X)
        int setMemberId;
        boolean existingId;
        do {
            Random random = new Random();
            int randomNumber = random.nextInt(900) + 100;
            setMemberId = Integer.parseInt(twoDigitYear + twoDigitMonth + departNo + randomNumber);

            existingId = memberService.findExistingMemberId(setMemberId);
        } while (existingId);

        memberDTO.setMemberId(setMemberId);
        memberDTO.setPassword("0000");
        String encodedPassword = passwordEncoder.encode(memberDTO.getPassword());
        memberDTO.setPassword(encodedPassword);
        memberDTO.setMemberStatus("재직");

        // JSON형식으로 LocalDate을 저장을 하기 위한 logic
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        LocalDate localDate = LocalDate.now();
        memberDTO.setEmployedDate(localDate);               // 입사일

        Member savedMember = memberService.saveMember(memberDTO);
        System.out.println("회원 가입한 구성원 정보: " + savedMember);

        // 회원가입을 하면 최초로 구성원의 인사발령 내역을 저장을 해야하기 때문에 작성하는 코드
        transferredHistoryService.saveHistory(savedMember);

        if(Objects.isNull(savedMember)) { // 비어있으면 실패
            System.out.println("회원가입 실패 🥲");
            return "회원가입 실패";
        } else {                    // 다 작성을 했으면 구성원 가입 성공
            System.out.println("회원가입 성공 🙂");
            return "회원 가입 성공!";
        }
    }


    /** 특정 구성원 정보 조회 */
    @GetMapping("/members/{memberId}")
    public String getSpecificMemberById(@PathVariable("memberId") int memberId) {
        System.out.println("받은 memberId: " + memberId);
        MemberDTO foundMember = memberService.findSpecificMember(memberId);
        System.out.println("특정 구성원 정보 조회: " + foundMember);

        if (foundMember != null) {
            return "foundMember: " + foundMember;
        } else {
            return "memberNotFound";
        }
    }

    /** 구성원 정보 등록 */
    @PutMapping("/members/updateProfile/{memberId}")
    public String updateSpecificMemberById(@PathVariable("memberId") int memberId, @RequestBody MemberDTO inputtedMemberInfo) {
        /* 특정 구성원의 정보를 전부 가져온다 */
        MemberDTO specificMember = memberService.findSpecificMember(memberId);
        System.out.println("specificMember: " + specificMember); // 확인용

        inputtedMemberInfo.setMemberId(memberId);
        inputtedMemberInfo.setPassword(specificMember.getPassword());
        inputtedMemberInfo.setEmployedDate(specificMember.getEmployedDate());
        inputtedMemberInfo.setAddress(specificMember.getAddress());
        inputtedMemberInfo.setRole(specificMember.getRole());
        inputtedMemberInfo.setImageUrl(specificMember.getImageUrl());
        System.out.println("수정을 하기전 구성원의 정보: " + specificMember);
        System.out.println("입력 받은 값: " + inputtedMemberInfo);

        /* 입력 받은 것을 덮어 쓴다 */
        String updatedMember = memberService.updateMember(inputtedMemberInfo);

        return "찾은 구성원의 정보: " + updatedMember;
    }

    /** 구성원 본인 비밀번호 */
    @PostMapping("/updateOwnPassword")
    public String updateOwnPassword(@RequestBody UpdatePasswordRequestDTO updatePasswordRequestDTO) {

        MemberDTO foundMember = memberService.findPasswordByMemberId(getTokenInfo().getMemberId());
        String existingPassword = foundMember.getPassword();
        System.out.println("기존에 있는 비밀번호: " + existingPassword);

        /* 입력한 현재 비밀번호가 일치하는지 확인하는 logic */
        if (!passwordEncoder.matches(updatePasswordRequestDTO.getCurrentPassword(), existingPassword)) {
            System.out.println("비밀번호가 틀렸습니다");
            return "wrong password";
        } else if (!updatePasswordRequestDTO.getNewPassword1().equals(updatePasswordRequestDTO.getNewPassword2())) {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return "password doesn't match";
        } else {
            String hashedNewPassword = passwordEncoder.encode(updatePasswordRequestDTO.getNewPassword2());
            String result = memberService.changePassword(hashedNewPassword, getTokenInfo123().getMemberId());
            return "successfully changed the password" + result;
        }
    }

    /** Claims에 구성원 정보를 받아오는 방법 */
    /**
     * getTokenInfo()에서 받아올 수 있는 정보들
     * 1. memberID -> getMemberId
     * 2. name -> getName
     * 3. memberStatus -> getMemberStatus
     * 4. role -> getRole.getRole()
     * 5. departmentName ->getDepartmentDTO.getDepartName
     * 6. positionName ->getPositionDTO.getPositionName
     * 6. imageUrl -> getImageUrl
     * */
    @GetMapping("/getTokenInfo")
    public MemberDTO getTokenInfo123() {
        System.out.println("회원의 비밀번호: " + getTokenInfo().getMemberId());
        System.out.println("memberId: " + getTokenInfo().getMemberId());
        System.out.println("name: " + getTokenInfo().getName());
        System.out.println("member status: " + getTokenInfo().getMemberStatus());
        System.out.println("role: " + getTokenInfo().getRole().getRole());
        System.out.println("positionName: " + getTokenInfo().getDepartmentDTO().getDepartName());
        System.out.println("department name: " + getTokenInfo().getPositionDTO().getPositionName());
        System.out.println("image url: " + getTokenInfo().getImageUrl());
        return getTokenInfo();
    }

    @GetMapping("/showAllMembersInfoPage")
    public List<MemberDTO> showAllMemberInfosInPage() {
        System.out.println("show all member infos in the page");
        List<MemberDTO> memberLists = memberService.showAllMembers();
        System.out.println("memberList: " + memberLists);

        for (MemberDTO member : memberLists) {
            member.getName();
            member.getMemberId();
            member.getEmployedDate();
            member.getPositionDTO().getPositionName();
            member.getDepartmentDTO().getDepartName();
            member.getMemberStatus();
        }

        // 근속년수 작성할 것
        return memberLists;
    }

    @PutMapping("/resetMemberPassword")
    public String resetMemberPassword() {
        MemberDTO memberInfo = memberService.findSpecificMember(getTokenInfo().getMemberId());
        memberInfo.setPassword("0000");

        memberInfo.setPassword("0000");
        memberInfo.setPassword(passwordEncoder.encode(memberInfo.getPassword()));
        memberService.resetPassword(memberInfo);

        /* 아직 ing */
        return null;
    }
}