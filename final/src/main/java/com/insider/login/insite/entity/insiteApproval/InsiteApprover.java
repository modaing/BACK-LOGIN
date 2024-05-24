package com.insider.login.insite.entity.insiteApproval;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;


@Entity(name = "InsiteApprover")
@Table(name="APPROVER")
public class InsiteApprover {

    @Id
    @Column(name = "APPROVER_NO")
    private String approverNo;          // 결재자 번호

    @Column(name = "APPROVAL_NO")
    private String approvalNo;          // 결재 번호

    @Column(name = "APPROVER_STATUS")
    private String approverStatus;      //결재 처리 상태

    @Column(name = "MEMBER_ID")
    private int memberId;               // 결재자 사번

}