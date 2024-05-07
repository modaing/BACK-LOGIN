package com.insider.login.position.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "position_info")
public class Position {

    @Id
    @Column(name = "position_name", nullable = false)
    private String positionName;
    @Column(name = "position_level", nullable = false, unique = true)
    private String positionLevel;

    protected Position() {
    }

    public Position(String positionName, String positionLevel) {
        this.positionName = positionName;
        this.positionLevel = positionLevel;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getPositionLevel() {
        return positionLevel;
    }
    @Override
    public String toString() {
        return "Position{" +
                "positionName='" + positionName + '\'' +
                ", positionLevel='" + positionLevel + '\'' +
                '}';
    }
}
