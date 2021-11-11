package com.teamname.citizens;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {
    private CitizenDAO citizenDAO;

    @Autowired
    public CitizenService(CitizenDAO citizenDAO) {
        this.citizenDAO = citizenDAO;
    }

    public List<Citizen> getAllCitizens() {
        return citizenDAO.selectAllCitizens();
    }

    public Optional<Citizen> getCitizenById(Integer id) {
        return citizenDAO.selectCitizenById(id);
    }

    public void insertCitizen(Citizen citizen) {
        citizenDAO.insertCitizen(citizen);
    }

    public void deleteCitizen(Integer id) {
        citizenDAO.deleteCitizen(id);
    }

    public void updateCitizen(Integer id, Citizen citizen) {
        citizenDAO.updateCitizen(id, citizen);
    }
}
