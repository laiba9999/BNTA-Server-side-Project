package com.teamname.buildings.houses;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class HouseDatabaseAccessService implements HouseDAO {
    private JdbcTemplate jdbcTemplate;

    public HouseDatabaseAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    Define Rowmapper for when we need to select building by ID
    RowMapper rowMapper = new RowMapper(){
        @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            House house = new House(rs.getInt("id"),rs.getString("buildingName")
                    ,rs.getInt("capacity"),rs.getInt("allotment_id"));
            return house;
        }
};


    @Override
    public List<House> selectAllHouses() {
        String sql = "SELECT * FROM houses;";
        var houses = jdbcTemplate.query(sql, rowMapper);
        return houses;
    }

    @Override
    public Optional<House> selectHouseById(int id) {
        String sql = "SELECT * FROM houses WHERE id = ?;";
        return jdbcTemplate.query(sql,rowMapper, id).stream().findFirst();
    }

    @Override
    public int createHouse(House house) {
        String sql = "INSERT into houses (buildingName, capacity, allotment_id) VALUES (?,?,?);";
        return jdbcTemplate.update(sql, house.getBuildingName(),house.getCapacity(),house.getAllotment_id());
    }

    @Override
    public int deleteHouse(int id) {
        String sql = "DELETE FROM houses WHERE id = ?;";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updateHouse(int id, House house) {
//        Todo check allotment is full before updating
        String sql = "UPDATE houses SET buildingName = ?, capacity = ?, allotment_id = ? WHERE id = ?;";

        return jdbcTemplate.update(sql, house.getBuildingName(),house.getCapacity(),house.getAllotment_id(),id);
    }
}
