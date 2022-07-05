package backend.JsonParser;

import backend.currencies.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.stream.JsonParsingException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppBackendTest {
    JsonParser testParser;
    Currency mockCurrency;
    String startDate;
    String faultyDate;
    LocalDateTime now;
    String correctDate;

    @BeforeEach
    void setUp() throws ParseException {
        testParser = new JsonParser();
        now = LocalDateTime.now();
        mockCurrency = Currency.builder()
                .date("2022-07-05")
                .table("A")
                .name("dolar amerykański")
                .code("USD")
                .value("4.5947")
                .valueAsDouble(Double.parseDouble("4.5947"))
                .dateAsDateObject(new SimpleDateFormat("yyyy-MM-dd").parse("2022-07-05"))
                .build();
        startDate = now.minusDays(6).toString().substring(0,10);
        faultyDate = now.plusDays(1).toString().substring(0,10);
        correctDate = "2022-07-05";
    }


    @Test
    void getCurrencyDataFromDateRange_TestForFutureDate() {
        assertThrows(JsonParsingException.class,
                ()-> testParser.getCurrencyDataFromDateRange(mockCurrency, startDate, faultyDate));

    }

    @Test
    void getSingleDateData_TestForFutureDate() {
        assertThrows(JsonParsingException.class,
                ()-> testParser.getSingleDateData(mockCurrency, faultyDate));
    }

    @Test
    void getSingleDateData_TestForCorrectDate() throws IOException, ParseException {
        correctDate = "2022-07-05";
        Currency result = testParser.getSingleDateData(mockCurrency, correctDate);
        assertEquals(result, mockCurrency);
    }

    @Test
    void getCurrencyDataFromDateRange_TestForCorrectListSize() throws IOException, ParseException {
        List<Currency> result = testParser.getCurrencyDataFromDateRange(mockCurrency, correctDate, correctDate);
        assertEquals(1, result.size());
    }
}