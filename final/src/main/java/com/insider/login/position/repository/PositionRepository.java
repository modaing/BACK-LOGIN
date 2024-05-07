package com.insider.login.position.repository;

import com.insider.login.position.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

    void deletePositionByPositionName(String positionName);
}
