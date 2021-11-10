package com.teamname.citizens;

import java.util.List;
import java.util.Optional;

public interface CitizenDAO {
    List<Citizen> selectAllCitizens();
    Optional<Citizen> selectPersonById(Integer id);
    int insertCitizen(Citizen citizen);
    int deleteCitizen(int id);
    int updateCitizen(Citizen citizen);
}
