package com.insider.login.proposal.Service;

import com.insider.login.member.entity.Member;
import com.insider.login.member.repository.MemberRepository;
import com.insider.login.proposal.DTO.ProposalDTO;
import com.insider.login.proposal.Entity.Proposal;
import com.insider.login.proposal.Repository.ProposalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProposalService {

    private final ProposalRepository proposalRepository;

    private final MemberRepository memberInfoRepository;
    private Long Id;

    @Autowired
    public ProposalService(ProposalRepository proposalRepository, MemberRepository memberInfoRepository) {
        this.proposalRepository = proposalRepository;
        this.memberInfoRepository = memberInfoRepository;
    }

    @Transactional
    public Proposal registerProposal(Proposal proposal) {
        // member_id 값이 member_info 테이블에 존재하는지 확인
        Optional<Member> memberInfoOptional = memberInfoRepository.findById(proposal.getMemberId());
        if (memberInfoOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid member_id: " + proposal.getMemberId());
        }

        // proposal 저장
        return proposalRepository.save(proposal);
    }

    public Map<String, Object> deleteProposal(Long proposalId) {
        Map<String, Object> result = new HashMap<>();

        try {
            proposalRepository.deleteById((long) proposalId);
            result.put("result", true);
        } catch (Exception e) {
            result.put("result", false);
        }

        return result;
    }

    public Proposal getProposalByProposalNo(int proposalNo) {
        return proposalRepository.findById((long) proposalNo).orElse(null);
    }

    public void updateProposal(int proposalNo, ProposalDTO proposalDTO) {
        Optional<Proposal> optionalProposal = proposalRepository.findById((long) proposalNo);
        optionalProposal.ifPresent(proposal -> {
            if (proposalDTO.getContent() != null) {
                proposal.setContent(proposalDTO.getContent());
            }
            if (proposalDTO.getProposalDate() != null) {
                proposal.setProposalDate(proposalDTO.getProposalDate());
            }
            proposalRepository.save(proposal);
        });
    }


    public Proposal retrieveProposalByProposalNo(int proposalNo) {
        return proposalRepository.findById((long) proposalNo).orElse(null);
    }

    public void deleteById(int proposalNo) {
        proposalRepository.deleteById((long) proposalNo);
    }


    public Optional<Proposal> findProposalByProposalNo(int proposalNo) {
        return proposalRepository.findById((long) proposalNo);
    }

    public Proposal modifyProposal(Long proposalNo, ProposalDTO proposalDTO) {
        Optional<Proposal> optionalProposal = proposalRepository.findById(proposalNo);
        return optionalProposal.map(proposal -> {
            if (proposalDTO.getContent() != null) {
                proposal.setContent(proposalDTO.getContent());
            }
            if (proposalDTO.getProposalDate() != null) {
                proposal.setProposalDate(proposalDTO.getProposalDate());
            }
            return proposalRepository.save(proposal);
        }).orElse(null);
    }

    public Page<ProposalDTO> getProposalList(UserDetails user, Pageable pageable) {
        // 사용자 정보를 기반으로 건의사항 목록을 조회하여 페이지로 반환
        return proposalRepository.findById(Id, pageable);
    }

    public ProposalDTO registerProposal(ProposalDTO proposalDTO) {
        Proposal proposal = mapToEntity(proposalDTO);
        Proposal savedProposal = proposalRepository.save(proposal);
        return mapToDto(savedProposal);
    }

    private Proposal mapToEntity(ProposalDTO proposalDTO) {
        Proposal proposal = new Proposal();
        proposal.setContent(proposalDTO.getContent());
        proposal.setMemberId(proposalDTO.getMemberId());
        proposal.setProposalDate(proposalDTO.getProposalDate());
        return proposal;
    }

    private ProposalDTO mapToDto(Proposal proposal) {
        ProposalDTO proposalDTO = new ProposalDTO();
        proposalDTO.setId(proposal.getId());
        proposalDTO.setContent(proposal.getContent());
        proposalDTO.setMemberId(proposal.getMemberId());
        proposalDTO.setProposalDate(proposal.getProposalDate());
        return proposalDTO;
    }




}

