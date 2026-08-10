package com.krzysztof.PoeExplorer.client;

import com.krzysztof.PoeExplorer.dto.PoeNinjaResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PoeNinjaClient {

    private final RestClient restClient;

    public PoeNinjaClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public PoeNinjaResponse getCurrencyPrices(String league) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/poe1/api/economy/exchange/current/overview")
                        .queryParam("league", league)
                        .queryParam("type", "Currency")
                        .build())
                .retrieve()
                .body(PoeNinjaResponse.class);
    }}
