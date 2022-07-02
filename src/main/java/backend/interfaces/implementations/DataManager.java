package backend.interfaces.implementations;

import backend.JsonParser.JsonParser;
import backend.currencies.Currency;
import backend.interfaces.DataGetter;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class DataManager implements DataGetter {
	private final JsonParser parser = new JsonParser();

	@Override
	public List<Currency> getAllCurrencies() throws IOException, ParseException {
		return parser.getAvailableCurrenciesList();
	}

	@Override
	public List<Currency> getForPeriod(Currency currency, String startDate, String endDate) throws IOException, ParseException {
		return parser.getCurrencyDataFromDateRange(currency, startDate, endDate);
	}

	@Override
	public Currency getForDate(Currency currency, String date) throws IOException, ParseException {
		return parser.getSingleDateData(currency, date);
	}
}
