package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Form;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ApprovalRepository {

    @PersistenceContext
    private EntityManager manager;

    public void insertForm(Form newForm) {
        manager.persist(newForm);
    }
}

