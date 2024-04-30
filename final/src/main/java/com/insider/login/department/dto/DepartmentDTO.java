package com.insider.login.department.dto;

import com.insider.login.member.dto.MemberDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DepartmentDTO {

    private int departNo;               // 부서 번호
    private String departName;          // 부서명
    private List<MemberDTO> members;    // 구성원 리스트

    public DepartmentDTO(int departNo, String departName) {
        this.departNo = departNo;
        this.departName = departName;
    }
}
