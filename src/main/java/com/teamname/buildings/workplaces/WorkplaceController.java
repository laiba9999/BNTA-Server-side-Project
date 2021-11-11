package com.teamname.buildings.workplaces;

import com.teamname.allotments.Allotment;
import com.teamname.buildings.houses.House;
import com.teamname.buildings.houses.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/buildings/workplace")
public class WorkplaceController {

    private WorkplaceService workplaceService;

    @Autowired
    public WorkplaceController(WorkplaceService workplaceService) {
        this.workplaceService = workplaceService;
    }

    @GetMapping
    public List<Workplace> getAllWorkplace(){return workplaceService.getAllWorkplace();}

    @GetMapping("{id}")
    public Optional<Workplace> getWorkplaceById(@PathVariable("id") int id){
        return workplaceService.getWorkplaceById(id);
    }

    @PostMapping
    public void createWorkplace(@RequestBody Workplace workplace) {workplaceService.createWorkplace(workplace);}

    @DeleteMapping("{id}")
    public void deleteWorkplace(@PathVariable("id") int id){
        workplaceService.deleteWorkplace(id);
    }

    @PutMapping("{id}")
    public void updateWorkplace(@PathVariable ("id") int id, @RequestBody Workplace workplace){
        workplaceService.updateWorkplace(id, workplace);
    }

}
