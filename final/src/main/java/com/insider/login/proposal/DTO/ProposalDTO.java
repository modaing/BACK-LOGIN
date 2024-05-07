package com.insider.login.proposal.DTO;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProposalDTO {

    private int suggestId;          // 건의 아이디
    private String title;           // 건의 제목
    private String content;         // 건의 내용
    private int memberId;           // 작성자 사번
    private LocalDate suggestDate;  // 건의 등록일자
}
