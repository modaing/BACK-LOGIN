package com.insider.login.commute.service;

import com.insider.login.commute.dto.CommuteDTO;
import com.insider.login.commute.entity.Commute;
import com.insider.login.commute.repository.CommuteRepository;
import org.springframework.stereotype.Service;

@Service
public class CommuteService {

    private CommuteRepository commuteRepository;

    public CommuteService(CommuteRepository commuteRepository) {
        this.commuteRepository = commuteRepository;
    }

    public void insertTimeOfCommute(CommuteDTO newCommute) {
        Commute startWork = new Commute(
                newCommute.getMemberId(),
                newCommute.getWorkingDate(),
                newCommute.getStartWork(),
                newCommute.getEndWork(),
                newCommute.getWorkingStatus(),
                newCommute.getTotalWorkingHours()
        );

        commuteRepository.save(startWork);
    }
}
