package com.insider.login.insite.entity;

import com.insider.login.insite.entity.insiteDepart.InsiteDepartment;
import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "member_info")
@ToString
public class InsiteMember {

    @Id
    @Column(name = "member_id", nullable = false)
    private int memberId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "employed_date", nullable = false)
    private LocalDate employedDate;

    @Column(name = "member_status", nullable = false)
    private String memberStatus;

    @ManyToOne
    @JoinColumn(name = "depart_no", referencedColumnName = "depart_no")
    private InsiteDepartment department;

    @Column(name = "gender", nullable = true)
    private String gender;

    @Column(name = "birthday", nullable = true)
    private LocalDate birthday;


    protected InsiteMember () {}

}
