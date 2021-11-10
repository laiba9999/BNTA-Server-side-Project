package com.teamname.allotments;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AllotmentDatabaseAccessService implements AllotmentDAO {
    private JdbcTemplate jdbcTemplate;

    public AllotmentDatabaseAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper rowMapper = new RowMapper() {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Allotment allotment = new Allotment(rs.getInt("id"));
            return allotment;
        }
    };

    @Override
    public List<Allotment> selectAllAllotments() {
        String sql = """
                SELECT id 
                FROM allotments;
                """;
        var allotments = jdbcTemplate.query(sql, rowMapper);
        return allotments;
    }

    @Override
    public Optional<Allotment> selectAllotmentById(int id) {
        String sql = """
                SELECT id
                FROM allotments
                WHERE id = ?;
                """;
        return jdbcTemplate.query(sql, rowMapper, id).stream().findFirst();
    }

    @Override
    public int createAllotment(Allotment allotment) {
        String sql = "insert into allotments (id) values (?);";
         return jdbcTemplate.update(sql, allotment.getId());

    }
}
