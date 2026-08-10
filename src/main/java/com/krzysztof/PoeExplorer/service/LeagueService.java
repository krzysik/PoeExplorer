package com.krzysztof.PoeExplorer.service;


import com.krzysztof.PoeExplorer.client.PoeApiClient;
import com.krzysztof.PoeExplorer.model.League;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LeagueService {


    private final PoeApiClient poeApiClient;


    public LeagueService(
            PoeApiClient poeApiClient
    ) {

        this.poeApiClient = poeApiClient;

    }



    public List<League> getAllLeagues() throws Exception {

        return poeApiClient.getLeagues();

    }

}