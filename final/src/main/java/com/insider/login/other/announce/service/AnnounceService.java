package com.insider.login.other.announce.service;

import com.insider.login.config.YmlConfig;
import com.insider.login.other.announce.dto.AncFileDTO;
import com.insider.login.other.announce.dto.AnnounceDTO;
import com.insider.login.other.announce.entity.AncFile;
import com.insider.login.other.announce.entity.Announce;
import com.insider.login.other.announce.repository.AnnounceFileRepository;
import com.insider.login.other.announce.repository.AnnounceRepository;
import com.insider.login.other.note.entity.Note;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.io.FileUtils.getFile;

@Service
@Slf4j
@AllArgsConstructor
public class AnnounceService {

    private final AnnounceRepository announceRepository;

    private final AnnounceFileRepository announceFileRepository;

    private final ModelMapper modelMapper;

    private final YmlConfig ymlConfig;


    /** 파일과 공지사항 모두 있을 때 */
    public Map<String, Object> insertAncWithFile(AnnounceDTO ancDTO, List<MultipartFile> files) {

        Map<String, Object> result = new HashMap<>();

        try {
            // 공지사항 저장
            Announce announce = modelMapper.map(ancDTO, Announce.class);
            Announce savedAnnounce = announceRepository.save(announce);

            // 저장된 공지사항의 ancNo 가져오기
            int ancNo = savedAnnounce.getAncNo();


            // files가 null이 아닌 경우에만 파일 저장 수행
            if (files != null) {
                for (MultipartFile file : files) {
                    // 파일 저장
                    String fileName = file.getOriginalFilename();
                    String fileType = file.getContentType();
                    String uploadDirectory = ymlConfig.getDirectory();
                    String filePath = uploadDirectory + fileName;   // 파일을 저장할 경로 지정

                    File newFile = new File(filePath);
                    file.transferTo(newFile);

                    // 파일 정보를 AncFileDTO로 생성
                    AncFileDTO ancFileDTO = new AncFileDTO();
                    ancFileDTO.setFileName(fileName);
                    ancFileDTO.setFileType(fileType);
                    ancFileDTO.setFilePath(filePath);
                    ancFileDTO.setAncNo(ancNo);

                    // AncFileDTO를 엔티티로 변환하여 저장
                    AncFile ancFile = modelMapper.map(ancFileDTO, AncFile.class);
                    announceFileRepository.save(ancFile);
                }
            }

            result.put("result", true);
        } catch (Exception e) {
            log.error("Error while inserting Announce with Files: " + e.getMessage());
            result.put("result", false);
        }

        return result;
    }

    /** 파일 없을 때 */
    public Map<String, Object> insertAnc(AnnounceDTO announceDTO) {

        Map<String, Object> result = new HashMap<>();

        try {
            Announce announce = modelMapper.map(announceDTO, Announce.class);
            announceRepository.save(announce);

            result.put("result", true);
        } catch (Exception e) {

            log.error(e.getMessage());
            result.put("result", false);
        }
        return result;

    }
}
