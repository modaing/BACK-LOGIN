package com.insider.login.approval.repository;

import com.insider.login.approval.dto.MemberDTO;
import com.insider.login.approval.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalMemberRepository extends JpaRepository<Member, Integer> {


    public Member findById(int memberId);

    @Query("SELECT m FROM ApprovalMember m, Position p WHERE m.positionLevel = p.positionLevel AND m.departNo = :departNo ORDER BY p.positionLevel ASC")
    public List<Member> findByDepart(@Param("departNo") int departNo);

    @Query(value = "SELECT d.depart_name, m.name, m.member_id, m.password, m.depart_no, m.position_level, m.employed_date, m.address, m.phone_no, m.member_status, m.email, m.member_role, m.image_url " +
            "FROM member_info m " +
            "join department_info d ON d.depart_no = m.depart_no " +
            "order by d.depart_no, CAST(m.position_level AS UNSIGNED) DESC",
    nativeQuery = true)
    List<Object[]> findAllMembersWithDepartmentOrderedNative();
}
