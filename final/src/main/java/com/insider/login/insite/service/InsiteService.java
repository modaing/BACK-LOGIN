package com.insider.login.insite.service;

import com.insider.login.insite.repository.InsiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsiteService {

    private final InsiteRepository insiteRepository;

    public List<Object[]> selectDepartmentMemberCounts() {

        return insiteRepository.selectDepartmentMemberCounts();
    }
}
