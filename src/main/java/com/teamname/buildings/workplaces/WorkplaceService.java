package com.teamname.buildings.workplaces;

import com.teamname.buildings.Building;
import com.teamname.buildings.BuildingService;
import com.teamname.citizens.Citizen;
import com.teamname.citizens.CitizenDAO;
import com.teamname.exceptions.NotModifiedException;
import com.teamname.exceptions.ResourcesNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkplaceService{
    private WorkplaceDAO workplaceDAO;
    private BuildingService buildingService;
    private CitizenDAO citizenDAO;

    public WorkplaceService(WorkplaceDAO workplaceDAO, BuildingService buildingService, CitizenDAO citizenDAO) {
        this.workplaceDAO = workplaceDAO;
        this.buildingService = buildingService;
        this.citizenDAO = citizenDAO;
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
            if (building.getAllotment_id().equals(workplace.getAllotment_id())) {
                throw new IllegalStateException("Allotment "+workplace.getAllotment_id()+" already has a building on it");
            }
        }
        workplaceDAO.createWorkplace(workplace);
    }

    public void deleteWorkplace(int id){
        if(workplaceDAO.selectWorkplaceById(id).isEmpty()){
            throw new ResourcesNotFoundException("Workplace with id: "+ id + " is not found");
        }
        workplaceDAO.deleteWorkplace(id);
    }

    public void updateWorkplace(int id, Workplace updatedWorkplace) {
        if(workplaceDAO.selectWorkplaceById(id).isEmpty()){
            throw new ResourcesNotFoundException("Workplace with id "+id+" doesn't exist!");
        }

        if ((updatedWorkplace.getBuildingName() == null || updatedWorkplace.getBuildingName().length() == 0)
                && updatedWorkplace.getCapacity() == null) {
            throw new IllegalStateException("No content"); // Implementation not complete
        }

        Optional<Workplace> oldWorkplace = workplaceDAO.selectWorkplaceById(id);

        updatedWorkplace.setId(oldWorkplace.get().getId());
        updatedWorkplace.setAllotment_id(oldWorkplace.get().getAllotment_id());
        if (Optional.of(updatedWorkplace).equals(oldWorkplace)) {
            throw new NotModifiedException("No modifications made to workplace with id " + id);
        }


        if (updatedWorkplace.getBuildingName() == null || updatedWorkplace.getBuildingName().length() == 0) {
            updatedWorkplace.setBuildingName(oldWorkplace.get().getBuildingName());
        }
        if (updatedWorkplace.getCapacity() == null) {
            updatedWorkplace.setCapacity(oldWorkplace.get().getCapacity());
        } else {
            Integer citizenCount = 0;
            for (Citizen citizenInDatabase : citizenDAO.selectAllCitizens()) {
                if (citizenInDatabase.getHouse_id().equals(id)) {
                    citizenCount++;
                }
            }
            if (citizenCount > updatedWorkplace.getCapacity()) {
                throw new IllegalStateException("Reduced capacity less than number of citizens in workplace. " +
                        "Number of citizens is " + citizenCount);
            }
        }

        workplaceDAO.updateWorkplace(id, updatedWorkplace);
    }



}
