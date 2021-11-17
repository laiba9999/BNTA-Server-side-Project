//package com.teamname.buildings;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//@Repository
//public abstract class BuildingDatabaseAccessService implements BuildingDAO {
//
//    private JdbcTemplate jdbcTemplate;
//
//    public BuildingDatabaseAccessService(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    RowMapper rowMapper = new RowMapper() {
//        @Override
//        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Building building = new House(rs.getInt("id"), rs.getString("buildingName"), rs.getInt("capacity"),rs.getInt("allotment_id"));
//        }
//    }
//
//    public List<Building> selectAllBuildings(){
//        String sql = "SELECT * FROM "
//    }
//}
