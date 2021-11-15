package com.teamname.citizens;


import com.teamname.exceptions.NotModifiedException;
import com.teamname.exceptions.ResourcesNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

//    throw resources not found error
    public Optional<Citizen> getCitizenById(Integer id) {
        if (citizenDAO.selectCitizenById(id).isEmpty()) {
            throw new ResourcesNotFoundException("Citizen with id "+id+" does not exist");
        }
        return citizenDAO.selectCitizenById(id);
    }

    public void insertCitizen(Citizen citizen) {
        citizenDAO.insertCitizen(citizen);
    }

//    throw resources not found
    public void deleteCitizen(Integer id) {
        if (citizenDAO.selectCitizenById(id).isEmpty()) {
            throw new ResourcesNotFoundException("Citizen with id " + id + " does not exist");
        }
        citizenDAO.deleteCitizen(id);
    }

//    throws resources not found
    public void updateCitizen(Integer id, Citizen citizen) {
        if (citizenDAO.selectCitizenById(id).isEmpty()) {
            throw new ResourcesNotFoundException("Citizen with id " + id + "does not exist");
        }
        if (citizenDAO.selectCitizenById(id).equals(Optional.of(citizen))) {
            throw new NotModifiedException("No modifications made to citizen with id " + id);
        }
        citizenDAO.updateCitizen(id, citizen);
    }
}
