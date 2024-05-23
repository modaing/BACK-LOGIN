package com.insider.login.insite.dto;
import com.insider.login.department.entity.Department;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InsiteMemberDTO {

    private int memberId;

    private String name;

    private LocalDate employedDate;

    private String memberStatus;

    private Department InsiteDepartment;

}
