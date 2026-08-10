package com.krzysztof.PoeExplorer.dto;
import java.math.BigDecimal;
import java.util.List;

public record Sparkline(BigDecimal totalChange, List<BigDecimal> data) {

}