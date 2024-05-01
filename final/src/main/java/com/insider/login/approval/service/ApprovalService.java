package com.insider.login.approval.service;

import com.insider.login.approval.builder.ApprovalBuilder;
import com.insider.login.approval.builder.ApproverBuilder;
import com.insider.login.approval.dto.*;
import com.insider.login.approval.entity.*;
import com.insider.login.approval.repository.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.LocalDateTime.now;

@Service
@Slf4j
public class ApprovalService {

    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;

    @Value("${file.file-dir}")
    private String FILE_DIR;

    private ApprovalRepository approvalRepository;
    private ApproverRepository approverRepository;
    private AttachmentRepository attachmentRepository;
    private ReferencerRepository referencerRepository;
    private MemberRepository memberRepository;
    private DepartmentRepository departmentRepository;
    private FormRepository formRepository;

    private final ModelMapper modelMapper;

    public ApprovalService(ApprovalRepository approvalRepository,
                           ApproverRepository approverRepository,
                           AttachmentRepository attachmentRepository,
                           ReferencerRepository referencerRepository,
                           MemberRepository memberRepository,
                           DepartmentRepository departmentRepository,
                           FormRepository formRepository,
                           ModelMapper modelMapper){
        this.approvalRepository = approvalRepository;
        this.approverRepository = approverRepository;
        this.attachmentRepository = attachmentRepository;
        this.referencerRepository = referencerRepository;
        this.memberRepository = memberRepository;
        this.departmentRepository = departmentRepository;
        this.formRepository = formRepository;
        this.modelMapper = modelMapper;
    }

    //전자결재 기안(등록)
    @Transactional
    public void insertApproval(ApprovalDTO approvalDTO, List<MultipartFile> files) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Approval approval = new Approval(
                approvalDTO.getApprovalNo(),
                approvalDTO.getMemberId(),
                approvalDTO.getApprovalTitle(),
                approvalDTO.getApprovalContent(),
                LocalDateTime.parse(approvalDTO.getApprovalDate(),formatter),
                approvalDTO.getApprovalStatus(),
                approvalDTO.getRejectReason(),
                approvalDTO.getFormNo()
        );

        // Approval 엔티티 저장
        approvalRepository.save(approval);

        //결재선 꺼내기
        for(int i = 0; i < approvalDTO.getApprover().size(); i++){

            Approver approver = new Approver(
                    approvalDTO.getApprover().get(i).getApproverNo(),
                    approvalDTO.getApprover().get(i).getApprovalNo(),
                    approvalDTO.getApprover().get(i).getApproverOrder(),
                    approvalDTO.getApprover().get(i).getApproverStatus(),
                    LocalDateTime.parse(approvalDTO.getApprover().get(i).getApproverDate(), formatter),
                    approvalDTO.getApprover().get(i).getMemberId()
            );

            //Approver 엔티티 저장
            approverRepository.save(approver);
        }

        //참조선 꺼내기
        for(int i = 0; i < approvalDTO.getReferencer().size(); i++){
            Referencer referencer = new Referencer(
                    approvalDTO.getReferencer().get(i).getRefNo(),
                    approvalDTO.getReferencer().get(i).getApprovalNo(),
                    approvalDTO.getReferencer().get(i).getMemberId(),
                    approvalDTO.getReferencer().get(i).getRefOrder()
            );

            //Referencer 엔티티 저장
            referencerRepository.save(referencer);
        }

        // 파일 꺼내기

        List<AttachmentDTO> attachmentList = approvalDTO.getAttachment();

        String savePath = UPLOAD_DIR + FILE_DIR;

        List<Map<String, String>> fileList = new ArrayList<>();


