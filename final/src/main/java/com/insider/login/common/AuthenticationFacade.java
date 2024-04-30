package com.insider.login.common;

import com.insider.login.auth.DetailsMember;
import com.insider.login.member.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public Member getLoggedInMember() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DetailsMember) {
            return ((DetailsMember) authentication.getPrincipal()).getMember();
        }
        return null;
    }
}
