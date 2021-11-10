package com.teamname.citizens;

import com.teamname.allotments.Allotment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CitizenDatabaseAccessService implements CitizenDAO {

    private JdbcTemplate jdbcTemplate;

    public CitizenDatabaseAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper rowMapper = new RowMapper() {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Citizen citizen = new Citizen(
                    rs.getInt("id"),
                    rs.getNString("fullname"),
                    rs.getInt("home_id"),
                    rs.getInt("workplace_id"));
            return citizen;
        }
    };

    @Override
    public List<Citizen> selectAllCitizens() {
        String sql = """
                SELECT *
                FROM citizens;
                """;

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Citizen> selectPersonById(Integer id) {
        String sql = """
                SELECT *
                FROM citizens
                WHERE id = ?;
                """;

        return jdbcTemplate.query(sql, rowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public int insertCitizen(Citizen citizen) {
        String sql = """
                INSERT TO citizens (id, fullname, home_id, workplace_id)
                VALUES (?, ?, ?, ?);
                """;
        return jdbcTemplate.update(
                sql,
                citizen.getId(),
                citizen.getFullName(),
                citizen.getHome_id(),
                citizen.getWorkplace_id());
    }

    @Override
    public int deleteCitizen(int id) {
        return 0;
    }

    @Override
    public int updateCitizen(Citizen citizen) {
        return 0;
    }
}
