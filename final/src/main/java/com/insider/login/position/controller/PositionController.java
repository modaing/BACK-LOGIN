package com.insider.login.position.controller;

import com.insider.login.position.dto.PositionDTO;
import com.insider.login.position.service.PositionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PositionController {

    private final PositionService positionService;

    public PositionController (PositionService positionService) {
        this.positionService = positionService;
    }

    /** 직급 등록 */
    @PostMapping("/registPosition")
    public String registPosition(@RequestBody PositionDTO positionDTO) {
        positionService.insertPosition(positionDTO);

        return "registered position";
    }

    /** 직급 조회 */
    @GetMapping("/showAllPosition")
    public List<PositionDTO> showAllPositionList() {
        List<PositionDTO> positionDTOList = positionService.findAllPositionList();

        return positionDTOList;
    }

    /** 직급 삭제 */
    @DeleteMapping("/positions/{positionName}")
    public String deletePosition(@PathVariable("positionName") String positionName) {
        positionService.deletePosition(positionName);

        return "successfully deleted position";
    }



}
