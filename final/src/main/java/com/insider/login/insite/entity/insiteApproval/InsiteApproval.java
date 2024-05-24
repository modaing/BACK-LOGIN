package com.insider.login.insite.entity.insiteApproval;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="InsiteApproval")
@Table(name="APPROVAL")
public class InsiteApproval {

        @Id
        @Column(name= "APPROVAL_NO")
        private String approvalNo;          //결재번호

        @Column(name="MEMBER_ID")
        private int memberId;               //기안자사번

        @Column(name="APPROVAL_TITLE")
        private String approvalTitle;       //제목

        @Column(name="APPROVAL_STATUS")
        private String approvalStatus;      //상태

        protected InsiteApproval(){}




}
