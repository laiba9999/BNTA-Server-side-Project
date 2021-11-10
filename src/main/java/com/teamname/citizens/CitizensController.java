package com.teamname.citizens;

public class CitizensController {
    private CitizensService citizensService;

    public CitizensController(CitizensService citizensService) {
        this.citizensService = citizensService;
    }
}
