package com.insider.login.position.dto;

public class PositionDetailsDTO {

    private String positionName;
    private String positionLevel;
    private int noOfPeople;

    public PositionDetailsDTO() {
    }

    public PositionDetailsDTO(String positionName, String positionLevel, int noOfPeople) {
        this.positionName = positionName;
        this.positionLevel = positionLevel;
        this.noOfPeople = noOfPeople;
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

    public int getNoOfPeople() {
        return noOfPeople;
    }

    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople = noOfPeople;
    }

    @Override
    public String toString() {
        return "PositionDetailsDTO{" +
                "positionName='" + positionName + '\'' +
                ", positionLevel='" + positionLevel + '\'' +
                ", noOfPeople=" + noOfPeople +
                '}';
    }
}
