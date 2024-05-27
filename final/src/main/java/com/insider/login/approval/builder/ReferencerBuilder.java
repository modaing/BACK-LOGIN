package com.insider.login.approval.builder;

import com.insider.login.approval.entity.Referencer;

public class ReferencerBuilder {

    private String refNo;           // 참조자 번호

    private String approvalNo;      // 결재 번호

    private int memberId;           // 참조자 사번

    private int refOrder;           // 참조 순번


    public ReferencerBuilder() {}
    public ReferencerBuilder(String refNo) {
        this.refNo = refNo;
    }

    public ReferencerBuilder(Referencer referencer){
        this.refNo = referencer.getRefNo();
        this.approvalNo = referencer.getApprovalNo();
        this.memberId = referencer.getMemberId();
        this.refOrder = referencer.getRefOrder();

    }

    public ReferencerBuilder refNo(String val){
        this.refNo = val;
        return this;
    }

    public ReferencerBuilder approvalNo(String val){
        this.approvalNo = val;
        return this;
    }

    public ReferencerBuilder memberId(int val){
        this.memberId = val;
        return this;
    }

    public ReferencerBuilder refOrder(int val){
        this.refOrder = val;
        return this;
    }

    public Referencer builder(){
        return new Referencer(refNo, approvalNo, memberId, refOrder);
    }
}
