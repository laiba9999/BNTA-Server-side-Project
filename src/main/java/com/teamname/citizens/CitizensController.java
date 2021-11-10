package com.teamname.citizens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/citizens")
public class CitizensController {
    private CitizensService citizensService;

    @Autowired
    public CitizensController(CitizensService citizensService) {
        this.citizensService = citizensService;
    }
}
