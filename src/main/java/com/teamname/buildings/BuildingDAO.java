package com.teamname.buildings;

import com.teamname.citizens.Citizen;

import java.util.List;
import java.util.Optional;

public interface BuildingDAO {
    List<Building> selectAllBuildings();
    Optional<Building> selectBuildingById();
    int insertBuilding(Citizen citizen);
    int deleteBuilding(int id);
    int updateBuilding(Citizen citizen);
}
