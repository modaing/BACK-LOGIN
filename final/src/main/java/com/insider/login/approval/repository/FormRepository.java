package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Form;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class FormRepository {

    @PersistenceContext
    private EntityManager manager;

    public Form findById(String formNo) {
        return manager.find(Form.class, formNo);
    }
}
