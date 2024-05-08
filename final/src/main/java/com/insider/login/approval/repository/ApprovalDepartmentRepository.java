package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApprovalDepartmentRepository {

    @PersistenceContext
    private EntityManager manager;


    public Department findById(int departNo) {
        return manager.find(Department.class, departNo);
    }

    public Department findByName(String departName){
        return manager.find(Department.class, departName);
    }

    public List<Department> findAll() {
        String query = "SELECT depart_no, depart_name" +
                " FROM department_info" +
                " ORDER BY DEPART_NO ASC";

        List<Department> deparmentList = manager.createNativeQuery(query, Department.class)
                .getResultList();

        return deparmentList;

    }
}
