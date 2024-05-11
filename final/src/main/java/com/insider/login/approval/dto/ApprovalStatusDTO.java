package com.insider.login.approval.dto;

import java.util.List;

public class ApprovalStatusDTO {

    // 결재번호, 기안자, 결재자 결재상태, 반려사유
    private String approvalNo;                  //결재번호
    private int memberId;                       //기안자사번
    private String approvalTitle;               //제목
    private String approvalContent;             //기안 내용
    private String approvalDate;                //작성일시
    private String approvalStatus;              //상태
    private String rejectReason;                //반려사유
    private String formNo;                      //양식번호
    private List<AttachmentDTO> attachment;     //첨부파일
    private List<ApproverDTO> approver;         //결재선
    private String senderDepartName;            //(*depart_info) 기안자 부서
    private String senderPositionName;          //(*member_info) 기안자 직급
    private String receiverDepartName;          //(*depart_info) 결재자 부서
    private String receiverPositionName;        //(*member_infO) 결재자 직급


    public ApprovalStatusDTO() {
    }

    public ApprovalStatusDTO(String approvalNo, int memberId, String approvalTitle, String approvalContent, String approvalDate, String approvalStatus, String rejectReason, String formNo, List<AttachmentDTO> attachment, List<ApproverDTO> approver, String senderDepartName, String senderPositionName, String receiverPositionName, String receiverDepartName) {
        this.approvalNo = approvalNo;
        this.memberId = memberId;
        this.approvalTitle = approvalTitle;
        this.approvalContent = approvalContent;
        this.approvalDate = approvalDate;
        this.approvalStatus = approvalStatus;
        this.rejectReason = rejectReason;
        this.formNo = formNo;
        this.attachment = attachment;
        this.approver = approver;
        this.senderDepartName = senderDepartName;
        this.senderPositionName = senderPositionName;
        this.receiverPositionName = receiverPositionName;
        this.receiverDepartName = receiverDepartName;
    }

    public String getApprovalNo() {
        return approvalNo;
    }

    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getApprovalTitle() {
        return approvalTitle;
    }

    public void setApprovalTitle(String approvalTitle) {
        this.approvalTitle = approvalTitle;
    }

    public String getApprovalContent() {
        return approvalContent;
    }

    public void setApprovalContent(String approvalContent) {
        this.approvalContent = approvalContent;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public List<AttachmentDTO> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<AttachmentDTO> attachment) {
        this.attachment = attachment;
    }

    public List<ApproverDTO> getApprover() {
        return approver;
    }

    public void setApprover(List<ApproverDTO> approver) {
        this.approver = approver;
    }

    public String getSenderDepartName() {
        return senderDepartName;
    }

    public void setSenderDepartName(String senderDepartName) {
        this.senderDepartName = senderDepartName;
    }

    public String getSenderPositionName() {
        return senderPositionName;
    }

    public void setSenderPositionName(String senderPositionName) {
        this.senderPositionName = senderPositionName;
    }

    public String getReceiverPositionName() {
        return receiverPositionName;
    }

    public void setReceiverPositionName(String receiverPositionName) {
        this.receiverPositionName = receiverPositionName;
    }

    public String getReceiverDepartName() {
        return receiverDepartName;
    }

    public void setReceiverDepartName(String receiverDepartName) {
        this.receiverDepartName = receiverDepartName;
    }

    @Override
    public String toString() {
        return "ApprovalDetailDTO{" +
                "approvalNo='" + approvalNo + '\'' +
                ", memberId=" + memberId +
                ", approvalTitle='" + approvalTitle + '\'' +
                ", approvalContent='" + approvalContent + '\'' +
                ", approvalDate='" + approvalDate + '\'' +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", rejectReason='" + rejectReason + '\'' +
                ", formNo='" + formNo + '\'' +
                ", attachment=" + attachment +
                ", approver=" + approver +
                ", departName='" + senderDepartName + '\'' +
                ", senderPositionName='" + senderPositionName + '\'' +
                ", receiverPositionName='" + receiverPositionName + '\'' +
                ", receiverDepartName='" + receiverDepartName + '\'' +
                '}';
    }
}
