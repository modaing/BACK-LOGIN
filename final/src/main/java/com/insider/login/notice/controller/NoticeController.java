package com.insider.login.notice.controller;

import com.insider.login.common.ResponseMessage;
import com.insider.login.notice.dto.NoticeDTO;
import com.insider.login.notice.entity.Notice;
import com.insider.login.notice.service.NoticeService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    /** 일반적인 HTTP 요청/응답 방식으로 구현한 알림 (실시간 기능X) */
    /** 새로운 알림 수신 (등록) */
    @PostMapping("/notices")
    public ResponseEntity<ResponseMessage> insertNoticeOfReqForCorrect(@RequestBody NoticeDTO newNotice) {
        return ResponseEntity.ok().body(new ResponseMessage(200, "등록 성공", noticeService.insertNewNotice(newNotice)));
    }

    /** 수신한 알림 내역 조회 */
    @GetMapping("/notices")
    public ResponseEntity<ResponseMessage> selectNoticeListByMemberId(@RequestParam(value = "memberId") int memberId) {
        return ResponseEntity.ok().body(new ResponseMessage(200, "조회 성공", noticeService.selectNoticeListByMemberId(memberId)));
    }

    /** 알림 선택 삭제 */
    @DeleteMapping("/notices/{noticeNo}")
    public ResponseEntity<ResponseMessage> deleteNoticeByNoticeNo(@PathVariable(value = "noticeNo") int noticeNo) {
        return ResponseEntity.ok().body(new ResponseMessage(200, "선택 삭제 성공", noticeService.deleteNoticeByNoticeNo(noticeNo)));
    }

    /** 알림 전체 삭제 */
    @DeleteMapping("/members/{memberId}/notices")
    public ResponseEntity<ResponseMessage> deleteNoticeListByMemberId(@PathVariable(value = "memberId") int memberId) {
        return ResponseEntity.ok().body(new ResponseMessage(200, "전체 삭제 성공", noticeService.deleteNoticeListByMemberId(memberId)));
    }

    /** SSE(Server-Sent-Events) 방식으로 구현한 실시간 알림 */

    //    @GetMapping(value = "/notices/subscribe/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public SseEmitter subscribe(@PathVariable Long id) {
//        return noticeService.subscribe(id);
//    }
//
//    @PostMapping("/notices/send-data/{id}")
//    public void sendData(@PathVariable Long id) {
//        noticeService.notify(id, "data");
//    }

    @GetMapping("/notices")
    public SseEmitter subscribe() {
        return noticeService.subscribe();
    }

    public ResponseEntity<Notice> createNotice(@RequestBody Notice notice) {
        Notice savedNotice = noticeService.createNotice(notice);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedNotice);
    }
}
