package com.insider.login.member.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DepartmentDTO {

    private int departNo;               // 부서 번호
    private String departName;          // 부서명
    private List<MemberDTO> memberList; // 구성원 리스트

}
