package com.krzysztof.PoeExplorer.dto;

import java.util.List;

public record PoeNinjaResponse(Core core, List<CurrencyPrice> lines) {

}