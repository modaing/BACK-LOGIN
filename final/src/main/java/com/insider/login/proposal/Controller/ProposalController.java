package com.insider.login.proposal.Controller;

import com.insider.login.proposal.Service.ProposalService;
import com.insider.login.proposal.DTO.ProposalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/")
@RestController
public class ProposalController {
    private ProposalService proposalService;

    @Autowired
    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @PostMapping
    public ResponseEntity<?> registSuggest(@RequestBody ProposalDTO proposalDTO) {
        Map<String, Object> result = proposalService.registSuggest(proposalDTO);
        if ((boolean) ((Map<?, ?>) result).get("result")) {
            return ResponseEntity.status(HttpStatus.CREATED).body("건의 등록 성공");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("건의 등록 실패");
        }
    }

    @PutMapping("/{proposaltNo}")
    public ResponseEntity<?> modifySuggest(@PathVariable int proposalNo, @RequestBody ProposalDTO proposalDTO) {
        proposalService.modifySuggest(proposalNo, proposalDTO);
        return ResponseEntity.ok("건의 수정 성공");
    }

    @DeleteMapping("/{proposalNo}")
    public ResponseEntity<?> deleteById(@PathVariable int proposalNo) {
        proposalService.deleteById(proposalNo);
        return ResponseEntity.ok("건의 삭제 성공");
    }

    @GetMapping("/{proposalNo}")
    public ResponseEntity<?> getSuggestBySuggestNo(@PathVariable int proposalNo) {
        return ResponseEntity.ok(proposalService.getSuggestBySuggestNo(proposalNo));
    }

}

