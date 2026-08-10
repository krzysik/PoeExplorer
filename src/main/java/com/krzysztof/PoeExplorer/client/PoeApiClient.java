package com.krzysztof.PoeExplorer.client;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krzysztof.PoeExplorer.model.League;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@Component
public class PoeApiClient {


    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;


    @Value("${poe.api.url}")
    private String apiUrl;


    public PoeApiClient() {

        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();

    }



    public List<League> getLeagues() throws Exception {


        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(apiUrl))
                        .GET()
                        .build();



        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );


        if(response.statusCode() != 200){

            throw new RuntimeException(
                    "PoE API error: "
                            + response.statusCode()
            );

        }



        return objectMapper.readValue(
                response.body(),
                new TypeReference<List<League>>() {}
        );

    }

}