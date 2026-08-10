package com.krzysztof.PoeExplorer.dto;

import java.util.List;
import java.util.Map;

public record Core(List<Currency> items, Map<String, Double> rates, String primary, String secondary) {

}