package com.krzysztof.PoeExplorer.dto;
import java.math.BigDecimal;
public record CurrencyPrice(String id, BigDecimal primaryValue, BigDecimal volumePrimaryValue, String maxVolumeCurrency, BigDecimal maxVolumeRate, Sparkline sparkline) {

}
