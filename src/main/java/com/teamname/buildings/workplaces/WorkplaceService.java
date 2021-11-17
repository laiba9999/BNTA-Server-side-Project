package com.teamname.buildings.workplaces;

import com.teamname.allotments.AllotmentService;
import com.teamname.buildings.Building;
import com.teamname.buildings.BuildingService;
import com.teamname.exceptions.NotModifiedException;
import com.teamname.exceptions.ResourcesNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkplaceService{
    private WorkplaceDAO workplaceDAO;
    private BuildingService buildingService;
    private AllotmentService allotmentService;

    public WorkplaceService(WorkplaceDAO workplaceDAO, BuildingService buildingService, AllotmentService allotmentService) {
        this.workplaceDAO = workplaceDAO;
        this.buildingService = buildingService;
        this.allotmentService = allotmentService;
    }

    public List<Workplace> getAllWorkplace() {
        return workplaceDAO.selectAllWorkplace();
    }

    public Optional<Workplace> getWorkplaceById(int id) {
        if(workplaceDAO.selectWorkplaceById(id).isEmpty()){
            throw new ResourcesNotFoundException("Workplace with id: "+ id + " is not found");
        }
        return workplaceDAO.selectWorkplaceById(id);
    }

    public void createWorkplace(Workplace workplace) {
        //does workplace exist
        for (Building building : buildingService.getAllBuildings()) {
            if (building.getAllotment_id() == workplace.getAllotment_id()) {
                throw new IllegalStateException("Allotment "+workplace.getAllotment_id()+" already has a building on it");
            }
            allotmentService.getAllotmentById(workplace.getAllotment_id());
        }
        workplaceDAO.createWorkplace(workplace);
    }

    public void deleteWorkplace(int id){
        if(workplaceDAO.selectWorkplaceById(id).isEmpty()){
            throw new ResourcesNotFoundException("Workplace with id: "+ id + " is not found");
        }
        workplaceDAO.deleteWorkplace(id);
    }

    public void updateWorkplace(int id, Workplace updatedWorkplace){
        if(workplaceDAO.selectWorkplaceById(id).isEmpty()){
            throw new ResourcesNotFoundException("Workplace with id: "+ id + " is not found");
        }
        Optional<Workplace> oldWorkplace = workplaceDAO.selectWorkplaceById(id);
        boolean update = false;

        if (oldWorkplace.equals(Optional.of(updatedWorkplace))){
            throw new NotModifiedException("No modifications made to workplace with id " + id);
        }
        if (updatedWorkplace.getBuildingName() == null) {
            updatedWorkplace.setBuildingName(oldWorkplace.get().getBuildingName());
        }
        if (updatedWorkplace.getCapacity() == null) {
            updatedWorkplace.setCapacity(oldWorkplace.get().getCapacity());
        }

        workplaceDAO.updateWorkplace(id, updatedWorkplace);
    }



}
