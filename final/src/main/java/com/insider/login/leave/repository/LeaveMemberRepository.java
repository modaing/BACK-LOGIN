package com.insider.login.leave.repository;

import com.insider.login.leave.entity.LeaveMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveMemberRepository extends JpaRepository<LeaveMember, Integer> {

    @Query("SELECT m.name FROM LeaveMember m WHERE m.memberId = :memberId")
    String findNameByMemberId(@Param("memberId") int memberId);

}