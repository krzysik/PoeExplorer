package com.krzysztof.PoeExplorer.controller;
import com.krzysztof.PoeExplorer.dto.CurrencyPriceResponse;
import com.krzysztof.PoeExplorer.service.CurrencyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {

        this.currencyService = currencyService;

    }

    @GetMapping("/{currencyId}")
    public CurrencyPriceResponse getCurrency (
            @RequestParam String league,
            @PathVariable String currencyId
    ) {
        return currencyService.getCurrency(league, currencyId);
    }
@GetMapping
    public List<CurrencyPriceResponse> getCurrencies(@RequestParam String league,@RequestParam(required = false) String sort,@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10") int size,@RequestParam(required = false) String search) throws Exception{
        return currencyService.getCurrencies(league,sort,page,size,search);
}

}
