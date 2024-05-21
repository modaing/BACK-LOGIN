package com.insider.login.insite.repository;

import com.insider.login.insite.entity.InsiteMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  InsiteRepository extends JpaRepository<InsiteMember, Integer> {

    @Query("SELECT m.department.departName, COUNT(m.memberId) FROM InsiteMember m GROUP BY m.department.departName")
    List<Object[]> selectDepartmentMemberCounts();


}
