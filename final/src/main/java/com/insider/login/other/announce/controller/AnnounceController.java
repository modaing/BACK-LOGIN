package com.insider.login.other.announce.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insider.login.common.CommonController;
import com.insider.login.common.ResponseMessage;
import com.insider.login.other.announce.dto.AncFileDTO;
import com.insider.login.other.announce.dto.AnnounceDTO;
import com.insider.login.other.announce.service.AnnounceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AnnounceController extends FileController {

    private final AnnounceService announceService;

    @GetMapping("/announces")
    public ResponseEntity<ResponseMessage> selectAncList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                                         @RequestParam(value = "sort", defaultValue = "sort") String sort,
                                                         @RequestParam(value = "direction", defaultValue = "DESC") String direction) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Pageable pageable = CommonController.getPageable(page, size, sort, direction);

        Page<AnnounceDTO> ancList = announceService.selectAncList(pageable);

        if (ancList.isEmpty()) {
            String errorMessage = "조회된 공지사항이 없습니다.";
            ResponseMessage responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), errorMessage, null);
            return new ResponseEntity<>(responseMessage, headers, HttpStatus.NOT_FOUND);
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("ancList", ancList.getContent());
        responseMap.put("currentPage", ancList.getNumber());
        responseMap.put("totalItems", ancList.getTotalElements());
        responseMap.put("totalPages", ancList.getTotalPages());

        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);

    }

    @PostMapping(value = "/announces", consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseMessage> insertAnnounce(@RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                          @RequestPart("announceDTO") String announceDTOJson) {

        // announceDTOJson을 AnnounceDTO 객체로 변환
        AnnounceDTO announceDTO = FileController.convertJsonToAnnounceDTO(announceDTOJson);

        Map<String, Object> serviceResult;
        if (files != null) {
            // 파일이 있는 경우
            serviceResult = announceService.insertAncWithFile(announceDTO, files);
            serviceResult.put("result", true);
        } else {
            // 파일이 없는 경우
            serviceResult = announceService.insertAnc(announceDTO);
            serviceResult.put("result", true);
        }

        if ((Boolean) serviceResult.get("result")) {
            return ResponseEntity.ok().body(new ResponseMessage(200, "등록 성공", serviceResult));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "등록 실패", serviceResult));
        }
    }





}
