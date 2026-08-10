package com.krzysztof.PoeExplorer.dto;

import java.math.BigDecimal;

public record CurrencyPriceResponse(String id,String name, BigDecimal price, BigDecimal change7Days) {

}
