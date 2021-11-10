package com.teamname.citizens;

import java.util.List;
import java.util.Optional;

public class CitizenDatabaseAccessService implements CitizensDAO {

    private JdbcTemplate jdbcTemplate;

    public CitizenDatabaseAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<Citizens> selectAllCitizens() {
        return null;
    }

    @Override
    public Optional<Citizens> selectPersonById() {
        return Optional.empty();
    }

    @Override
    public int insertCitizen(Citizens citizen) {
        return 0;
    }

    @Override
    public int deleteCitizen(int id) {
        return 0;
    }

    @Override
    public int updateCitizen(Citizens citizen) {
        return 0;
    }
}
