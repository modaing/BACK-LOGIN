package com.insider.login.other.announce.entity;

import com.insider.login.other.announce.dto.AnnounceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "ANNOUNCE_FILES")
@AllArgsConstructor
@Getter
public class AncFile {

    @Id
    @Column(name = "file_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileNo;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_path")
    private String filePath;

    @JoinColumn(name = "anc_no")
    private int ancNo;


    protected AncFile() {}


}
