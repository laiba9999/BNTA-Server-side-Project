package com.teamname.allotments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/allotments")
public class AllotmentController {

    private AllotmentService allotmentService;

    @Autowired
    public AllotmentController(AllotmentService allotmentService) {
        this.allotmentService = allotmentService;
    }
}
