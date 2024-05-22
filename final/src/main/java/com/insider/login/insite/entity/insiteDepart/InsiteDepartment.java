package com.insider.login.insite.entity.insiteDepart;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@Table(name = "department_info")
@ToString
public class InsiteDepartment {

    @Id
    @Column(name = "depart_no", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departNo;

    @Column(name = "depart_name", nullable = false)
    private String departName;

    protected InsiteDepartment() {
    }


}
