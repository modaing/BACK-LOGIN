package com.insider.login.proposal.ServiceTest;

import com.insider.login.proposal.Service.ProposalService;
import com.insider.login.proposal.DTO.ProposalDTO;
import com.insider.login.proposal.Entity.Proposal;
import com.insider.login.proposal.Repository.ProposalRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ProposalServiceTest {

    @Test
    public void testInsertProposal() {
        // Mock 객체 생성
        ProposalRepository proposalRepository = mock(ProposalRepository.class);
        ModelMapper modelMapper = mock(ModelMapper.class);

        ProposalService proposalService = new ProposalService(modelMapper, proposalRepository);

        // 테스트용 DTO 생성
        ProposalDTO proposalDTO = new ProposalDTO();
        proposalDTO.setTitle("Test Title");
        proposalDTO.setContent("Test Content");
        proposalDTO.setSuggestDate(LocalDate.of(2024, 5, 3));

        // 테스트용 Proposal 생성
        Proposal proposal = new Proposal();
        proposal.setTitle("Test Title");
        proposal.setContent("Test Content");
        proposal.setSuggestDate(LocalDate.of(2024, 5, 3));

        // Mock의 save 메서드가 호출될 때 테스트용 Proposal을 반환하도록 설정
        when(modelMapper.map(proposalDTO, Proposal.class)).thenReturn(proposal);

        // 테스트용 Proposal을 저장하고 결과 확인
        Map<String, Object> result = proposalService.registpro(proposalDTO);


        // 결과 확인
        assertArg(true, result.get("result"));
        // save 메서드가 1회 호출되었는지 확인
        verify(proposalRepository, times(1)).save(proposal);
    }

    private void assertArg(boolean b, Object result) {
    }

    public void regiProposal(ProposalDTO proposalDTO) {
    }

    public void modifyProposal(int suggestNo, ProposalDTO proposalDTO) {
    }

    public void deleteById(int suggestNo) {
    }

    public Object getSuggestByProposalNo(int suggestNo) {
        return null;
    }

    // 다른 테스트 메서드들을 추가할 수 있어요.
}
