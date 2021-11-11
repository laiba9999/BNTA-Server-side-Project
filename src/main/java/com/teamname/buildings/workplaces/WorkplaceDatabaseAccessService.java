package com.teamname.buildings.workplaces;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class WorkplaceDatabaseAccessService implements WorkplaceDAO {
    private JdbcTemplate jdbcTemplate;

    public WorkplaceDatabaseAccessService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper rowMapper = new RowMapper() {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Workplace workplace = new Workplace(rs.getInt("id"), rs.getString("buildingName"),rs.getInt("capacity"), rs.getInt("allotment_id"));
            return workplace;
        }
    };


    @Override
    public List<Workplace> selectAllWorkplace(){
        String sql = """
                SELECT id
                FROM workplace;
                """;
        List<Workplace> workplace = jdbcTemplate.query(sql, rowMapper);
        return workplace;
    }

    @Override
    public Optional<Workplace> selectWorkplaceById(int id){
        String sql = """
                SELECT id
                FROM workplace
                WHERE id = ?;
                """;
        return jdbcTemplate.query(sql, rowMapper, id).stream().findFirst();
    }

    @Override
    public int createWorkplace(Workplace workplace){
        String sql = """
                INSERT INTO allotment 
                (id) values (?);
                """;
        return jdbcTemplate.update(sql, workplace.getId());
    }

    @Override
    public int deleteWorkplace(int id){
        return 0;
    }

    @Override
    public int updateWorkplace(int id) {
        return 0;
    }

}
