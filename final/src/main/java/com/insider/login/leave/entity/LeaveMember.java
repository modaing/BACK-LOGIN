package com.insider.login.leave.entity;

import jakarta.persistence.*;


@Entity
@Table(name="member_info")
public class LeaveMember {

    @Id
    @Column(name = "member_id", nullable = false)
    private int memberId;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "depart_no", nullable = false)
    private int departNo;

    @Column(name = "position_level")
    private String positionLevel;

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public int getDepartNo() {
        return departNo;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    @Override
    public String toString() {
        return "LeaveMember{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", departNo='" + departNo + '\'' +
                ", positionLevel='" + positionLevel + '\'' +
                '}';
    }
}