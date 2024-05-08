package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApprovalMemberRepository {


    @PersistenceContext
    private EntityManager manager;

    public Member findById(int memberId) {

        return manager.find(Member.class, memberId);
    }

    public List<Member> findByDepart(int departNo) {

        String query = "SELECT m.name, m.member_id, m.password, m.depart_no, m.position_name, m.employed_date, m.address, m.phone_no, m.member_status, m.email, m.member_role, m.image_url" +
                " FROM member_info m" +
                " JOIN position_info p ON m.position_name = p.position_name" +
                " WHERE m.depart_no = ?" +
                " ORDER BY p.position_level ASC";

        List<Member> memberList = manager.createNativeQuery(query, Member.class)
                .setParameter(1, departNo)
                .getResultList();

        return memberList;
    }
}
