package com.krzysztof.PoeExplorer;

import com.krzysztof.PoeExplorer.client.PoeNinjaClient;
import com.krzysztof.PoeExplorer.dto.*;
import com.krzysztof.PoeExplorer.exception.InvalidPaginationException;
import com.krzysztof.PoeExplorer.exception.InvalidSortException;
import com.krzysztof.PoeExplorer.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {
    @Mock private PoeNinjaClient poeNinjaClient;
    @InjectMocks private CurrencyService currencyService;

    @Test
    void shouldReturnCurrencies() {
        Currency currency = new Currency( "chaos", "Chaos Orb", "chaos.png", "currency", "chaos");
        Sparkline sparkline = new Sparkline(  BigDecimal.valueOf(5),List.of() );
        CurrencyPrice currencyPrice = new CurrencyPrice( "chaos", BigDecimal.TEN, BigDecimal.ONE, "chaos", BigDecimal.ONE, sparkline );
        Core core = new Core( List.of(currency), Map.of(), "chaos", "divine" );
        PoeNinjaResponse response = new PoeNinjaResponse( core, List.of(currencyPrice) );
        when(poeNinjaClient.getCurrencyPrices("Settlers"))
                .thenReturn(response);
        List<CurrencyPriceResponse> result = currencyService.getCurrencies( "Settlers", "price", 0, 10, "chaos" );
         assertEquals(1, result.size());
         CurrencyPriceResponse resultCurrency = result.get(0);
         assertEquals("chaos", resultCurrency.id());
         assertEquals("Chaos Orb", resultCurrency.name());
         assertEquals(BigDecimal.TEN, resultCurrency.price());
         assertEquals(BigDecimal.valueOf(5), resultCurrency.change7Days()); }

    @Test
    void shouldReturnEmptyListWhenPageIsOutOfRange(){
        Currency currency = new Currency( "chaos", "Chaos Orb", "chaos.png", "currency", "chaos");
        Sparkline sparkline = new Sparkline(  BigDecimal.valueOf(5),List.of() );
        CurrencyPrice currencyPrice = new CurrencyPrice( "chaos", BigDecimal.TEN, BigDecimal.ONE, "chaos", BigDecimal.ONE, sparkline );
        Core core = new Core( List.of(currency), Map.of(), "chaos", "divine" );
        PoeNinjaResponse response = new PoeNinjaResponse( core, List.of(currencyPrice) );
        when(poeNinjaClient.getCurrencyPrices("Settlers"))
                .thenReturn(response);
        List<CurrencyPriceResponse> result = currencyService.getCurrencies( "Settlers", "price", 1000, 10, "chaos" );
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenPageIsNegative(){
        assertThrows(
                InvalidPaginationException.class,
                () -> currencyService.getCurrencies(
                        "Settlers", "price", -1, 10, "chaos")
        );
    }
    @Test
    void shouldThrowExceptionWhenSizeIsNegative() {
        assertThrows(
                InvalidPaginationException.class,
                () -> currencyService.getCurrencies(
                        "Settlers", "price", 0, -1, "chaos")
        );
}
    @Test
    void shouldThrowExceptionWhenSizeIsZero() {
        InvalidPaginationException exception = assertThrows(
                InvalidPaginationException.class,
                () -> currencyService.getCurrencies(
                        "Settlers", "price", 0, 0, "chaos")
        );

        assertEquals("Invalid size value 0", exception.getMessage());
    }

    @Test
    void shouldReturnSortedCurrenciesByPrice(){

        Sparkline sparkline = new Sparkline(  BigDecimal.valueOf(5),List.of() );
        List<Currency> currencies = List.of(
                new Currency(  "chaos", "Chaos Orb", "chaos.png", "currency", "chaos"),
                new Currency( "divine", "Divine Orb", "divine.png", "currency", "divine"),
                new Currency("augument", "Augument Orb", "augument.png", "augument", "augument")
);

        List<CurrencyPrice> currenciesPrice = List.of(
                new CurrencyPrice( "chaos", BigDecimal.TEN, BigDecimal.ONE, "chaos", BigDecimal.ONE, sparkline ),
                new CurrencyPrice( "divine", BigDecimal.valueOf(100), BigDecimal.ONE, "divine", BigDecimal.ONE, sparkline ),
                new CurrencyPrice( "augument", BigDecimal.ONE, BigDecimal.ONE, "augument", BigDecimal.ONE, sparkline )
);


        Core core = new Core( currencies, Map.of(), "chaos", "divine" );
        PoeNinjaResponse response = new PoeNinjaResponse( core, currenciesPrice );

        when(poeNinjaClient.getCurrencyPrices("Settlers")).thenReturn(response);
        List<CurrencyPriceResponse> result = currencyService.getCurrencies( "Settlers", "price", 0, 10, "orb" );
        assertEquals("divine", result.get(0).id());
        assertEquals("chaos", result.get(1).id());
        assertEquals("augument", result.get(2).id());


    }

    @Test
    void shouldReturnFilteredCurrency(){
        Sparkline sparkline = new Sparkline(  BigDecimal.valueOf(5),List.of() );
        List<Currency> currencies = List.of(
                new Currency(  "chaos", "Chaos Orb", "chaos.png", "currency", "chaos"),
                new Currency( "divine", "Divine Orb", "divine.png", "currency", "divine"),
                new Currency("augument", "Augument Orb", "augument.png", "augument", "augument")
        );

        List<CurrencyPrice> currenciesPrice = List.of(
                new CurrencyPrice( "chaos", BigDecimal.TEN, BigDecimal.ONE, "chaos", BigDecimal.ONE, sparkline ),
                new CurrencyPrice( "divine", BigDecimal.valueOf(100), BigDecimal.ONE, "divine", BigDecimal.ONE, sparkline ),
                new CurrencyPrice( "augument", BigDecimal.ONE, BigDecimal.ONE, "augument", BigDecimal.ONE, sparkline )
        );


        Core core = new Core( currencies, Map.of(), "chaos", "divine" );
        PoeNinjaResponse response = new PoeNinjaResponse( core, currenciesPrice );

        when(poeNinjaClient.getCurrencyPrices("Settlers")).thenReturn(response);
        List<CurrencyPriceResponse> result = currencyService.getCurrencies( "Settlers", "price", 0, 10, "chaos" );
        assertEquals(1,result.size());
        assertEquals("chaos", result.get(0).id());

    }

    @Test
    void shouldReturnEmptyListWhenInvalidFilterProvided() {
        Sparkline sparkline = new Sparkline(BigDecimal.valueOf(5), List.of());
        List<Currency> currencies = List.of(
                new Currency("chaos", "Chaos Orb", "chaos.png", "currency", "chaos"),
                new Currency("divine", "Divine Orb", "divine.png", "currency", "divine"),
                new Currency("augument", "Augument Orb", "augument.png", "augument", "augument")
        );

        List<CurrencyPrice> currenciesPrice = List.of(
                new CurrencyPrice("chaos", BigDecimal.TEN, BigDecimal.ONE, "chaos", BigDecimal.ONE, sparkline),
                new CurrencyPrice("divine", BigDecimal.valueOf(100), BigDecimal.ONE, "divine", BigDecimal.ONE, sparkline),
                new CurrencyPrice("augument", BigDecimal.ONE, BigDecimal.ONE, "augument", BigDecimal.ONE, sparkline)
        );


        Core core = new Core(currencies, Map.of(), "chaos", "divine");
        PoeNinjaResponse response = new PoeNinjaResponse(core, currenciesPrice);

        when(poeNinjaClient.getCurrencyPrices("Settlers")).thenReturn(response);
        List<CurrencyPriceResponse> result = currencyService.getCurrencies("Settlers", "price", 0, 10, "xyz");
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnSortedCurrenciesByChange(){

        List<Sparkline> sparklines = List.of(
                new Sparkline(BigDecimal.valueOf(5),List.of() ),
                new Sparkline(BigDecimal.valueOf(20), List.of()),
                new Sparkline(BigDecimal.valueOf(10), List.of())
        );
        Sparkline sparkline = new Sparkline(  BigDecimal.valueOf(5),List.of() );
        List<Currency> currencies = List.of(
                new Currency(  "chaos", "Chaos Orb", "chaos.png", "currency", "chaos"),
                new Currency( "divine", "Divine Orb", "divine.png", "currency", "divine"),
                new Currency("augument", "Augument Orb", "augument.png", "augument", "augument")
        );

        List<CurrencyPrice> currenciesPrice = List.of(
                new CurrencyPrice( "chaos", BigDecimal.TEN, BigDecimal.ONE, "chaos", BigDecimal.ONE, sparklines.get(2) ),
                new CurrencyPrice( "divine", BigDecimal.valueOf(100), BigDecimal.ONE, "divine", BigDecimal.ONE, sparklines.get(1) ),
                new CurrencyPrice( "augument", BigDecimal.ONE, BigDecimal.ONE, "augument", BigDecimal.ONE, sparklines.get(0) )
        );


        Core core = new Core( currencies, Map.of(), "chaos", "divine" );
        PoeNinjaResponse response = new PoeNinjaResponse( core, currenciesPrice );

        when(poeNinjaClient.getCurrencyPrices("Settlers")).thenReturn(response);
        List<CurrencyPriceResponse> result = currencyService.getCurrencies( "Settlers", "change", 0, 10, "orb" );
        assertEquals("divine", result.get(0).id());
        assertEquals("chaos", result.get(1).id());
        assertEquals("augument", result.get(2).id());


    }
    @Test
    void shouldThrowExceptionWhenInvalidSortProvided() {

            Sparkline sparkline = new Sparkline(BigDecimal.valueOf(5), List.of());

            Currency currency = new Currency("chaos", "Chaos Orb", "chaos.png", "currency", "chaos");

            CurrencyPrice currencyPrice = new CurrencyPrice("chaos", BigDecimal.TEN, BigDecimal.ONE, "chaos", BigDecimal.ONE, sparkline);

            Core core = new Core(List.of(currency), Map.of(), "chaos", "divine");

            PoeNinjaResponse response = new PoeNinjaResponse(core, List.of(currencyPrice));

            when(poeNinjaClient.getCurrencyPrices("Settlers"))
                    .thenReturn(response);

            InvalidSortException exception = assertThrows(
                    InvalidSortException.class,
                    () -> currencyService.getCurrencies(
                            "Settlers", "xyz", 0, 10, "chaos"
                    )
            );

            assertEquals("Invalid sort value xyz", exception.getMessage());
        }

        @Test
        void shouldReturnSortedByPriceWhenSortIsNull(){

            Sparkline sparkline = new Sparkline(  BigDecimal.valueOf(5),List.of() );
            List<Currency> currencies = List.of(
                    new Currency(  "chaos", "Chaos Orb", "chaos.png", "currency", "chaos"),
                    new Currency( "divine", "Divine Orb", "divine.png", "currency", "divine"),
                    new Currency("augument", "Augument Orb", "augument.png", "augument", "augument")
            );

            List<CurrencyPrice> currenciesPrice = List.of(
                    new CurrencyPrice( "chaos", BigDecimal.TEN, BigDecimal.ONE, "chaos", BigDecimal.ONE, sparkline ),
                    new CurrencyPrice( "divine", BigDecimal.valueOf(100), BigDecimal.ONE, "divine", BigDecimal.ONE, sparkline ),
                    new CurrencyPrice( "augument", BigDecimal.ONE, BigDecimal.ONE, "augument", BigDecimal.ONE, sparkline )
            );


            Core core = new Core( currencies, Map.of(), "chaos", "divine" );
            PoeNinjaResponse response = new PoeNinjaResponse( core, currenciesPrice );

            when(poeNinjaClient.getCurrencyPrices("Settlers")).thenReturn(response);
            List<CurrencyPriceResponse> result = currencyService.getCurrencies( "Settlers", null, 0, 10, "orb" );
            assertEquals("divine", result.get(0).id());
            assertEquals("chaos", result.get(1).id());
            assertEquals("augument", result.get(2).id());

        }
    @Test
    void shouldReturnAllCurrenciesWhenFilterIsNull(){
        Sparkline sparkline = new Sparkline(  BigDecimal.valueOf(5),List.of() );
        List<Currency> currencies = List.of(
                new Currency(  "chaos", "Chaos Orb", "chaos.png", "currency", "chaos"),
                new Currency( "divine", "Divine Orb", "divine.png", "currency", "divine"),
                new Currency("augument", "Augument Orb", "augument.png", "augument", "augument")
        );

        List<CurrencyPrice> currenciesPrice = List.of(
                new CurrencyPrice( "chaos", BigDecimal.TEN, BigDecimal.ONE, "chaos", BigDecimal.ONE, sparkline ),
                new CurrencyPrice( "divine", BigDecimal.valueOf(100), BigDecimal.ONE, "divine", BigDecimal.ONE, sparkline ),
                new CurrencyPrice( "augument", BigDecimal.ONE, BigDecimal.ONE, "augument", BigDecimal.ONE, sparkline )
        );


        Core core = new Core( currencies, Map.of(), "chaos", "divine" );
        PoeNinjaResponse response = new PoeNinjaResponse( core, currenciesPrice );

        when(poeNinjaClient.getCurrencyPrices("Settlers")).thenReturn(response);
        List<CurrencyPriceResponse> result = currencyService.getCurrencies( "Settlers", "price", 0, 10, null );
        assertEquals(3,result.size());
        assertEquals("divine", result.get(0).id());
        assertEquals("chaos", result.get(1).id());
        assertEquals("augument", result.get(2).id());

    }
    @Test
    void shouldReturnSecondPage(){
        Sparkline sparkline = new Sparkline(  BigDecimal.valueOf(5),List.of() );
        List<Currency> currencies = List.of(
                new Currency(  "chaos", "Chaos Orb", "chaos.png", "currency", "chaos"),
                new Currency( "divine", "Divine Orb", "divine.png", "currency", "divine"),
                new Currency("augument", "Augument Orb", "augument.png", "augument", "augument")
        );

        List<CurrencyPrice> currenciesPrice = List.of(
                new CurrencyPrice( "chaos", BigDecimal.TEN, BigDecimal.ONE, "chaos", BigDecimal.ONE, sparkline ),
                new CurrencyPrice( "divine", BigDecimal.valueOf(100), BigDecimal.ONE, "divine", BigDecimal.ONE, sparkline ),
                new CurrencyPrice( "augument", BigDecimal.ONE, BigDecimal.ONE, "augument", BigDecimal.ONE, sparkline )
        );


        Core core = new Core( currencies, Map.of(), "chaos", "divine" );
        PoeNinjaResponse response = new PoeNinjaResponse( core, currenciesPrice );

        when(poeNinjaClient.getCurrencyPrices("Settlers")).thenReturn(response);

        List<CurrencyPriceResponse> result = currencyService.getCurrencies( "Settlers", "price", 1, 2, null );
        assertEquals(1,result.size());
        assertEquals("augument", result.get(0).id());

    }
    @Test
    void shouldReturnFullSecondPage(){
        Sparkline sparkline = new Sparkline(  BigDecimal.valueOf(5),List.of() );
        List<Currency> currencies = List.of(
                new Currency(  "chaos", "Chaos Orb", "chaos.png", "currency", "chaos"),
                new Currency( "divine", "Divine Orb", "divine.png", "currency", "divine"),
                new Currency("augument", "Augument Orb", "augument.png", "currency", "augument"),
                new Currency("exalted", "Exalted Orb","exalted.png","currency","exalt")
        );

        List<CurrencyPrice> currenciesPrice = List.of(
                new CurrencyPrice( "chaos", BigDecimal.TEN, BigDecimal.ONE, "chaos", BigDecimal.ONE, sparkline ),
                new CurrencyPrice( "divine", BigDecimal.valueOf(100), BigDecimal.ONE, "divine", BigDecimal.ONE, sparkline ),
                new CurrencyPrice( "augument", BigDecimal.ONE, BigDecimal.ONE, "augument", BigDecimal.ONE, sparkline ),
                new CurrencyPrice( "exalted", BigDecimal.valueOf(5), BigDecimal.ONE, "exalted", BigDecimal.ONE, sparkline )
        );


        Core core = new Core( currencies, Map.of(), "chaos", "divine" );
        PoeNinjaResponse response = new PoeNinjaResponse( core, currenciesPrice );

        when(poeNinjaClient.getCurrencyPrices("Settlers")).thenReturn(response);

        List<CurrencyPriceResponse> result = currencyService.getCurrencies( "Settlers", "price", 1, 2, null );
        assertEquals(2,result.size());
        assertEquals("exalted", result.get(0).id());
        assertEquals("augument", result.get(1).id());
    }
    }


