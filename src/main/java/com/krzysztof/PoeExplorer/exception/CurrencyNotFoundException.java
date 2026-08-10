package com.krzysztof.PoeExplorer.exception;

public class CurrencyNotFoundException extends RuntimeException{
    public CurrencyNotFoundException(String currencyId) {
        super("Currency not found:" + currencyId);
    }
}
