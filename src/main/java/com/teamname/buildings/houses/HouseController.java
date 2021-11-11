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
    public void createHouse(@RequestBody House house) {houseService.createHouse(house);}

    @DeleteMapping("{id}")
    public void deleteHouse(@PathVariable("id") int id){houseService.deleteHouse(id);}

//    Update request is Put request in REST API terms
    @PutMapping("{id}")
    public void updateHouse(@PathVariable("id") int id, @RequestBody House house){houseService.updateHouse(id,house);}
}