        if(files != null && !files.isEmpty()){
            try {
                String savedName = "";
                String ext = "";

                for(int i = 0; i < files.size(); i++)
                {
                    MultipartFile file = files.get(i);

                    AttachmentDTO attachmentDTO = approvalDTO.getAttachment().get(i);
                    ext = attachmentDTO.getFileOriname().substring(attachmentDTO.getFileOriname().lastIndexOf("."));
                    savedName = UUID.randomUUID().toString().replace("-", "") + ext;
                    //파일저장명 암호화

                    Map<String, String> fileMap = new HashMap<>();

                    fileMap.put("originFileName", file.getOriginalFilename());
                    fileMap.put("savedFileName", savedName);
                    fileMap.put("savePath", savePath);

                    attachmentDTO.setFileSavename(savedName);

                    Path uploadPath = Paths.get(savePath);

                    if(!Files.exists(uploadPath)){
                        Files.createDirectories(uploadPath);
                    }
                    //파일저장경로 없으면 만들어주기

                    Path filePath = uploadPath.resolve(savedName);

                    //**파일 경로에 저장
                    Files.copy(file.getInputStream(), filePath);


                    fileList.add(fileMap);


                    Attachment attachment = modelMapper.map(attachmentDTO, Attachment.class);

                    //** 첨부파일정보 DB에 저장
                    attachmentRepository.save(attachment);

                }

            } catch (IOException e) {
                //무슨 에러가 발생해도 파일 지워주기
                e.printStackTrace();

                int cnt = 0;
                for(int i = 0; i < fileList.size(); i++){
                    Map<String, String> file = fileList.get(i);
                    String savedFileName  = file.get("savedFileName");

                    File deleteFile = new File(savePath + "/" + savedFileName);

                    boolean isDeleted = deleteFile.delete();

                    if(isDeleted == true){
                        cnt++;
                    }
                }
                if(cnt == fileList.size()){
                    e.printStackTrace();
                }
                else{
                    e.printStackTrace();
                }
            }
        }
    }


    //한 전자결재 조회
    public ApprovalDTO selectApproval(String approvalNo){
        Approval approval = approvalRepository.findById(approvalNo);

        //기안자 정보 가져오기
        Member senderMember = memberRepository.findById(approval.getMemberId());
        //기안자의 부서 정보 가져오기
        Department senderDepart = departmentRepository.findById(senderMember.getDepartNo());
        //양식 정보 가져오기
        Form approvalForm = formRepository.findById(approval.getFormNo());

        List<ApproverDTO> approver = new ArrayList<>();
        List<ReferencerDTO> referencer = new ArrayList<>();
        List<AttachmentDTO> attachment = new ArrayList<>();


        List<Approver> approverList = approverRepository.findByApprovalId(approvalNo);
        log.info("*****selectApprovel -- approverList " + approverList);

        for(int i = 0; i < approverList.size(); i++){

            //결재자 정보 가져오기
            Member receiverMember = memberRepository.findById(approverList.get(i).getMemberId());
            //결재자 부서 정보 가져오기
            Department receiverDepart = departmentRepository.findById(receiverMember.getDepartNo());

            ApproverDTO approverDTO = new ApproverDTO(approverList.get(i).getApproverNo(), approverList.get(i).getApprovalNo(), approverList.get(i).getApproverOrder(), approverList.get(i).getApproverStatus(), approverList.get(i).getApproverDate().toString(), approverList.get(i).getMemberId(), receiverMember.getName(), receiverMember.getPositionName(), receiverDepart.getDepartName());
            approver.add(approverDTO);


        }
        log.info("*****selectApproval -- Approver List " + approver );

        List<Referencer> referencerList = referencerRepository.findByApprovalId(approvalNo);
        for(int i = 0; i < referencerList.size(); i++){

            //참조자 정보 가져오기
            Member referencerMember = memberRepository.findById(referencerList.get(i).getMemberId());
            //참조자 부서 정보 가져오기
            Department referencerDepart = departmentRepository.findById(referencerMember.getDepartNo());

            ReferencerDTO referencerDTO = new ReferencerDTO(referencerList.get(i).getRefNo(), referencerList.get(i).getApprovalNo(), referencerList.get(i).getMemberId(), referencerList.get(i).getRefOrder(), referencerMember.getName(), referencerMember.getPositionName(), referencerDepart.getDepartName());
            referencer.add(referencerDTO);
        }

        List<Attachment> attachmentList = attachmentRepository.findByApprovalId(approvalNo);
        for(int i = 0; i < attachmentList.size(); i++){
            AttachmentDTO attachmentDTO = new AttachmentDTO(attachmentList.get(i).getFileNo(), attachmentList.get(i).getFileOriname(), attachmentList.get(i).getFileSavepath(), attachmentList.get(i).getFileSavename(), attachmentList.get(i).getApprovalNo());
            attachment.add(attachmentDTO);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = approval.getApprovalDate().format(formatter);

        log.info("***** formattedDateTime ***** " + formattedDateTime);

        ApprovalDTO approvalDTO = new ApprovalDTO(approval.getApprovalNo(), approval.getMemberId(), approval.getApprovalTitle(), approval.getApprovalContent(), formattedDateTime, approval.getApprovalStatus(), approval.getRejectReason(), approval.getFormNo(), approvalForm.getFormName(), senderDepart.getDepartName(), senderMember.getName(), senderMember.getPositionName(), attachment, approver, referencer);

        return approvalDTO;
    }


    //전자결재 회수
    @Transactional
    public ApprovalDTO updateApproval(String approvalNo) {

        //수정하고자 하는 전자결재 정보 조회
        ApprovalDTO approvalDTO = selectApproval(approvalNo);

        log.info("***** 날짜 확인 ***** " + approvalDTO.getApprovalDate());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedDateTime = LocalDateTime.parse(approvalDTO.getApprovalDate(), formatter);

        Approval approval = new Approval(approvalDTO.getApprovalNo(), approvalDTO.getMemberId(), approvalDTO.getApprovalTitle(), approvalDTO.getApprovalContent(), parsedDateTime, approvalDTO.getApprovalStatus(), approvalDTO.getRejectReason(), approvalDTO.getFormNo());

        //상태 update
        approval = new ApprovalBuilder(approval).approvalStatus("회수").builder();

        //db update
        approvalRepository.update(approval);

        //DTO도 변경
        approvalDTO.setApprovalStatus(approval.getApprovalStatus());

        return approvalDTO;
    }



    //전자결재 처리(결재)
    @Transactional
    public ApproverDTO updateApprover(String approverNo, Map<String, String> statusMap){
        // DTO -> 엔티티 -> DTO

        //전자결재 번호 가져오기
        String approvalNo = approverNo.substring(0, approverNo.indexOf("_"));

        //전자결재 번호 받아서 해당 전자결재 정보 조회
        ApprovalDTO approvalDTO = selectApproval(approvalNo);

        //해당 전자결재에서 수정하고자 하는 전자결재자 정보 조회
        List<ApproverDTO> approverList = approvalDTO.getApprover();
        log.info("***** Service : approverList : " + approverList);

        ApproverDTO approverDTO = null;

        for(int i = 0; i < approverList.size(); i++)
        {
            approverDTO = approverList.get(i);

            if(approverDTO.getApproverNo().equals(approverNo) || approverDTO.getApproverNo() == approverNo){
                //가져온 결재자 번호가 존재한다면

                //날짜 변경
                Approver approver = new Approver(approverDTO.getApproverNo(), approverDTO.getApprovalNo(), approverDTO.getApproverOrder(), approverDTO.getApproverStatus(), now(), approverDTO.getMemberId());

                //승인 시 : 결재자 처리상태 변경 -> 결재자 처리 날짜 변경 -> (마지막 번호일경우) 전자결재 처리상태 변경 (승인)
                //반려 시 : 결재자 처리상태 변경 -> 결재자 처리 날짜 변경 -> 전자결재 처리상태 변경(반려) -> 전자결재 처리상태 변경(반려사유)
                String status = statusMap.get("approverStatus");

                String rejectReason = statusMap.get("rejectReason");

                //결재자 처리 상태 update
                approver = new ApproverBuilder(approver).approverStatus(status).builder();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime parsedDateTime = LocalDateTime.parse(approvalDTO.getApprovalDate(), formatter);
                Approval approval = new Approval(approvalDTO.getApprovalNo(),approvalDTO.getMemberId(),approvalDTO.getApprovalTitle(),approvalDTO.getApprovalContent(), parsedDateTime, approvalDTO.getApprovalStatus(), approvalDTO.getRejectReason(), approvalDTO.getFormNo());

                switch(status){
                    case "승인" :
                        if(i == approverList.size() -1 ){
                            //마지막 순서일 경우

                            //전자결재 처리상태 변경 (승인)
                            approval = new ApprovalBuilder(approval).approvalStatus(status).builder();
                        }
                        break;

                    case "반려" :
                        approval = new ApprovalBuilder(approval).approvalStatus(status).rejectReason(rejectReason).builder();

                        break;
                }
                //db 결재자 update
                approverRepository.update(approver);
                approverDTO.setApproverStatus(status);

                //db 결재정보 update
                approvalRepository.update(approval);
                approvalDTO.setApprovalStatus(status);

            }
        }

        return approverDTO;

    }

}
