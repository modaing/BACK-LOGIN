package com.insider.login.approval.builder;

import com.insider.login.approval.entity.Attachment;

public class AttachmentBuilder {

    private String fileNo;
    private String fileOriname;
    private String fileSavepath;
    private String fileSavename;
    private String approvalNo;

    public AttachmentBuilder() {}

    public AttachmentBuilder(String fileNo){
        this.fileNo = fileNo;
    }

    public AttachmentBuilder(Attachment attachment){
        this.fileNo = attachment.getFileNo();
        this.fileOriname = attachment.getFileOriname();
        this.fileSavepath = attachment.getFileSavepath();
        this.fileSavename = attachment.getFileSavename();
        this.approvalNo = attachment.getApprovalNo();
    }

    public AttachmentBuilder fileNo(String val){
        this.fileNo = val;
        return this;
    }

    public AttachmentBuilder fileOriname(String val){
        this.fileOriname = val;
        return this;
    }

    public AttachmentBuilder fileSavepath(String val){
        this.fileSavepath = val;
        return this;
    }

    public AttachmentBuilder fileSavename(String val){
        this.fileSavename = val;
        return this;
    }

    public AttachmentBuilder approvalNo(String val){
        this.approvalNo = val;
        return this;
    }

    public Attachment builder(){
        return new Attachment(fileNo, fileOriname, fileSavepath, fileSavename, approvalNo);
    }
}
