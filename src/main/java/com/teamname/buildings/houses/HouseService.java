package com.teamname.buildings.houses;

import com.teamname.allotments.AllotmentService;
import com.teamname.buildings.Building;
import com.teamname.buildings.BuildingService;
import com.teamname.buildings.workplaces.WorkplaceDAO;
import com.teamname.exceptions.ResourcesNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseService {
    private HouseDAO houseDAO;
    private BuildingService buildingService;
    private AllotmentService allotmentService;

    public HouseService(HouseDAO houseDAO, BuildingService buildingService, AllotmentService allotmentService) {
        this.houseDAO = houseDAO;
        this.buildingService = buildingService;
        this.allotmentService = allotmentService;
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

    public void createHouse(House house) {houseDAO.createHouse(house);
        for(Building building: buildingService.getAllBuildings() ){
            if (building.getAllotment_id() == house.getAllotment_id()){
                throw new IllegalStateException("Allotment "+house.getAllotment_id()+" already has a building on it");
            }
            allotmentService.getAllotmentById(house.getAllotment_id());
        }
        houseDAO.createHouse(house);
    }

    public void deleteHouse(int id) {
        if(houseDAO.selectHouseById(id).isEmpty()){
            throw new ResourcesNotFoundException("House with id "+id+" doesn't exist!");
        }
        houseDAO.deleteHouse(id);
    }

    public void updateHouse(int id, House house) {
        if(houseDAO.selectHouseById(id).isEmpty()){
            throw new ResourcesNotFoundException("House with id "+id+" doesn't exist!");
        }
        houseDAO.updateHouse(id, house);
    }
}
