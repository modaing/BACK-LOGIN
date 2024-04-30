package com.insider.login.leave.controller;

import com.insider.login.common.CommonController;
import com.insider.login.common.ResponseMessage;
import com.insider.login.leave.dto.LeaveInfoDTO;
import com.insider.login.leave.dto.LeaveSubmitDTO;
import com.insider.login.leave.service.LeaveService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LeaveController extends CommonController{

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @GetMapping("/leaveSubmits")
    public ResponseEntity<ResponseMessage> selectSubmitList(@RequestParam(value = "page", defaultValue = "0") int pageNumber,
                                                            @RequestParam(value = "direction", defaultValue = "DESC" ) String direction,
                                                            @RequestParam(value = "properties", defaultValue = "leaveSubNo") String properties,
                                                            @RequestParam(value = "memberid", defaultValue = "0") int memberId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Pageable pageable;

        if (direction != "DESC") {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, properties));
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, properties));
        }

        Page<LeaveSubmitDTO> submitPage = leaveService.selectLeaveSubmitList(memberId, pageable);

        if (submitPage.isEmpty()) {
            String errorMessage = "신청된 휴가 내역이 없습니다.";
            ResponseMessage responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), errorMessage, null);
            return new ResponseEntity<>(responseMessage, headers, HttpStatus.NOT_FOUND);
        }


        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("submitPage", submitPage);

        // 멤버 아이디가 있다면 개인 내역 조회이기 때문에 요청자의 휴가 보유 내역도 같이 전달함
        if (memberId != 0) {
            LeaveInfoDTO leaveInfo = leaveService.getLeaveInfoById(memberId);
            responseMap.put("leaveInfo", leaveInfo);
        }

        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

}
