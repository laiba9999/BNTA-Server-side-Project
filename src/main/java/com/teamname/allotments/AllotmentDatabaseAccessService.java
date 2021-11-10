package com.teamname.allotments;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AllotmentDatabaseAccessService implements AllotmentDAO {
    private JdbcTemplate jdbcTemplate;

    public AllotmentDatabaseAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Allotment> selectAllAllotments() {
        return null;
    }

    @Override
    public Optional<Allotment> selectAllotmentById() {
        return Optional.empty();
    }

    @Override
    public int createAllotment() {
        return 0;
    }
}
