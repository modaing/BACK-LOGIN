package com.insider.login.approval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "Form")
@Table(name="form")
public class Form {

    @Id
    @Column(name = "form_no")
    private String formNo;

    @Column(name = "form_shape")
    private String formShape;

    protected Form() {}

    public Form(String formNo, String formShape) {
        this.formNo = formNo;
        this.formShape = formShape;
    }

    public String getFormNo() {
        return formNo;
    }

    public String getFormShape() {
        return formShape;
    }

    @Override
    public String toString() {
        return "Form{" +
                "formNo='" + formNo + '\'' +
                ", formShape='" + formShape + '\'' +
                '}';
    }
}
