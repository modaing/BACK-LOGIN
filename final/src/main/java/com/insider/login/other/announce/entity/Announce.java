package com.insider.login.other.announce.entity;

import com.insider.login.other.announce.dto.AnnounceDTO;
import com.insider.login.other.announce.service.AnnounceService;
import com.insider.login.other.note.dto.NoteDTO;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "ANNOUNCE")
@Getter
public class Announce {

    @Id
    @Column(name = "anc_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ancNo;

    @Column(name = "anc_title")
    private String ancTitle;

    @Column(name = "anc_content")
    private String ancContent;

    @Column(name = "anc_date")
    private String ancDate;

    @Column(name = "anc_writer")
    private String ancWriter;

    @Column(name = "hits")
    private int hits;

    protected Announce() {}

    public Announce(AnnounceDTO announceDTO) {
        this.ancNo = announceDTO.getAncNo();
        this.ancTitle = announceDTO.getAncTitle();
        this.ancContent = announceDTO.getAncContent();
        this.ancDate = announceDTO.getAncDate();
        this.ancWriter = announceDTO.getAncWriter();
        this.hits = announceDTO.getHits();
    }



}
