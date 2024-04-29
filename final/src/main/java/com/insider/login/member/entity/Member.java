package com.insider.login.member.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "member_info")
public class Member {                 // JPA를 사용을 할 것이기 때문에 entity 설계 필수

    @Id
    @Column(name = "member_id", nullable = false)
    private int memberId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "depart_no", nullable = false)
    private int departNo;
    @Column(name = "position_name" ,nullable = false)
    private String positionName;
    @Column(name = "employed_date", nullable = false)
    private LocalDate employedDate;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "phone_no", nullable = false)
    private String phoneNo;
//    @Column(name = "member_status", nullable = false)
//    private String memberStatus;
    @Column(name = "email", nullable = false)
    private String email;


    public Member() {

    }

    public int getMemberId() {
        return memberId;
    }

    public Member(int memberId, String name, String password, int departNo, String positionName, LocalDate employedDate, String address, String phoneNo, String memberStatus, String email ) {
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.departNo = departNo;
        this.positionName = positionName;
        this.employedDate = employedDate;
        this.address = address;
        this.phoneNo = phoneNo;
//        this.memberStatus = memberStatus;
        this.email = email;
    }


    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDepartNo() {
        return departNo;
    }

    public void setDepartNo(int departNo) {
        this.departNo = departNo;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public LocalDate getEmployedDate() {
        return employedDate;
    }

    public void setEmployedDate(LocalDate employedDate) {
        this.employedDate = employedDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

//    public String getMemberStatus() {
//        return memberStatus;
//    }
//
//    public void setMemberStatus(String memberStatus) {
//        this.memberStatus = memberStatus;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", departNo=" + departNo +
                ", positionName='" + positionName + '\'' +
                ", employedDate=" + employedDate +
                ", address='" + address + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
//                ", memberStatus='" + memberStatus + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
