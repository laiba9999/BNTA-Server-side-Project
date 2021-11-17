package com.teamname.allotments;

import com.teamname.exceptions.ResourcesNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllotmentService {
    private AllotmentDAO allotmentDAO;

    @Autowired
    public AllotmentService(AllotmentDAO allotmentDAO) {
        this.allotmentDAO = allotmentDAO;
    }

    public List<Allotment> getAllAllotments() {
        return allotmentDAO.selectAllAllotments();
    }

    public Optional<Allotment> getAllotmentById(int id) {
        if (allotmentDAO.selectAllotmentById(id).isEmpty()){
            throw new ResourcesNotFoundException("Allotment with the id " + id + " not found");
        }
        return allotmentDAO.selectAllotmentById(id);
    }

    public void createAllotment(Allotment allotment) {
        allotmentDAO.createAllotment(allotment);
    }


}
