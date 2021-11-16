package com.teamname.citizens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/citizens")
public class CitizenController {
    private CitizenService citizenService;

    @Autowired
    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @GetMapping
    public List<Citizen> getAllCitizens() {
        return citizenService.getAllCitizens();
    }

    @GetMapping("{id}")
    public Optional<Citizen> getCitizenById(@PathVariable("id") Integer id) {
        return citizenService.getCitizenById(id);
    }

    @PostMapping
    public void insertCitizen(@RequestBody Citizen citizen) {
        citizenService.insertCitizen(citizen);
    }

    @DeleteMapping("{id}")
    public void deleteCitizen(@PathVariable("id") @RequestBody Integer id) {
        citizenService.deleteCitizen(id);
    }

    @PutMapping("{id}")
    public void updateCitizen(@PathVariable("id") Integer id, @RequestBody Citizen citizen) {
        citizenService.updateCitizen(id, citizen);
    }

    @PutMapping("{id}/fullname")
    public void updateCitizenName(@PathVariable("id") Integer id, @RequestBody Citizen citizen) {
        citizenService.updateCitizenName(id, citizen.getFullName());
    }

    @PutMapping("{id}/house_id")
    public void updateCitizenHouseId(@PathVariable("id") Integer id, @RequestBody Citizen citizen) {
        citizenService.updateCitizenHouseId(id, citizen.getHouse_id());
    }

    @PutMapping("{id}/workplace_id")
    public void updateCitizenWorkplaceId(@PathVariable("id") Integer id, @RequestBody Citizen citizen) {
        citizenService.updateCitizenWorkplaceId(id, citizen.getWorkplace_id());
    }

    @PatchMapping("{id}")
    public void updateCitizenPatch(@PathVariable("id") Integer id, @RequestBody Citizen citizen) {
        citizenService.updateCitizenPatch(id, citizen);
    }
}
