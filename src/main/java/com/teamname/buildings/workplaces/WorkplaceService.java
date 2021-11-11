package com.teamname.buildings.workplaces;

import com.teamname.allotments.Allotment;
import com.teamname.allotments.AllotmentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkplaceService {

    private WorkplaceDAO workplaceDAO;

    @Autowired
    public WorkplaceService(WorkplaceDAO workplaceDAO) {
        this.workplaceDAO = workplaceDAO ;
    }

    public List<Workplace> getAllWorkplace() {
        return workplaceDAO.selectAllWorkplace();
    }

    public Optional<Workplace> getWorkplaceById(int id) {
        return workplaceDAO.selectWorkplaceById(id);
    }

    public void createWorkplace(Workplace workplace) {
        workplaceDAO.createWorkplace(workplace);
    }

    public void deleteWorkplace(int id){
        workplaceDAO.deleteWorkplace(id);
    }

    public void updateWorkplace(int id, Workplace workplace){
        workplaceDAO.updateWorkplace(id, workplace);
    }



}
