package com.teamname.allotments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/allotments")
public class AllotmentController {

    private AllotmentService allotmentService;

    @Autowired
    public AllotmentController(AllotmentService allotmentService) {
        this.allotmentService = allotmentService;
    }

    @GetMapping
    public List<Allotment> getAllAllotments() {
        return allotmentService.getAllAllotments();
    }

    @GetMapping("{id}")
    public Optional<Allotment> getAllotmentById(@PathVariable("id") int id) {
        return allotmentService.getAllotmentById(id);
    }

    @PostMapping
    public void createAllotment(@RequestBody Allotment allotment) {
        allotmentService.createAllotment(allotment);
    }
}
