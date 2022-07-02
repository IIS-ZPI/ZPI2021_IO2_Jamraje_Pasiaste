package backend.interfaces;

import backend.currencies.Currency;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface DataGetter {
	List<Currency> getAllCurrencies() throws IOException, ParseException;
	List<Currency> getForPeriod(Currency currency, String startDate, String endDate) throws IOException, ParseException;
	Currency getForDate(Currency currency, String date) throws IOException, ParseException;
}
