package com.krzysztof.PoeExplorer.service;

import com.krzysztof.PoeExplorer.client.PoeNinjaClient;
import com.krzysztof.PoeExplorer.dto.Currency;
import com.krzysztof.PoeExplorer.dto.CurrencyPrice;
import com.krzysztof.PoeExplorer.dto.CurrencyPriceResponse;
import com.krzysztof.PoeExplorer.dto.PoeNinjaResponse;
import com.krzysztof.PoeExplorer.exception.CurrencyNotFoundException;
import com.krzysztof.PoeExplorer.exception.InvalidPaginationException;
import com.krzysztof.PoeExplorer.exception.InvalidSortException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    private final PoeNinjaClient poeNinjaClient;


    public CurrencyService(PoeNinjaClient poeNinjaClient){
        this.poeNinjaClient=poeNinjaClient;

    }

    public PoeNinjaResponse getCurrencyPrices(String league) throws Exception{
        return poeNinjaClient.getCurrencyPrices(league);
    }
    public CurrencyPriceResponse getCurrency(String league, String currencyId) {
        PoeNinjaResponse response = poeNinjaClient.getCurrencyPrices(league);
        CurrencyPrice price = response.lines()
                .stream()
                .filter(currencyPrice ->currencyPrice.id().equals(currencyId))
                .findFirst()
                .orElseThrow(()-> new CurrencyNotFoundException(currencyId)) ;

        Currency item = response.core().items()
                .stream()
                .filter(currency -> currency.id().equals(currencyId))
                .findFirst()
                .orElseThrow(() -> new CurrencyNotFoundException((currencyId)));

        return new CurrencyPriceResponse(
                price.id(),
                item.name(),
                price.primaryValue(),
                price.sparkline().totalChange());
    }

    public List<CurrencyPriceResponse> getCurrencies(String league, String sort,int page,int size,String search){

        validatePagination(page,size);

        PoeNinjaResponse response = poeNinjaClient.getCurrencyPrices(league);

        List<CurrencyPriceResponse> currencies = mapCurrencies(response);

        currencies = filterCurrencies(search,currencies);
        sortCurrencies(sort,currencies);

        int start = page*size;
        int end = Math.min(start+size,currencies.size());

        if(start>= currencies.size()){
            return Collections.emptyList();
        }

            return currencies.subList(start,end);

        }



    private void validatePagination(int page, int size){

        if (page < 0) {
            throw new InvalidPaginationException("Invalid page value " + page);
        }

        if (size <= 0) {
            throw new InvalidPaginationException("Invalid size value " + size);
        }
    }
    private void sortCurrencies(String sort,List<CurrencyPriceResponse> currencies){
        if(sort == null || sort.equals("price")){
            currencies.sort(Comparator.comparing(CurrencyPriceResponse::price).reversed());
        } else if(sort.equals("change")){
            currencies.sort(Comparator.comparing(CurrencyPriceResponse::change7Days).reversed());
        }else {
            throw new InvalidSortException("Invalid sort value "+ sort);
        }
    }
    private List<CurrencyPriceResponse> mapCurrencies(PoeNinjaResponse response) {
        return response.lines()
                .stream()
                .map(price -> {
                    Optional<Currency> item = response.core().items()
                            .stream()
                            .filter(currency -> currency.id().equals(price.id()))
                            .findFirst();

                    return new CurrencyPriceResponse(
                            price.id(),
                            item.map(Currency::name)
                                    .orElse(price.id()),
                            price.primaryValue(),
                            price.sparkline().totalChange()
                    );
                })
                .collect(Collectors.toList());
    }
    private List<CurrencyPriceResponse> filterCurrencies(String search, List<CurrencyPriceResponse> currencies){
    if(search == null){
        return currencies;
    }else{
        return currencies.stream()
                .filter(currency-> currency.name().toLowerCase().contains(search.toLowerCase()) || currency.id().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
    }



}}
