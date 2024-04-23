package com.insider.login.commute.service;

import com.insider.login.commute.repository.CommuteRepository;
import org.springframework.stereotype.Service;

@Service
public class CommuteService {

    private CommuteRepository commuteRepository;

    public CommuteService(CommuteRepository commuteRepository) {
        this.commuteRepository = commuteRepository;
    }
}
