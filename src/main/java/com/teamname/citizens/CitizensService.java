package com.teamname.citizens;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitizensService {
    private CitizensDAO citizensDAO;

    @Autowired
    public CitizensService(CitizensDAO citizensDAO) {
        this.citizensDAO = citizensDAO;
    }
}
