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
    public List<Citizen> getAllCitizens(@RequestParam(required = false) Integer house_id, @RequestParam(required = false) Integer workplace_id) {
        if (house_id == null && workplace_id == null) {
            return citizenService.getAllCitizens();
        } else if (house_id != null) {
            return citizenService.getCitizensOfHouse(house_id);
        } else {
            return citizenService.getCitizensOfWorkplace(workplace_id);
        }
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


}
