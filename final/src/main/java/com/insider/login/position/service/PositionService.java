package com.insider.login.position.service;

import com.insider.login.position.dto.PositionDTO;
import com.insider.login.position.entity.Position;
import com.insider.login.position.repository.PositionRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class PositionService {

    private final ModelMapper modelMapper;
    private final PositionRepository positionRepository;

    public PositionService (ModelMapper modelMapper, PositionRepository positionRepository) {
        this.modelMapper = modelMapper;
        this.positionRepository = positionRepository;
    }
    @Transactional
    public void insertPosition(PositionDTO positionDTO) {
        Position position = modelMapper.map(positionDTO, Position.class);
        System.out.println("확인용: " + position);
        positionRepository.save(position);
    }

    public List<PositionDTO> findAllPositionList() {
        List<Position> positionList = positionRepository.findAll();
        Type listType = new TypeToken<List<PositionDTO>>() {}.getType();
        List<PositionDTO> departmentList = modelMapper.map(positionList, listType);

        return departmentList;
    }

    @Transactional
    public void deletePosition(String positionName) {
        System.out.println("delete position name: " + positionName);
        positionRepository.deletePositionByPositionName(positionName);
    }
}
