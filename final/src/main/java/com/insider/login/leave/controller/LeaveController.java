package com.insider.login.leave.controller;

import com.insider.login.common.CommonController;
import com.insider.login.common.ResponseMessage;
import com.insider.login.leave.dto.LeaveAccrualDTO;
import com.insider.login.leave.dto.LeaveInfoDTO;
import com.insider.login.leave.dto.LeaveSubmitDTO;
import com.insider.login.leave.entity.LeaveSubmit;
import com.insider.login.leave.service.LeaveService;

import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
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
                                                            @RequestParam(value = "memberId", defaultValue = "0") int memberId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Pageable pageable;

        if (!direction.equals("DESC")) {
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
    public ResponseEntity<String> insertSubmit(@RequestBody LeaveSubmitDTO leaveSubmitDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        leaveSubmitDTO.setLeaveSubApplyDate(nowDate());

        String result = "";

        result = leaveService.insertSubmit(leaveSubmitDTO);

        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * 휴가 신청 취소 (삭제)
     */
    @DeleteMapping("/leaveSubmits/{LeaveSubNo}")
    public ResponseEntity<String> deleteSubmit(@PathVariable("LeaveSubNo") int leaveSubNo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return ResponseEntity.ok().headers(headers).body(leaveService.deleteSubmit(leaveSubNo));
    }

    /**
     * 발생 내역 조회
     */
    @GetMapping("/leaveAccruals")
    public ResponseEntity<ResponseMessage> selectAccrualList(@RequestParam(value = "page", defaultValue = "0") int pageNumber,
                                                             @RequestParam(value = "direction", defaultValue = "DESC") String direction,
                                                             @RequestParam(value = "properties", defaultValue = "leaveAccrualNo") String properties) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Pageable pageable;

        if (!direction.equals("DESC")) {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, properties));
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, properties));
        }

        Page<LeaveAccrualDTO> accrualPage = leaveService.selectAccrualList(pageable);


        if (accrualPage.isEmpty()) {
            String errorMessage = "처리 과정에서 문제가 발생했습니다. 다시 시도해주세요";
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
    public ResponseEntity<String> insertAccrual(@RequestBody LeaveAccrualDTO leaveAccrualDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        // TODO: 처리자 사번 뽑아서 DTO에 담기
        leaveAccrualDTO.setAccrualDate(nowDate());
        return ResponseEntity.ok().headers(headers).body(leaveService.insertAccrual(leaveAccrualDTO));
    }

    /**
     * 상세 조회
     */
    @GetMapping("/leaveSubmits/{leaveSubNo}")
    public ResponseEntity<ResponseMessage> selectSubmitByLeaveSubNo(@PathVariable("leaveSubNo") int leaveSubNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        LeaveSubmitDTO submit = leaveService.selectSubmitByLeaveSubNo(leaveSubNo);

        if (submit == null) {
            String errorMessage = "처리 과정에서 문제가 발생했습니다. 다시 시도해주세요";
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
    @PutMapping("/leaveSubmits/{leaveSubNo}")
    public ResponseEntity<String> updateSubimt(@PathVariable("leaveSubNo") int leaveSubNo,
                                               @RequestBody LeaveSubmitDTO leaveSubmitDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        // TODO:시큐리티 안정화되면 토큰에서 승인자 사번 뽑아서 DTO에 담기 현재는 임시
        leaveSubmitDTO.setLeaveSubApprover(200401023);

        leaveSubmitDTO.setLeaveSubNo(leaveSubNo);
        leaveSubmitDTO.setLeaveSubProcessDate(nowDate());
        return ResponseEntity.ok().headers(headers).body(leaveService.updateSubmit(leaveSubmitDTO));

    }

    /**
     * 휴가 보유 내역 조회
     */
    @PostMapping("/leaves")
    public ResponseEntity<ResponseMessage> selectLeavesList(@RequestParam(value = "page", defaultValue = "0") int pageNumber,
                                                            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
                                                            @RequestParam(value = "properties", defaultValue = "leaveSubNo") String properties) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Pageable pageable;

        if (!direction.equals("DESC")) {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.ASC, properties));
        } else {
            pageable = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, properties));
        }

        Page<LeaveInfoDTO> leavesList = leaveService.selectLeavesList(pageable);

        if (leavesList.isEmpty()) {
            String errorMessage = "처리 과정에서 문제가 발생했습니다. 다시 시도해주세요";
            ResponseMessage responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), errorMessage, null);
            return new ResponseEntity<>(responseMessage, headers, HttpStatus.NOT_FOUND);
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("leavesList", leavesList);

        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

}
