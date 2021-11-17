package com.teamname.citizens;

import com.teamname.buildings.workplaces.Workplace;
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
        if(citizenDAO.selectCitizenById(id).isEmpty()){
            throw new ResourcesNotFoundException("Citizen with id "+id+" doesn't exist!");
        }

        if ((updatedCitizen.getFullName() == null || updatedCitizen.getFullName().length() == 0)
                && (updatedCitizen.getHouse_id() == null || updatedCitizen.getHouse_id() == 0)
                && (updatedCitizen.getWorkplace_id() == null || updatedCitizen.getWorkplace_id() == 0)) {
            throw new IllegalStateException("No content"); // Implementation not complete
        }

        Optional<Citizen> oldCitizen = citizenDAO.selectCitizenById(id);

        updatedCitizen.setId(oldCitizen.get().getId());
        updatedCitizen.setFullName(oldCitizen.get().getFullName());
        updatedCitizen.setHouse_id(oldCitizen.get().getHouse_id());
        updatedCitizen.setWorkplace_id(oldCitizen.get().getWorkplace_id());
        if (Optional.of(updatedCitizen).equals(oldCitizen)) {
            throw new NotModifiedException("No modifications made to citizen with id " + id);
        }


        if (updatedCitizen.getFullName() == null || updatedCitizen.getFullName().length() == 0) {
            updatedCitizen.setFullName(oldCitizen.get().getFullName());
        }
        if (updatedCitizen.getHouse_id() == null || updatedCitizen.getHouse_id() == 0) {
            updatedCitizen.setHouse_id(oldCitizen.get().getHouse_id());
        }
        if (updatedCitizen.getWorkplace_id() == null || updatedCitizen.getWorkplace_id() == 0){
            updatedCitizen.setWorkplace_id(oldCitizen.get().getWorkplace_id());
        }
        citizenDAO.updateCitizen(id, updatedCitizen);
    }
}
