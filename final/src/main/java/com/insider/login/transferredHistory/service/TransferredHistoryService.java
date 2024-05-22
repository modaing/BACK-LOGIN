package com.insider.login.transferredHistory.service;

import com.insider.login.member.entity.Member;
import com.insider.login.transferredHistory.dto.TransferredHistoryDTO;
import com.insider.login.transferredHistory.entity.TransferredHistory;
import com.insider.login.transferredHistory.repository.TransferredHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferredHistoryService {

    private final ModelMapper modelMapper;
    private final TransferredHistoryRepository transferredHistoryRepository;

    public TransferredHistoryService (ModelMapper modelMapper, TransferredHistoryRepository transferredHistoryRepository) {
        this.modelMapper = modelMapper;
        this.transferredHistoryRepository = transferredHistoryRepository;
    }

    @Transactional
    public void saveHistory(Member savedMember) {
        /* 회원 등록을 하면서 인사 발령 내역에다가도 저장 하는 logic */
        System.out.println("save transfer history: " + savedMember);
        TransferredHistoryDTO transferredHistoryDTO = modelMapper.map(savedMember, TransferredHistoryDTO.class);
        transferredHistoryDTO.setTransferredDate(savedMember.getEmployedDate());
        transferredHistoryDTO.setNewDepartNo(savedMember.getDepartment().getDepartNo());
        transferredHistoryDTO.setNewPositionName(savedMember.getPosition().getPositionName());
        System.out.println("transferredHistory에 저장될 정보들: " + transferredHistoryDTO);
        TransferredHistory transferredHistory = modelMapper.map(transferredHistoryDTO, TransferredHistory.class);
        transferredHistoryRepository.save(transferredHistory);
    }

    public String calculateDateDifference(int transferredNo) {
        TransferredHistory transferredHistory = transferredHistoryRepository.findById(transferredNo).orElseThrow(() -> new EntityNotFoundException("인사 발령내역 조회에 실패하였습니다."));

        LocalDate transferredDate = transferredHistory.getTransferredDate();
        LocalDate currentDate = LocalDate.now();
        long monthsDifference = ChronoUnit.MONTHS.between(transferredDate, currentDate);
        long yearsDifference = ChronoUnit.YEARS.between(transferredDate, currentDate);

        String result;
        if (yearsDifference >= 1) {
            result = yearsDifference + "년 " + monthsDifference + "월";
        } else {
            result = monthsDifference + "월";
        }
        return result;
    }

    public List<TransferredHistoryDTO> getTransferredHistoryRecord(int memberId) {
        List<TransferredHistory> transferredHistoryList = transferredHistoryRepository.findByMemberId(memberId);

        // Map TransferredHistory objects to TransferredHistoryDTO objects
        List<TransferredHistoryDTO> transferredHistoryDTOList = transferredHistoryList.stream()
                .map(transferredHistory -> modelMapper.map(transferredHistory, TransferredHistoryDTO.class))
                .collect(Collectors.toList());
        return transferredHistoryDTOList;
    }

    @Transactional
    public void updateTransferredHistory(TransferredHistoryDTO firstTransferredHistoryDTO) {
        List<TransferredHistory> transferredHistoryList = transferredHistoryRepository.findByMemberId(firstTransferredHistoryDTO.getMemberId());

        if (!transferredHistoryList.isEmpty()) {
            TransferredHistory firstTransferredHistory = transferredHistoryList.get(0);

            firstTransferredHistory.setTransferredDate(firstTransferredHistoryDTO.getTransferredDate());

            transferredHistoryRepository.save(firstTransferredHistory);
        }
    }
}
