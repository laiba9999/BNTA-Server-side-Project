package com.teamname.citizens;
import com.teamname.buildings.houses.HouseService;
import com.teamname.buildings.workplaces.WorkplaceService;
import com.teamname.exceptions.NotModifiedException;
import com.teamname.exceptions.ResourcesNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {
    private CitizenDAO citizenDAO;
    private HouseService houseService;
    private WorkplaceService workplaceService;

    @Autowired
    public CitizenService(CitizenDAO citizenDAO, HouseService houseService, WorkplaceService workplaceService) {
        this.citizenDAO = citizenDAO;
        this.houseService = houseService;
        this.workplaceService = workplaceService;
    }

    public List<Citizen> getAllCitizens() {
        return citizenDAO.selectAllCitizens();
    }

    public List<Citizen> getCitizensOfHouse(Integer houseID) {
        return citizenDAO.selectCitizensOfHouse(houseID);
    }

    public List<Citizen> getCitizensOfWorkplace(Integer workplaceID) {
        return citizenDAO.selectCitizensOfWorkplace(workplaceID);
    }
//    throw resources not found error
    public Optional<Citizen> getCitizenById(Integer id) {
        if (citizenDAO.selectCitizenById(id).isEmpty()) {
            throw new ResourcesNotFoundException("Citizen with id "+id+" does not exist");
        }
        return citizenDAO.selectCitizenById(id);
    }

    public void insertCitizen(Citizen citizen) {
        // name validation

        if(citizen.getHouse_id() != null) {

            Integer houseCount = 0;
            for (Citizen citizenInDatabase : getAllCitizens()) {
                if (citizenInDatabase.getHouse_id().equals(citizen.getHouse_id())) {
                    houseCount++;
                }
            }
            if (houseCount >= houseService.getHouseById(citizen.getHouse_id()).get().getCapacity()) {
                throw new IllegalStateException("House at max capacity");
            }
        }

        if(citizen.getWorkplace_id() != null) {
            Integer workplaceCount = 0;
            for (Citizen citizenInDatabase : getAllCitizens()) {
                if (citizenInDatabase.getWorkplace_id().equals(citizen.getWorkplace_id())) {
                    workplaceCount++;
                }
            }
            if (workplaceCount >= workplaceService.getWorkplaceById(citizen.getWorkplace_id()).get().getCapacity()) {
                throw new IllegalStateException("Workplace at max capacity");
            }
        }

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
                && (updatedCitizen.getHouse_id() == null)
                && (updatedCitizen.getWorkplace_id() == null)) {
            throw new IllegalStateException("No content"); // Implementation not complete
        }

        Optional<Citizen> oldCitizen = citizenDAO.selectCitizenById(id);

        updatedCitizen.setId(oldCitizen.get().getId());
        if (Optional.of(updatedCitizen).equals(oldCitizen)) {
            throw new NotModifiedException("No modifications made to citizen with id " + id);
        }


        if (updatedCitizen.getFullName() == null || updatedCitizen.getFullName().length() == 0) {
            updatedCitizen.setFullName(oldCitizen.get().getFullName());
        }

        if (updatedCitizen.getHouse_id() == null) {
            if (oldCitizen.get().getHouse_id() == 0) {
                updatedCitizen.setHouse_id(null);
            } else {
                updatedCitizen.setHouse_id(oldCitizen.get().getHouse_id());
            }
        } else if (updatedCitizen.getHouse_id() == 0) {
            updatedCitizen.setHouse_id(null);
        } else {
            Integer houseCount = 0;
            for (Citizen citizenInDatabase : getAllCitizens()) {
                if (citizenInDatabase.getHouse_id().equals(updatedCitizen.getHouse_id())) {
                    houseCount++;
                }
            }
            if (houseCount >= houseService.getHouseById(updatedCitizen.getHouse_id()).get().getCapacity()) {
                throw new IllegalStateException("House at max capacity");
            }
        }

        if (updatedCitizen.getWorkplace_id() == null) {
            if (oldCitizen.get().getWorkplace_id() == 0) {
                updatedCitizen.setWorkplace_id(null);
            } else {
                updatedCitizen.setWorkplace_id(oldCitizen.get().getWorkplace_id());
            }
        } else if (updatedCitizen.getWorkplace_id() == 0){
            updatedCitizen.setWorkplace_id(null);
        } else {
            Integer workplaceCount = 0;
            for (Citizen citizenInDatabase : getAllCitizens()) {
                if (citizenInDatabase.getWorkplace_id().equals(updatedCitizen.getWorkplace_id())) {
                    workplaceCount++;
                }
            }
            if (workplaceCount >= workplaceService.getWorkplaceById(updatedCitizen.getWorkplace_id()).get().getCapacity()) {
                throw new IllegalStateException("Workplace at max capacity");
            }
        }
        citizenDAO.updateCitizen(id, updatedCitizen);
    }

}
