package com.insider.login.leave.controller;

import com.insider.login.common.CommonController;
import com.insider.login.common.ResponseMessage;
import com.insider.login.leave.dto.LeaveAccrualDTO;
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
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LeaveController extends CommonController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    /**
     * 휴가 신청 내역 조회
     */
    @GetMapping("/leaveSubmits")
    public ResponseEntity<ResponseMessage> selectSubmitList(@RequestParam(value = "page", defaultValue = "0") int pageNumber,
                                                            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
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

    /**
     * 휴가 신청
     */
    @PostMapping("/leaveSubmits")
    public ResponseEntity<String> insertSubmit(@RequestParam("applicantId") int applicantId,
                                               @ModelAttribute LeaveSubmitDTO leaveSubmitDTO) {

        leaveSubmitDTO.setLeaveSubApplyDate(nowDate());

        return ResponseEntity.ok().body(leaveService.insertSubmit(leaveSubmitDTO));
    }

    /**
     * 휴가 신청 취소 (삭제)
     */
    @DeleteMapping("/leaveSubmits/{LeaveSubNo}")
    public ResponseEntity<String> deleteSubmit(@PathVariable("LeaveSubNo") int leaveSubNo) {
        return ResponseEntity.ok().body(leaveService.deleteSubmit(leaveSubNo));
    }

    /**
     * 휴가 취소 요청
     */
    @PostMapping("/leaveSubmit/{LeaveSubNo}")
    public ResponseEntity<String> insertSubmitCancel(@PathVariable("LeaveSubNo") int leaveSubNo,
                                                     @ModelAttribute LeaveSubmitDTO leaveSubmitDTO) {
        leaveSubmitDTO.setLeaveSubApplyDate(nowDate());
        return ResponseEntity.ok().body(leaveService.insertSubmitCancel(leaveSubmitDTO));
    }

    /**
     * 발생 내역 조회
     */
    @GetMapping("/leaveAccruals")
    public ResponseEntity<ResponseMessage> selectAccrualList(@RequestParam(value = "page", defaultValue = "0") int pageNumber,
                                                             @RequestParam(value = "direction", defaultValue = "DESC") String direction,
                                                             @RequestParam(value = "properties", defaultValue = "leaveSubNo") String properties) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Pageable pageable;

        if (direction != "DESC") {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, properties));
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, properties));
        }

        Page<LeaveAccrualDTO> accrualPage = leaveService.selectAccrualList(pageable);


        if (accrualPage.isEmpty()) {
            String errorMessage = "발생된 내역이 없습니다.";
            ResponseMessage responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), errorMessage, null);
            return new ResponseEntity<>(responseMessage, headers, HttpStatus.NOT_FOUND);
        }


        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("accrualPage", accrualPage);

        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    /**
     * 휴가 발생
     */
    @PostMapping("/leaveAccruals")
    public ResponseEntity<String> insertAccrual(@ModelAttribute LeaveAccrualDTO leaveAccrualDTO) {
        // TODO:시큐리티 안정화되면 토큰에서 처리자 사번 뽑아서 DTO에 담기
        return ResponseEntity.ok().body(leaveService.insertAccrual(leaveAccrualDTO));
    }

    /**
     * 상세 조회
     */
    @GetMapping("/leaveSubmits/{LeaveSubNo}")
    public ResponseEntity<ResponseMessage> selectSubmitByLeaveSubNo(@PathVariable("leaveSubNo") int leaveSubNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        LeaveSubmitDTO submit = leaveService.selectSubmitByLeaveSubNo(leaveSubNo);

        if (submit == null) {
            String errorMessage = "처리 과정에서 문제가 발생했습니다.";
            ResponseMessage responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), errorMessage, null);
            return new ResponseEntity<>(responseMessage, headers, HttpStatus.NOT_FOUND);
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("submit", submit);

        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    /**
     * 휴가 신청 처리
     */
    @PutMapping("/leaveSubmits/{LeaveSubNo}")
    public ResponseEntity<String> updateSubimt(@PathVariable("leaveSubNo") int leaveSubNo,
                                               @ModelAttribute LeaveSubmitDTO leaveSubmitDTO) {
        // TODO:시큐리티 안정화되면 토큰에서 승인자 사번 뽑아서 DTO에 담기
        leaveSubmitDTO.setLeaveSubNo(leaveSubNo);
        return ResponseEntity.ok().body(leaveService.updateSubmit(leaveSubmitDTO));

    }
}
