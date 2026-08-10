package com.krzysztof.PoeExplorer.controller;


import com.krzysztof.PoeExplorer.model.League;
import com.krzysztof.PoeExplorer.service.LeagueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/leagues")
public class LeagueController {


    private final LeagueService leagueService;


    public LeagueController(
            LeagueService leagueService
    ) {

        this.leagueService = leagueService;

    }



    @GetMapping
    public List<League> getLeagues() throws Exception {

        return leagueService.getAllLeagues();

    }

}