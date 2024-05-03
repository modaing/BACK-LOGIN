package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {


    @PersistenceContext
    private EntityManager manager;

    public Member findById(int memberId) {

        return manager.find(Member.class, memberId);
    }
}
