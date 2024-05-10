package com.insider.login.approval.controller;

import com.insider.login.approval.dto.*;
import com.insider.login.approval.service.ApprovalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@Slf4j
public class ApprovalController {


    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;

    @Value("${file.file-dir}")
    private String FILE_DIR;


    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService){
        this.approvalService = approvalService;
    }


    //전자결재 상세 조회
    @Tag(name = "전자결재 상세 조회", description = "전자결재 상세 조회")
    @GetMapping("/approvals/{approvalNo}")
    public ResponseEntity<ResponseDTO> selectApprovalByNo(@PathVariable(name="approvalNo") String approvalNo){
       /* ApprovalDTO approvalDTO = approvalService.selectApproval(approvalNo);
        log.info("approvalDTO: " + approvalDTO);*/

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전자결재 상세 조회 성공", approvalService.selectApproval(approvalNo)));

    }

    @Tag(name = "전자결재 목록 조회", description = "전자결재 목록 조회")
    @GetMapping("/approvals")
    public ResponseEntity<ResponseDTO> selectApprovalList(@RequestParam("fg") String fg,
                                                          @RequestParam(name="page",defaultValue = "0") String page,
                                                          @RequestParam(name="title", defaultValue = "") String title,
                                                          @RequestHeader(value = "memberId", required = false) String memberIdstr){
        log.info("****컨트롤러 들어왔어");

        int memberId = 0;

        if(memberIdstr == null){
            //현재 사용자의 인증 정보 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info("memberId: " + authentication.getName());

            //인증 정보에서 사용자의 식별 정보 가져오기
            memberId = Integer.parseInt(authentication.getName());

        }
        else{
            memberId = Integer.parseInt(memberIdstr);
        }
        log.info("현재 사용자 : " + memberId);

        Map<String, Object> condition = new HashMap<>();
        condition.put("flag", fg);
        condition.put("offset", 0);
        condition.put("limit", 10);
        condition.put("direction", null);
        condition.put("title", title);

        int pageNo = Integer.parseInt(page);
        System.out.println("현재 pageNo : " + pageNo);
        log.info("현재 pageNo : " + pageNo);


        Page<ApprovalDTO> approvalDTOPage =  approvalService.selectApprovalList(memberId, condition, pageNo);
//        log.info("approvalDTOPage : " + approvalDTOPage.getContent());

            ResponseDTO response = new ResponseDTO(HttpStatus.OK, "상신 목록 조회 성공", approvalDTOPage);
            System.out.println("조회성공");
            return ResponseEntity.ok().body(response);

    }


    @Tag(name = "전자결재 회수", description = "회수")
    @PutMapping(value = "/approvals/{approvalNo}")
    public ResponseEntity<ResponseDTO> updateApproval(@PathVariable(name="approvalNo") String approvalNo){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전자 결재 회수 성공", approvalService.updateApproval(approvalNo)));

    }

    @Tag(name = "전자결재 기안", description = "기안")
    @PostMapping("/approvals")
    public ResponseEntity<ResponseDTO> insertApproval(@ModelAttribute ApprovalDTO approvalDTO,
                                                      @RequestParam("multipartFile") List<MultipartFile> multipartFile,
                                                      @RequestHeader(name = "memberId", required = false) String memberIdstr){

        log.info("****컨트롤러 들어왔어");
        System.out.println("*****컨트롤러 들어왔어");

        //전자결재 번호(연도+_양식번호+순번)
        int Year = LocalDate.now().getYear();
        String formNo = approvalDTO.getFormNo();
        String YearFormNo = Year + "-" + formNo;
        log.info("YearFormNo : " + YearFormNo);

        String lastApprovalNo = approvalService.selectApprovalNo(YearFormNo);

        log.info("lastApprovalNo : " + lastApprovalNo);

        String[] parts = lastApprovalNo.split("-");
        String lastPart = parts[parts.length -1];


        String sequenceString = lastPart.replaceAll("\\D", "");
        log.info("sequenceString: " + sequenceString);

        int sequenceNumber = Integer.parseInt(sequenceString) +1;
        log.info("늘어난 번호 : " + sequenceNumber);


        String approvalNo = Year + "-" + formNo + String.format("%05d",sequenceNumber);
        log.info("approvalNo: " + approvalNo);

        approvalDTO.setApprovalNo(approvalNo);


        //기안자사번
        //현재 사용자의 인증 정보 가져오기
        int memberId = 0;

        if(memberIdstr == null){
            //현재 사용자의 인증 정보 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info("memberId: " + authentication.getName());

            //인증 정보에서 사용자의 식별 정보 가져오기
            memberId = Integer.parseInt(authentication.getName());

        }
        else{
            memberId = Integer.parseInt(memberIdstr);
        }
        log.info("현재 사용자 : " + memberId);

        approvalDTO.setMemberId(memberId);


        //결재자번호(결재번호+_apr+순번)
        List<ApproverDTO> approverDTOList = approvalDTO.getApprover();
        for(int i = 0; i < approverDTOList.size(); i++){
            ApproverDTO approverDTO = approverDTOList.get(i);
            approverDTO.setApproverNo(approvalNo + "_apr" + String.format("%03d", (i + 1)));
            approverDTO.setApprovalNo(approvalNo);
            approverDTO.setApproverStatus("대기");
            approverDTO.setApproverOrder(i + 1);
        }
        approvalDTO.setApprover(approverDTOList);

        //참조자번호(결재번호+_ref+순번)
        List<ReferencerDTO> referencerDTOList = approvalDTO.getReferencer();
        for(int i = 0; i < referencerDTOList.size(); i++){
            ReferencerDTO referencerDTO = referencerDTOList.get(i);
            referencerDTO.setRefNo(approvalNo + "_ref" + String.format("%03d", (i + 1)));
            referencerDTO.setApprovalNo(approvalNo);
            referencerDTO.setRefOrder(i + 1);
        }
        approvalDTO.setReferencer(referencerDTOList);

        List<AttachmentDTO> attachmentDTOList =  new ArrayList<>();

        String savePath = UPLOAD_DIR + FILE_DIR;

        //첨부파일번호(결재번호+_f+순번)
        if(!multipartFile.isEmpty()){
            for(int i = 0; i < multipartFile.size(); i++){
                MultipartFile oneFile = multipartFile.get(i);

                AttachmentDTO attachmentDTO = new AttachmentDTO();
                attachmentDTO.setFileNo(approvalNo + "_f" + String.format("%03d", (i +1)));
                attachmentDTO.setFileOriname(oneFile.getOriginalFilename());
                attachmentDTO.setFileSavename(oneFile.getName());
                attachmentDTO.setFileSavepath(savePath);
                attachmentDTO.setApprovalNo(approvalNo);

                attachmentDTOList.add(attachmentDTO);
            }
            approvalDTO.setAttachment(attachmentDTOList);
        }


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전자결재 기안 성공",
                approvalService.insertApproval(approvalDTO, multipartFile)));
    }

    @Tag(name = "전자결재 처리", description = "결재처리")
    @PutMapping("/approvers/{approverNo}")
    public ResponseEntity<ResponseDTO> updateApprover(@PathVariable(name = "approverNo") String approverNo,
                                                      @RequestBody ApproverDTO approverStatus,
                                                      @RequestBody ApprovalDTO rejectReason){



        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("approverStatus", approverStatus.getApproverStatus());
        statusMap.put("rejectReason", rejectReason.getRejectReason());

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전자결재" + approverStatus + "처리 완료",
                approvalService.updateApprover(approverNo, statusMap)));
    }

}
