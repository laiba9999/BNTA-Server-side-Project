package com.teamname.citizens;

import java.util.List;
import java.util.Optional;

public interface CitizensDAO {
    List<Citizens> selectAllCitizens();
    Optional<Citizens> selectPersonById();
    int insertCitizen(Citizens citizen);
    int deleteCitizen(int id);
    int updateCitizen(Citizens citizen);
}
