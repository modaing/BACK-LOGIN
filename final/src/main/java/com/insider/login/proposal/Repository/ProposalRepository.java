package com.insider.login.proposal.Repository;

import com.insider.login.proposal.Entity.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Integer> {

    Page<Proposal> findByMemberIdAndDeleteYn(int memberId, Pageable pageable, String deleteYn);

    Page<Proposal> findByReceiverIdAndDeleteYn(int memberId, Pageable pageable, String deleteYn);

    // 수정된 메서드 이름
    Optional<Proposal> findBySuggestionId(int suggestionId);

    void deleteById(int suggestionId);

}
