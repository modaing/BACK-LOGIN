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
@RequestMapping("/approvals")
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
    @GetMapping("/{approvalNo}")
    public ResponseEntity<ResponseDTO> selectApprovalByNo(@PathVariable(name="approvalNo") String approvalNo){
       /* ApprovalDTO approvalDTO = approvalService.selectApproval(approvalNo);
        log.info("approvalDTO: " + approvalDTO);*/

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전자결재 상세 조회 성공", approvalService.selectApproval(approvalNo)));

    }

    @Tag(name = "전자결재 목록 조회", description = "전자결재 목록 조회")
    @GetMapping("")
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

    //전자결재 기안 화면으로
    @PostMapping("/")
    public void goInsertApproval() {

    }

    @Tag(name = "전자결재 회수", description = "회수")
    @PutMapping(value = "/{approvalNo}")
    public ResponseEntity<ResponseDTO> updateApproval(@PathVariable(name="approvalNo") String approvalNo){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전자 결재 회수 성공", approvalService.updateApproval(approvalNo)));

    }

    @Tag(name = "전자결재 기안", description = "기안")
    @PostMapping(value = "")
    public ResponseEntity<ResponseDTO> insertApproval(@RequestBody ApprovalDTO approvalDTO,
                                                      List<MultipartFile> multipartFile,
                                                      @RequestHeader(value = "memberId", required = false) String memberIdstr){


        //전자결재 번호(연도+_양식번호+순번)
        int Year = LocalDate.now().getYear();
        String formNo = approvalDTO.getFormNo();
        String YearFormNo = Year + "-" + formNo;

        String lastApprovalNo = approvalService.selectApprovalNo(YearFormNo);

        String sequenceString = lastApprovalNo.replaceAll("^\\D+", "");
        int sequenceNumber = Integer.parseInt(sequenceString) +1;


        String approvalNo = Year + "-" + formNo + String.format("%05d",sequenceNumber);

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
        }
        approvalDTO.setApprover(approverDTOList);

        //참조자번호(결재번호+_ref+순번)
        List<ReferencerDTO> referencerDTOList = approvalDTO.getReferencer();
        for(int i = 0; i < referencerDTOList.size(); i++){
            ReferencerDTO referencerDTO = referencerDTOList.get(i);
            referencerDTO.setRefNo(approvalNo + "_ref" + String.format("%03d", (i + 1)));
        }
        approvalDTO.setReferencer(referencerDTOList);

        List<AttachmentDTO> attachmentDTOList =  new ArrayList<>();

        String savePath = UPLOAD_DIR + FILE_DIR;

        //첨부파일번호(결재번호+_f+순번)
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



        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전자결재 기안 성공",
                approvalService.insertApproval(approvalDTO, multipartFile)));
    }


    //전자결재 기안
  /*  @PostMapping("/")
    public ModelAndView insertApproval(@RequestParam ApprovalDTO approvalDTO,
                                       @RequestParam(value = "files", required = false) MultipartFile[] files,
                                       ModelAndView mv) {

        if (files.length > 0) {
            String rootLocation = UPLOAD_DIR + FILE_DIR;

            String fileUploadDirectory = rootLocation + "/approval";

            File directory = new File(fileUploadDirectory);

            //파일 저장경로가 존재하지 않은 경우 디렉토리를 생성한다.
            if (!directory.exists()) {
                directory.mkdirs();
            }

            //업로드 파일 하나하나에 대한 정보를 담을 리스트
            List<Map<String, String>> fileList = new ArrayList<>();

            try {
                for (MultipartFile File : files) {
                    //파일 하나씩 꺼내기

                    if(File.getSize() > 0){ //파일이 있다면
                        List<MultipartFile> FileList = new ArrayList<>();

                        String originFileName = File.getOriginalFilename();
                        System.out.println("originFileName : " + originFileName);

                        String ext= originFileName.substring(originFileName.lastIndexOf("."));
                        String savedFileName = UUID.randomUUID().toString().replace("-", "") + ext;

                        File.transferTo(new File(fileUploadDirectory + "/" + savedFileName));
                        //저장 경로에 파일 저장

                        Map<String, String> fileMap = new HashMap<>();
                        fileMap.put("originFileName", originFileName);
                        fileMap.put("savedFileName", savedFileName);
                        fileMap.put("savePath", fileUploadDirectory);

                        fileList.add(fileMap);
                        //파일 하나하나에 정보를 저장하고 그걸 Map에 담아 각 Map을 List에 담기
                        //list({map}, {map}, {map},...)
                    }
                }

                List<AttachmentDTO> list = approvalDTO.getAttachment();

                for(int i = 0; i < fileList.size(); i++){
                    //list에 들어간 map 을 하나씩 꺼내기
                    Map<String, String> file = fileList.get(i);
                    System.out.println("fileList.get(" + i + ")번째 : " + fileList.get(i));

                    AttachmentDTO fileInfo = new AttachmentDTO();

                    fileInfo.setFileOriname(file.get("originFileName"));
                    fileInfo.setFileSavename(file.get("savedFileName"));
                    fileInfo.setFileSavepath(file.get("savePath"));
                    //꺼낸 map의 key로 value를 불러와 DTO에 넣어주기


                    list.add(fileInfo);
                    //정보를 담은 DTO를 list에 넣기
                }
                System.out.println("파일 list: " + list);

                approvalDTO.setAttachment(list);
                //DTO를 담은 list를 전체 Approval DTO에 담기


                *//* 현재 로그인중인 사번 가져오기 *//*
                int memberId = 2024001001;

                approvalDTO.setMemberId(memberId);              //memberId 저장
                approvalDTO.setApprovalDate(now().toString());  //date 저장

                String thisYear = approvalDTO.getApprovalDate().substring(0,3); //연도만 빼오기
                int appCount = approvalService.findCountApproval(thisYear);     //현재 연도의 전자결재 수 가져오기

                approvalDTO.setApprovalNo(thisYear + "-" + approvalDTO.getFormNo() + String.format("%05d", (appCount + 1)));
                //전자결재No = 현재연도-양식번호0000순번  ex) 2024-con00001

                approvalDTO.setApprovalStatus("처리중");           //최초 상태 저장

                System.out.println("controller 들어온 데이터 : " + approvalDTO);

                approvalService.insertApproval(approvalDTO);
                System.out.println("서비스 다녀왔대요");


            } catch (IllegalStateException | IOException e) {
                //무슨 에러가 발생해도 넣어준 파일 지워주기

                e.printStackTrace();
            }

        } else {

        }


        return mv;
    }*/


}
