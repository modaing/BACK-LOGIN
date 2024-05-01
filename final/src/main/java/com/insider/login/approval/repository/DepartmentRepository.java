package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepository {

    @PersistenceContext
    private EntityManager manager;


    public Department findById(int departNo) {
        return manager.find(Department.class, departNo);
    }
}
