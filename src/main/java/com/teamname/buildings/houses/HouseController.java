package com.teamname.buildings.houses;

import com.teamname.allotments.Allotment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/houses")
public class HouseController {

    private HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping
    public List<House> getALlHouses(){return houseService.getAllHouses();}

    @GetMapping("{id}")
    public Optional<House> getHouseById(@PathVariable("id") int id){
        return houseService.getHouseById(id);
    }

    @PostMapping
    public void createAllotment(@RequestBody House house) {houseService.createHouse(house);}
}
