package com.insider.login.other.announce.service;

import com.insider.login.common.CommonController;
import com.insider.login.other.announce.dto.AncFileDTO;
import com.insider.login.other.announce.dto.AnnounceDTO;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class AnnounceServiceTests {

    @Autowired
    private AnnounceService announceService;

    @Test
    @DisplayName("공지사항 insert 테스트")
    public void insertAnnounce() throws IOException {

        // 테스트할 파일 생성
        // given
        File imageFile = new File(getClass().getClassLoader().getResource("jjang-gu.png").getFile());
        FileInputStream input = new FileInputStream(imageFile);
        MultipartFile multipartFile = new MockMultipartFile("test.png", imageFile.getName(), "image/png", IOUtils.toByteArray(input));

        AnnounceDTO announceDTO = new AnnounceDTO();
        announceDTO.setAncTitle("김제목 김제목");
        announceDTO.setAncWriter("김덕배");
        announceDTO.setAncContent("성공의 지름길");
        announceDTO.setHits(0);
        announceDTO.setAncDate("2020-2020-2020");

        // when
        Map<String, Object> result = new HashMap<>();

        try {
            List<MultipartFile> files = new ArrayList<>();
            files.add(multipartFile);
            announceService.insertAncWithFile(announceDTO, files);
            result.put("result", true);
        } catch (Exception e) {
            result.put("result", false);
        }

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.get("result"), true);
    }

}
