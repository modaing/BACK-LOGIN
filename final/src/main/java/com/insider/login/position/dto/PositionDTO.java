package com.insider.login.position.dto;

import com.insider.login.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

public  class PositionDTO {

    private String positionName;
    private String positionLevel;
    public PositionDTO() {
    }

    public PositionDTO (String positionName, String positionLevel) {
        this.positionName = positionName;
        this.positionLevel = positionLevel;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

//    public List<Member> getMembers() {
//        return members;
//    }
//
//    public void setMembers(List<Member> members) {
//        this.members = members;
//    }

    @Override
    public String toString() {
        return "Position{" +
                "positionName='" + positionName + '\'' +
                ", positionLevel='" + positionLevel + '\'' +
//                ", members=" + members +
                '}';
    }
}