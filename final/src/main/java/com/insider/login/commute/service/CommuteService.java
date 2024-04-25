package com.insider.login.commute.service;

import com.insider.login.commute.dto.CommuteDTO;
import com.insider.login.commute.dto.UpdateTimeOfCommuteDTO;
import com.insider.login.commute.entity.Commute;
import com.insider.login.commute.repository.CommuteRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

@Service
@Slf4j
public class CommuteService {

    private final CommuteRepository commuteRepository;

    private final ModelMapper modelMapper;

    public CommuteService(CommuteRepository commuteRepository, ModelMapper modelMapper) {
        this.commuteRepository = commuteRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void insertTimeOfCommute(CommuteDTO newCommute) {

        /* 방법 1 */
        /*
        log.info("[CommuteService] insertTimeOfCommute");
        log.info("[CommuteService] CommuteDTO : " + newCommute);
        try {
            Commute startWork = new Commute(
                    newCommute.getMemberId(),
                    newCommute.getWorkingDate(),
                    newCommute.getStartWork(),
                    newCommute.getEndWork(),
                    newCommute.getWorkingStatus(),
                    newCommute.getTotalWorkingHours()
            );

            commuteRepository.save(startWork);

        } catch (Exception e) {
            log.info("[insertCommute] Exception");
        }

        log.info("[CommuteService] insertTimeOfCommute End ===========");
         */

        /* 방법 2 */
        commuteRepository.save(modelMapper.map(newCommute, Commute.class));
    }


    public void updateTimeOfCommuteByCommuteNo(UpdateTimeOfCommuteDTO updateTimeOfCommute) {

        int commuteNo = updateTimeOfCommute.getCommuteNo();
        Commute commute = commuteRepository.findByCommuteNo(commuteNo);

//        if(commute != null) {
//            commute.setEndWork
//        }

    }
}
