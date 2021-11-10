package com.teamname.allotments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllotmentService {
    private AllotmentDAO allotmentDAO;

    @Autowired
    public AllotmentService(AllotmentDAO allotmentDAO) {
        this.allotmentDAO = allotmentDAO;
    }
}
