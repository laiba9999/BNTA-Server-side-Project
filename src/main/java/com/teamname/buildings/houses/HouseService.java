package com.teamname.buildings.houses;

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
public class HouseService {
    private HouseDAO houseDAO;
    private BuildingService buildingService;
    private CitizenDAO citizenDAO;

    public HouseService(HouseDAO houseDAO, BuildingService buildingService, CitizenDAO citizenDAO) {
        this.houseDAO = houseDAO;
        this.buildingService = buildingService;
        this.citizenDAO = citizenDAO;
    }


    public List<House> getAllHouses() {
        return houseDAO.selectAllHouses();
    }

    public Optional<House> getHouseById(int id) {
        if(houseDAO.selectHouseById(id).isEmpty()){
            throw new ResourcesNotFoundException("House with id "+id+" doesn't exist!");
        }
        return houseDAO.selectHouseById(id);
    }

    public void createHouse(House house) {
        for (Building building: buildingService.getAllBuildings()){
            if (building.getAllotment_id().equals(house.getAllotment_id())){
                throw new IllegalStateException("Allotment "+house.getAllotment_id()+" already has a building on it");
            }
        }
        houseDAO.createHouse(house);
    }

    public void deleteHouse(int id) {
        if(houseDAO.selectHouseById(id).isEmpty()){
            throw new ResourcesNotFoundException("House with id "+id+" doesn't exist!");
        }
        houseDAO.deleteHouse(id);
    }

    public void updateHouse(int id, House updatedHouse) {
        if(houseDAO.selectHouseById(id).isEmpty()){
            throw new ResourcesNotFoundException("House with id "+id+" doesn't exist!");
        }

        if ((updatedHouse.getBuildingName() == null || updatedHouse.getBuildingName().length() == 0)
                && updatedHouse.getCapacity() == null) {
            throw new IllegalStateException("No content"); // Implementation not complete
        }

        Optional<House> oldHouse = houseDAO.selectHouseById(id);

        updatedHouse.setId(oldHouse.get().getId());
        updatedHouse.setAllotment_id(oldHouse.get().getAllotment_id());
        if (Optional.of(updatedHouse).equals(oldHouse)) {
            throw new NotModifiedException("No modifications made to house with id " + id);
        }


        if (updatedHouse.getBuildingName() == null || updatedHouse.getBuildingName().length() == 0) {
            updatedHouse.setBuildingName(oldHouse.get().getBuildingName());
        }
        if (updatedHouse.getCapacity() == null) {
            updatedHouse.setCapacity(oldHouse.get().getCapacity());
        } else {
            Integer citizenCount = 0;
            for (Citizen citizenInDatabase : citizenDAO.selectAllCitizens()) {
                if (citizenInDatabase.getHouse_id().equals(id)) {
                    citizenCount++;
                }
            }
            if (citizenCount > updatedHouse.getCapacity()) {
                throw new IllegalStateException("Reduced capacity less than number of citizens in house. " +
                        "Number of citizens is " + citizenCount);
            }
        }

        houseDAO.updateHouse(id, updatedHouse);
    }
}
