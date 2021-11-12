package com.teamname.buildings.workplaces;

import com.teamname.exceptions.ResourcesNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkplaceService{
    private WorkplaceDAO workplaceDAO;


    public WorkplaceService(WorkplaceDAO workplaceDAO) {
        this.workplaceDAO = workplaceDAO;
    }

    public List<Workplace> getAllWorkplace() {
        return workplaceDAO.selectAllWorkplace();
    }

    public Optional<Workplace> getWorkplaceById(int id) {
        if(workplaceDAO.selectWorkplaceById(id).isEmpty()){
            throw new ResourcesNotFoundException("Workplace with id: "+ id + "is not found");
        }
        return workplaceDAO.selectWorkplaceById(id);
    }

    public void createWorkplace(Workplace workplace) {
        workplaceDAO.createWorkplace(workplace);
    }

    public void deleteWorkplace(int id){
        if(workplaceDAO.selectWorkplaceById(id).isEmpty()){
            throw new ResourcesNotFoundException("Workplace with id: "+ id + "is not found");
        }
        workplaceDAO.deleteWorkplace(id);
    }

    public void updateWorkplace(int id, Workplace workplace){
        if(workplaceDAO.selectWorkplaceById(id).isEmpty()){
            throw new ResourcesNotFoundException("Workplace with id: "+ id + "is not found");
        }
        workplaceDAO.updateWorkplace(id, workplace);
    }



}
