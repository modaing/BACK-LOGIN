package com.insider.login.member.controller;

import com.insider.login.auth.filter.JwtAuthorizationFilter;
import com.insider.login.common.AuthConstants;
import com.insider.login.common.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@PreAuthorize("hasAuthority('ADMIN')") // ADMIN인 사용자만 접근 가능하다
public class TestController {
    @GetMapping("/test")
    public String test(HttpServletRequest request){
        System.out.println("===== test controller ===== 도착");
//        String token = request.getHeader(AuthConstants.AUTH_HEADER);
//        Claims claims = TokenUtils.getClaimsFromToken(token);
//        System.out.println("Claims from token: " + claims);
//        System.out.println("claims에 담은 memberId 정보: " + claims.get("memberId")); // 확인용
//        int getMemberIdFromClaims = claims.get("memberId");

        return "test GET";
    }

    @PostMapping("/test")
    public String test2(){
        return "test POST";
    }
}