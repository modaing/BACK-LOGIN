package com.insider.login.other.note.controller;

import com.insider.login.other.common.CommonController;
import com.insider.login.other.common.ResponseMessage;
import com.insider.login.other.note.dto.NoteDTO;
import com.insider.login.other.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class NoteController extends CommonController {

    private NoteService noteService;


    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;

    }


    /** 받은 쪽지, 보낸 쪽지 리스트  */
    @GetMapping("/members/{memberNo}/notes")
    public ResponseEntity<ResponseMessage> receiveNoteList(@PathVariable("memberNo") int memberNo,
                                                           @RequestParam(value = "receiverId", required = false) Integer receiverId,
                                                           @RequestParam(value = "senderId", required = false) Integer senderId,
                                                           @RequestParam(value = "deleteYn", required = true) String deleteYn,
                                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                           @RequestParam(value = "sort", defaultValue = "sort") String sort,
                                                           @RequestParam(value = "direction", defaultValue = "DESC") String direction) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));


        Pageable pageable = CommonController.getPageable(page, size, sort, direction);

        Page<NoteDTO> notePage = noteService.selectNoteList(memberNo, receiverId, senderId, pageable, deleteYn);

        if (notePage.isEmpty()) {
            // null 예외처리
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("notes", notePage.getContent());
        responseMap.put("currentPage", notePage.getNumber());
        responseMap.put("totalItems", notePage.getTotalElements());
        responseMap.put("totalPages", notePage.getTotalPages());


        // 요청 파라미터와 페이징 정보를 ResponseMessage 객체에 추가
        ResponseMessage responseMessage = new ResponseMessage(200, "조회성공", responseMap);


        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);

    }

    @PostMapping("/notes")
    public ResponseEntity<ResponseMessage> insertNote(@RequestBody NoteDTO noteDTO) {

        noteDTO.setSendNoteDate(nowDate());
        noteDTO.setDeleteYn("N");

        return ResponseEntity.ok().body(new ResponseMessage(200, "성공", noteService.insertNote(noteDTO)));
    }








}
