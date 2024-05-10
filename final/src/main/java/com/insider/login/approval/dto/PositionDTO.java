package com.insider.login.approval.dto;

public class PositionDTO {

    private String positionName;        //직급이름
    private String positionLevel;       //직급레벨

    public PositionDTO() {
    }

    public PositionDTO(String positionName, String positionLevel) {
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

    @Override
    public String toString() {
        return "PositionDTO{" +
                "positionName='" + positionName + '\'' +
                ", positionLevel='" + positionLevel + '\'' +
                '}';
    }
}
