package com.insider.login.leave.repository;

import com.insider.login.position.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LeavePositionRepository extends JpaRepository<Position, String> {

    @Query("SELECT p.positionName FROM Position p WHERE p.positionLevel = :positionLevel")
    String findPositionNameByPositionLevel(@Param("positionLevel") String positionLevel);


}
