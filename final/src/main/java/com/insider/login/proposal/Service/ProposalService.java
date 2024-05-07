package com.insider.login.proposal.Service;

import com.insider.login.proposal.DTO.ProposalDTO;
import com.insider.login.proposal.Entity.Proposal;
import com.insider.login.proposal.Repository.ProposalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProposalService {

    private final ModelMapper modelMapper;
    private final ProposalRepository proposalRepository;
    private ProposalDTO proposalDTO;

    @Autowired
    public ProposalService(ModelMapper modelMapper, ProposalRepository proposalRepository) {
        this.modelMapper = modelMapper;
        this.proposalRepository = proposalRepository;
    }

    // 건의 등록
    public Map<String, Object> registProposal(ProposalDTO proposalDTO) {
        this.proposalDTO = proposalDTO;
        Map<String, Object> result = new HashMap<>();
        try {
            Proposal proposal = modelMapper.map(proposalDTO, Proposal.class);
            proposal.setDate(LocalDate.now()); // 현재 날짜로 등록일자 설정
            proposalRepository.save(proposal); // proposalRepository를 통해 저장
            result.put("result", true);
        } catch (Exception e) {
            result.put("result", false);
        }
        return result;
    }

    // 건의 삭제
    public Map<String, Object> deleteSuggest(int suggestId) {
        Map<String, Object> result = new HashMap<>();
        try {
            proposalRepository.deleteById(suggestId);
            result.put("result", true);
        } catch (Exception e) {
            result.put("result", false);
        }
        return result;
    }

    // 건의 상세 조회
    public Optional<Proposal> findSuggestBySuggestId(int suggestId) {
        return proposalRepository.findById(suggestId);
    }

    // 건의 수정
    public Map<String, Object> modifySuggest(int suggestId, ProposalDTO proposalDTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            Optional<Proposal> optionalSuggest = proposalRepository.findById(suggestId);
            if (optionalSuggest.isPresent()) {
                Proposal proposal = optionalSuggest.get();

                // SuggestDTO에서 값을 추출하여 Suggest 객체에 반영
                if (proposalDTO.getTitle() != null) {
                    proposal.setTitle(proposalDTO.getTitle());
                }
                if (proposalDTO.getContent() != null) {
                    proposal.setContent(proposalDTO.getContent());
                }
                if (proposalDTO.getSuggestDate() != null) {
                    proposal.setSuggestDate(proposalDTO.getSuggestDate());
                }
                // 필요한 만큼 위와 같은 방식으로 다른 필드에 대해서도 처리

                // 변경된 Suggest 객체를 데이터베이스에 저장
                proposalRepository.save(proposal);

                // 수정 성공 여부를 결과 맵에 추가
                result.put("result", true);
            } else {
                result.put("result", false);
            }
        } catch (Exception e) {
            result.put("result", false);
        }
        return result;
    }

    // 아직 미구현된 메서드들
    public Map<String, Object> registSuggest(ProposalDTO proposalDTO) {
        // 구현 필요
        return null;
    }

    public Object getSuggestBySuggestNo(int suggestNo) {
        // 구현 필요
        return null;
    }

    public void deleteById(int suggestNo) {
        // 구현 필요
    }

    public void modifyProposal(int suggestNo, ProposalDTO proposalDTO) {
    }

    public Object getSuggestByProposalNo(int suggestNo) {
        return null;
    }

    public Map<String, Object> registpro(ProposalDTO proposalDTO) {
        return null;
    }
}
