package com.teamname.buildings;

import com.teamname.buildings.houses.HouseDAO;
import com.teamname.buildings.workplaces.WorkplaceDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingService {
    private HouseDAO houseDAO;
    private WorkplaceDAO workplaceDAO;

    public BuildingService(HouseDAO houseDAO, WorkplaceDAO workplaceDAO) {
        this.houseDAO = houseDAO;
        this.workplaceDAO = workplaceDAO;
    }

    public List<Building> getAllBuildings() {
        List<Building> buildings = new ArrayList<>();
        houseDAO.selectAllHouses().forEach(house -> buildings.add(house));
        workplaceDAO.selectAllWorkplace().forEach(workplace -> buildings.add(workplace));
        return buildings;
    }
}
