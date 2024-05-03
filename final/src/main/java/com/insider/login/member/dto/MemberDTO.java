package com.insider.login.member.dto;

import com.insider.login.auth.image.entity.Image;
import com.insider.login.common.utils.MemberRole;
import com.insider.login.department.entity.Department;
import com.insider.login.position.entity.Position;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemberDTO {

    private int memberId;
    private String name;
    private String password;
    private LocalDate employedDate;
    private String address;
    private String phoneNo;
    private String memberStatus;
    private String email;
    private MemberRole role;

    @ManyToOne
    @JoinColumn
    private Department department;
    @ManyToOne
    @JoinColumn
    private Position position;
    @OneToOne
    @JoinColumn
    private Image image;

    public MemberDTO() {

    }

    public List<String> getRoleList() {
        if (this.role.getRole().length() > 0) {
            return Arrays.asList(this.role.getRole().split(","));
        }
        return new ArrayList<>();
    }

    public int getMemberId() {
        return memberId;
    }

    public MemberDTO(int memberId, String name, String password, LocalDate employedDate, String address, String phoneNo, String memberStatus, String email, MemberRole role, Department department, Position position, Image image) {
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.employedDate = employedDate;
        this.address = address;
        this.phoneNo = phoneNo;
        this.memberStatus = memberStatus;
        this.email = email;
        this.role = role;
        this.department = department;
        this.position = position;
        this.image = image;
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

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MemberRole getRole() {
        return role;
    }

    public void setRole(MemberRole role) {
        this.role = role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", employedDate=" + employedDate +
                ", address='" + address + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", memberStatus='" + memberStatus + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", department=" + department +
                ", position=" + position +
                ", image=" + image +
                '}';
    }

    public void setMemberId(Object memberId, Class<Integer> integerClass) {
    }
}
