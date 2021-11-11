package com.teamname.buildings.houses;

import java.util.List;
import java.util.Optional;

public interface HouseDAO {
    List<House> selectAllHouses();
    Optional<House> selectHouseById(int id);
    int createHouse(House house);
    int deleteHouse(int id);
    int updateHouse(House house);
}
