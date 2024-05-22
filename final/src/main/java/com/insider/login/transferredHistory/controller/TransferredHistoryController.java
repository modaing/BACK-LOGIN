package com.insider.login.transferredHistory.controller;

import com.insider.login.department.entity.Department;
import com.insider.login.department.service.DepartmentService;
import com.insider.login.transferredHistory.dto.TransferredHistoryDTO;
import com.insider.login.transferredHistory.dto.TransferredHistoryDTOSecond;
import com.insider.login.transferredHistory.service.TransferredHistoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
public class TransferredHistoryController {

    private final TransferredHistoryService transferredHistoryService;
    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;


    public TransferredHistoryController(TransferredHistoryService transferredHistoryService, ModelMapper modelMapper, DepartmentService departmentService) {
        this.transferredHistoryService = transferredHistoryService;
        this.modelMapper = modelMapper;
        this.departmentService = departmentService;
    }

    /* 구성원 관리 페이지에서 근속년수 보여주는 logic */
    @GetMapping("/dateDifference/{transferredNo}")
    public ResponseEntity<String> getDateDifference(@PathVariable ("transferredNo") int transferredNo) {
        String dateDifference = transferredHistoryService.calculateDateDifference(transferredNo);       // 날짜의 차이
        return ResponseEntity.ok(dateDifference);
    }

    @GetMapping("/transferredHistory/{memberId}")
    public ResponseEntity<List<TransferredHistoryDTOSecond>> getTransferredHistoryRecord(@PathVariable ("memberId") String receivedMemberId) {
        try {
            int memberId = Integer.parseInt(receivedMemberId);
            List<TransferredHistoryDTO> transferredHistoryList = transferredHistoryService.getTransferredHistoryRecord(memberId);

            // Map TransferredHistoryDTO to TransferredHistoryDTOSecond
            List<TransferredHistoryDTOSecond> transferredHistoryDTOSecondList = new ArrayList<>();
            for (TransferredHistoryDTO historyDTO : transferredHistoryList) {
                TransferredHistoryDTOSecond historyDTOSecond = modelMapper.map(historyDTO, TransferredHistoryDTOSecond.class);
                // Populate DepartName based on DepartNo (assuming you have a method to get DepartName from DepartNo)
                String departName = getDepartNameByDepartNo(historyDTO.getNewDepartNo());
                historyDTOSecond.setNewDepartName(departName);
                transferredHistoryDTOSecondList.add(historyDTOSecond);
            }

            System.out.println("transferredHistoryDTO: " + transferredHistoryList);
            return ResponseEntity.ok().body(transferredHistoryDTOSecondList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    private String getDepartNameByDepartNo(int departNo) {
        Department department = departmentService.findDepartByDepartNo(departNo);
        return department.getDepartName();
    }

}
