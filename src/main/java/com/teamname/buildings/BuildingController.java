package com.teamname.buildings;

import com.teamname.buildings.houses.HouseService;
import com.teamname.buildings.workplaces.WorkplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/buildings")
public class BuildingController {

    private HouseService houseService;
    private WorkplaceService workplaceService;

    @Autowired
    public BuildingController(HouseService houseService, WorkplaceService workplaceService) {
        this.houseService = houseService;
        this.workplaceService = workplaceService;
    }

    @GetMapping
    public List<Building> getAllBuildings(){
        List<Building> buildings = new ArrayList<>();
        houseService.getAllHouses().forEach(house -> buildings.add(house));
        workplaceService.getAllWorkplace().forEach(workplace -> buildings.add(workplace));
        return buildings;
    }
}
