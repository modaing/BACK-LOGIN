package com.insider.login.approval.service;

import com.insider.login.approval.entity.Form;
import com.insider.login.approval.repository.ApprovalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovalService {

    @Autowired
    private ApprovalRepository approvalRepository;

    public ApprovalService(ApprovalRepository approvalRepository){
        this.approvalRepository = approvalRepository;
    }

    @Transactional
    public void insertForm(Form newForm) {

        approvalRepository.insertForm(newForm);

    }
}
