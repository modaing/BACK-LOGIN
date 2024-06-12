package com.insider.login.notice.service;

import com.insider.login.member.entity.Member;
import com.insider.login.notice.dto.NoticeDTO;
import com.insider.login.notice.entity.Notice;
import com.insider.login.notice.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;
    private final Map<Integer, List<SseEmitter>> subscribers = new ConcurrentHashMap<>();

    public NoticeService(NoticeRepository noticeRepository,
                         ModelMapper modelMapper) {
        this.noticeRepository = noticeRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * 일반적인 HTTP 요청/응답 방식으로 구현한 알림 (실시간 기능X)
     */
    @Transactional
    public Map<String, Object> insertNewNotice(NoticeDTO newNotice) {
        /** 새로운 알림 수신 (등록) */
        log.info("[NoticeService] insertNewNotice");

        noticeRepository.save(modelMapper.map(newNotice, Notice.class));

        Map<String, Object> result = new HashMap<>();
        result.put("result", true);

        return result;
    }

    @Transactional
    public Map<String, Object> selectNoticeListByMemberId(int memberId) {
        /** 알림 조회 */
        log.info("[NoticeService] selectNoticeList");

        List<Notice> findNoticeListByMemberId = noticeRepository.findByMemberId(memberId);

        List<NoticeDTO> noticeDTOList = findNoticeListByMemberId.stream()
                .map(notice -> modelMapper.map(notice, NoticeDTO.class))
                .collect(Collectors.toList());

        log.info("[NoticeService] selectNoticeListByMemberId End ================");
        Map<String, Object> result = new HashMap<>();
        result.put("result", noticeDTOList);

        return result;
    }

    @Transactional
    public Map<String, Object> deleteNoticeByNoticeNo(int noticeNo) {
        /** 알림 선택 삭제 */
        log.info("[NoticeService] deleteNoticeByNoticeNo");

        Notice findNoticeByNoticeNo = noticeRepository.findByNoticeNo(noticeNo);

        noticeRepository.delete(findNoticeByNoticeNo);

        Map<String, Object> result = new HashMap<>();
        result.put("result", true);

        return result;
    }

    @Transactional
    public Map<String, Object> deleteNoticeListByMemberId(int memberId) {
        /**알림 전체 삭제 */
        log.info("[NoticeService] deleteNoticeListByMemberId");

        List<Notice> findNoticeListByMemberId = noticeRepository.findByMemberId(memberId);

        noticeRepository.deleteAll(findNoticeListByMemberId);

        Map<String, Object> result = new HashMap<>();
        result.put("result", true);

        return result;
    }

    /**
     * SSE(Server-Sent-Events) 방식으로 구현한 실시간 알림
     */

//    public SseEmitter subscribe(Long id) {
//    }
//
//    public void notify(Long id, String data) {
//    }
    public SseEmitter subscribe() {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        int memberId = userDetails.getAuthorities().size();
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int memberId = member.getMemberId();

        SseEmitter emitter = new SseEmitter();

        List<SseEmitter> memberSubscribers = subscribers.computeIfAbsent(memberId, k -> new ArrayList<>());
        memberSubscribers.add(emitter);

        emitter.onCompletion(() -> memberSubscribers.remove(emitter));
        emitter.onTimeout(() -> memberSubscribers.remove(emitter));

        return emitter;
    }

    public Notice createNotice(Notice notice) {
        Notice savedNotice = noticeRepository.save(notice);
        notifySubscribers(savedNotice);

        return savedNotice;
    }

    private void notifySubscribers(Notice notice) {
        List<SseEmitter> memberSubscribers = subscribers.get(notice.getMemberId());
        if (memberSubscribers != null) {
            for (SseEmitter emitter : memberSubscribers) {
                try {
                    emitter.send(notice);
                } catch (IOException e) {
                    memberSubscribers.remove(emitter);
                }
            }
        }
    }
}
