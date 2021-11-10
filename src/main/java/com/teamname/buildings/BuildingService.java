package com.teamname.buildings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {
    private BuildingDAO buildingDAO;

    @Autowired
    public BuildingService(BuildingDAO buildingDAO) {
        this.buildingDAO = buildingDAO;
    }


}
