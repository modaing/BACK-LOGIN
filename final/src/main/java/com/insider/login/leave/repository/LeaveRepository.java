package com.insider.login.leave.repository;


import com.insider.login.leave.entity.Leaves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<Leaves, Integer> {
}
