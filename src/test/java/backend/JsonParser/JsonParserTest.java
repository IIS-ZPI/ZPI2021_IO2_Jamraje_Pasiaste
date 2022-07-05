package backend.JsonParser;

import backend.currencies.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {
    JsonParser testParser;
    Currency mockCurrency;
    String startDate;
    String endDate;
    LocalDateTime now;

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
        endDate = now.plusDays(1).toString().substring(0,10);
    }


    @Test
    void getCurrencyDataFromDateRange_TestForFutureDate() throws IOException, ParseException {

        List<Currency> result = testParser.getCurrencyDataFromDateRange(mockCurrency, startDate, endDate);
        assertEquals(5, result.size());

    }

    @Test
    void getSingleDateData_TestForFutureDate() throws IOException, ParseException {
        Currency result = testParser.getSingleDateData(mockCurrency, endDate);
        assertNotNull(result);
    }
}