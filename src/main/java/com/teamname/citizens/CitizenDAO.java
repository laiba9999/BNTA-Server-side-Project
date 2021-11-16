package com.teamname.citizens;

import java.util.List;
import java.util.Optional;

public interface CitizenDAO {
    List<Citizen> selectAllCitizens();
    Optional<Citizen> selectCitizenById(Integer id);
    int insertCitizen(Citizen citizen);
    int deleteCitizen(int id);
    int updateCitizen(Integer id, Citizen citizen);
    int updateCitizenName(Integer id, String name);
    int updateCitizenHouseId(Integer id, Integer house_id);
    int updateCitizenWorkplaceId(Integer id, Integer workplace_id);

}
