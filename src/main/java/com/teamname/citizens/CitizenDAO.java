package com.teamname.citizens;

import java.util.List;
import java.util.Optional;

public interface CitizenDAO {
    List<Citizen> selectAllCitizens();

    Optional<Citizen> selectCitizenById(Integer id);

    int insertCitizen(Citizen citizen);

    int deleteCitizen(int id);

    int updateCitizen(Integer id, Citizen citizen);

    List<Citizen> selectCitizensOfHouse(Integer houseID);

    List<Citizen> selectCitizensOfWorkplace(Integer workplaceID);
}
