package com.insider.login.commute.repository;

import com.insider.login.commute.entity.CommuteDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface CommuteDepartmentRepository extends JpaRepository<CommuteDepartment, Integer> {
    CommuteDepartment findByDepartNo(int departNo);

    List<CommuteDepartment> findByDepartNoIn(Set<Integer> departNoSet);
}
