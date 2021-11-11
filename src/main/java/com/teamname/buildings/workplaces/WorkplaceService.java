package com.teamname.buildings.workplaces;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkplaceService {
    private WorkplaceDAO workplaceDAO;


    public WorkplaceService(WorkplaceDAO workplaceDAO) {
        this.workplaceDAO = workplaceDAO;
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
