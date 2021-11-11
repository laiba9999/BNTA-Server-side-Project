package com.teamname.buildings.houses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseService {
    private HouseDAO houseDAO;

    @Autowired
    public HouseService(HouseDAO houseDAO) {
        this.houseDAO = houseDAO;
    }

    public List<House> getAllHouses() {
        return houseDAO.selectAllHouses();
    }

    public Optional<House> getHouseById(int id) {
        return houseDAO.selectHouseById(id);
    }

    public void createHouse(House house) {houseDAO.createHouse(house);
    }

    public void deleteHouse(int id) {
        houseDAO.deleteHouse(id);
    }

    public void updateHouse(int id, House house) {
        houseDAO.updateHouse(id, house);
    }
}
