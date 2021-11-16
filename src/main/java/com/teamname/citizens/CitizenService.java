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

    // throw ResourcesNotFound and NotModified
    public void updateCitizen(Integer id, Citizen updatedCitizen) {
        if (citizenDAO.selectCitizenById(id).isEmpty()) {
            throw new ResourcesNotFoundException("Citizen with id " + id + "does not exist");
        }
        Optional<Citizen> oldCitizen = citizenDAO.selectCitizenById(id);
        boolean update = false;

        if (oldCitizen.equals(Optional.of(updatedCitizen))) {
            throw new NotModifiedException("No modifications made to citizen with id " + id);
        }
        if (updatedCitizen.getFullName() == null) {
            updatedCitizen.setFullName(oldCitizen.get().getFullName());
        }
        if (updatedCitizen.getHouse_id() == null) {
            updatedCitizen.setHouse_id(oldCitizen.get().getHouse_id());
        }
        if (updatedCitizen.getWorkplace_id() == null) {
            updatedCitizen.setWorkplace_id(oldCitizen.get().getWorkplace_id());
        }
        citizenDAO.updateCitizen(id, updatedCitizen);

    }
}
