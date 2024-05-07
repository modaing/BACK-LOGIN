package com.insider.login.proposal.ControllerTest;

import com.insider.login.proposal.Service.ProposalService;
import com.insider.login.proposal.DTO.ProposalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/suggests")
public class ProposalControllerTest {

    private final ProposalService proposalService;

    @Autowired
    public ProposalControllerTest(ProposalService proposalService) {
        this.proposalService = proposalService;
    }


    @PostMapping("/proposal")
    public Map<String, Object> RegistProposal(ProposalDTO proposalDTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("result", true);
        } catch (Exception e) {
            // 건의 등록에 실패
            result.put("result", false);
        }
        return result;
    }


    @PutMapping("/{proposalNo}")
    public ResponseEntity<?> modifySuggest(@PathVariable int proposalNo, @RequestBody ProposalDTO proposalDTO) {
        proposalService.modifyProposal(proposalNo, proposalDTO);
        return ResponseEntity.ok("건의 수정 성공");
    }

    @DeleteMapping("/{proposalNo}")
    public ResponseEntity<?> deleteById(@PathVariable int proposalNo) {
        proposalService.deleteById(proposalNo);
        return ResponseEntity.ok("건의 삭제 성공");
    }

    @GetMapping("/{proposalNo}")
    public ResponseEntity<?> getSuggestBySuggestNo(@PathVariable int proposalNo) {
        return ResponseEntity.ok(proposalService.getSuggestByProposalNo(proposalNo));
    }
}
