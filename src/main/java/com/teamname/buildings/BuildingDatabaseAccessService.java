package com.teamname.buildings;

import com.teamname.citizens.Citizen;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BuildingDatabaseAccessService implements BuildingDAO {
    private JdbcTemplate jdbcTemplate;

    public BuildingDatabaseAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Building> selectAllBuildings() {
        return null;
    }

    @Override
    public Optional<Building> selectBuildingById() {
        return Optional.empty();
    }

    @Override
    public int insertBuilding(Citizen citizen) {
        return 0;
    }

    @Override
    public int deleteBuilding(int id) {
        return 0;
    }

    @Override
    public int updateBuilding(Citizen citizen) {
        return 0;
    }
}
