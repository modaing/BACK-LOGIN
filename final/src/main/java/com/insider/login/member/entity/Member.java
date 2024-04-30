package com.insider.login.member.entity;

import com.insider.login.auth.image.entity.Image;
import com.insider.login.common.utils.MemberRole;
import com.insider.login.commute.entity.Commute;
import com.insider.login.department.entity.Department;
import com.insider.login.position.entity.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "member_info")
@AllArgsConstructor
public class Member {                 // JPA를 사용을 할 것이기 때문에 entity 설계 필수

    @Id
    @Column(name = "member_id", nullable = false)
    private int memberId;                           // 구성원 번호 (사번)
    @Column(name = "name", nullable = false)
    private String name;                            // 구성원 이름
    @Column(name = "password", nullable = false)
    private String password;                        // 비밀번호
    @Column(name = "depart_no", nullable = false)
    private int departNo;                           // 부서 번호
    @Column(name = "position_name" ,nullable = false)
    private String positionName;                    // 직급명
    @Column(name = "employed_date", nullable = false)
    private LocalDate employedDate;                 // 입사일
    @Column(name = "address", nullable = false)
    private String address;                         // 주소
    @Column(name = "phone_no", nullable = false)
    private String phoneNo;                         // 전화 번호
    @Column(name = "current_status", nullable = false)
    private String memberStatus;                    // 현재 상태
    @Column(name = "email", nullable = false)
    private String email;                           // 이메일
    @Column(name = "member_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRole role;                        // 권한
//    private com.insider.prefinal.common.UserRole userRole;
//    @Column(name = "member_image_no")
//    private int memberImageNo;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "depart_no", insertable = false, updatable = false)
    private Department department;                  // 부서
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "position_name")
    private Position position;                      // 직급
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_image_no", referencedColumnName = "member_image_no")
    private Image image;

    @OneToMany(mappedBy = "member")
    private List<Commute> commutes;                 // 출퇴근 리스트
    @Column(name = "transferred_no", nullable = false)
    private int transferredNo;                      // 발령 번호

    protected Member() {

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

    public Member(int memberId, String name, String password, LocalDate employedDate, String address, String phoneNo, String memberStatus, String email, MemberRole role, Department department, Position position, Image image) {
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
