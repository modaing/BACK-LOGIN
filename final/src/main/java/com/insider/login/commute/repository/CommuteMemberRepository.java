package com.insider.login.commute.repository;

import com.insider.login.commute.entity.CommuteMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface CommuteMemberRepository extends JpaRepository<CommuteMember, Integer> {
    List<CommuteMember> findByMemberRole(String admin);

    CommuteMember findByMemberId(int memberId);

    List<CommuteMember> findByMemberIdIn(Set<Integer> memberIds);
}
