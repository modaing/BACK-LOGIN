package com.insider.login.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.insider.login.auth.DetailsMember;
import com.insider.login.auth.image.entity.Image;
import com.insider.login.auth.image.service.ImageService;
import com.insider.login.auth.interceptor.JwtTokenInterceptor;
import com.insider.login.auth.model.dto.LoginDTO;
import com.insider.login.common.AuthConstants;
import com.insider.login.common.AuthenticationFacade;
import com.insider.login.common.utils.TokenUtils;
import com.insider.login.department.dto.DepartmentDTO;
import com.insider.login.member.dto.MemberDTO;
import com.insider.login.member.entity.Member;
import com.insider.login.member.repository.MemberRepository;
import com.insider.login.member.service.MemberService;
import com.insider.login.position.dto.PositionDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@RestController
public class MemberController {

    @Value("${jwt.key}")
    private String jwtSecret;
    private final MemberRepository memberRepository; // 원래 service에 작성을 하는 것인데 test를 하기 위해서 임시로 사용 하는 것...!
    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public MemberController(MemberRepository memberRepository, MemberService memberService, BCryptPasswordEncoder passwordEncoder, ImageService imageService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    @PostMapping("/regist")
    public String signup(@RequestBody MemberDTO memberDTO) {

        // 2자릿수 년도
        String twoDigitYear = Year.now().format(DateTimeFormatter.ofPattern("yy"));
        System.out.println("2자릿수 년도: " + twoDigitYear);

        // 2자릿수 월
        String twoDigitMonth = String.format("%02d", YearMonth.now().getMonthValue());
        System.out.println("2자릿수 월: " + twoDigitMonth);

        // 2자릿수 부서번호
        String departNo = String.valueOf(memberDTO.getDepartment().getDepartNo());

        if (departNo.length() == 1) {
            departNo = "0" + departNo;
        }

        // Random 번호 (뒷 3자리)
        Random random = new Random();
        int randomNumber = random.nextInt(900) + 100;
        int setMemberId = Integer.parseInt(twoDigitYear + twoDigitMonth + departNo + randomNumber);

        // 기본적인 정보들을 member에다가 값들을 setting 해준다
        memberDTO.setMemberId(setMemberId); // 사번 생성
        memberDTO.setPassword("0000");      // 수동적으로 비밀번호는 0000이다
        String encodedPassword = passwordEncoder.encode(memberDTO.getPassword());
        memberDTO.setPassword(encodedPassword);
        memberDTO.setMemberStatus("재직");   // 처음 등록을 할 때 "재직" 상태로 설정 하는 logic


        // JSON형식으로 LocalDate을 저장을 하기 위한 block of code
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        LocalDate localDate = LocalDate.now();

        memberDTO.setEmployedDate(localDate); // 등록한 날짜 가입

        imageService.saveImage(memberDTO.getImage());
        MemberDTO savedMember = memberService.saveMember(memberDTO);

        System.out.println("회원 가입한 구성원 정보: " + savedMember);

        if(Objects.isNull(savedMember)) { // 비어있으면 실패
            System.out.println("회원가입 실패 🥲");
            return "회원가입 실패";
        } else {                    // 다 작성을 했으면 구성원 가입 성공
            System.out.println("회원가입 성공 🙂");
            return "회원 가입 성공!";
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        System.out.println("login controller 도착");
        int memberIdInfo = loginDTO.getId();
        String memberPasswordInfo = loginDTO.getPass();

        System.out.println("아이디 정보: " + memberIdInfo);
        System.out.println("비밀번호 정보: " + memberPasswordInfo);

        return null;
    }

    @PostMapping("/registDepart")
    public String registDepartment(@RequestBody DepartmentDTO departmentDTO) {
        memberService.insertDepart(departmentDTO);

        return "registered Department";
    }

    @PostMapping("/registPosition")
    public String registPosition(@RequestBody PositionDTO positionDTO) {
        memberService.insertPosition(positionDTO);

        return "registered position";
    }


    /* 1번째 방법 */
    @GetMapping("/getMemberInfo")
    public String getMemberInfo(@RequestHeader(AuthConstants.AUTH_HEADER) String token) {
        System.out.println("token: " + token); // 확인용
        String jwtToken = token.substring(7);
        Claims claims = TokenUtils.getClaimsFromToken(jwtToken);
        Object memberIdObject = claims.get("memberId");

        // memberId가 존재하고 integer일 경우에는
        if (memberIdObject != null && memberIdObject instanceof Integer) {
            int memberId = (Integer) memberIdObject;
            System.out.println("memberId: " + memberId);
            return "memberID: " + memberId;
        } else {
            return "MemberId not found in token";
        }
    }

    /* 2번째 방법 */
    @GetMapping("/getToken")
    public String tokenInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DetailsMember detailsMember = (DetailsMember) authentication.getPrincipal();
        Member member = detailsMember.getMember();
        System.out.println("member 정보들: " + member);
        return ("member의 정보들: " + member);
//        return ("token 정보: " + token);
    }

    // 본인 비밀번호 변경
    @PostMapping("/updatePassword")
    public String updateOwnPassword(@RequestHeader(AuthConstants.AUTH_HEADER) String token,
                                    @RequestBody Map<String, String> passwords) {
        String jwtToken = token.substring(7);
        Claims claims = TokenUtils.getClaimsFromToken(jwtToken);
        Object memberIdObject = claims.get("memberId");
        int memberId = (Integer) memberIdObject;

        // 현재 인증된 구성원의 정보들
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String memberName = authentication.getName();
            System.out.println("구성원 이름: " + memberName);

            String currentPassword = passwords.get("currentPassword");
            String newPassword1 = passwords.get("newPassword123");
            String newPassword2 = passwords.get("newPassword123123");
        }
        return null;
    }
}
