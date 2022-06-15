package backend.interfaces;

import backend.currencies.Currency;

import java.io.IOException;
import java.util.List;

public interface DataGetter {
	List<Currency> getAllCurrencies() throws IOException;
	List<Currency> getForPeriod(Currency currency, String startDate, String endDate) throws IOException;
	Currency getForDate(Currency currency, String date) throws IOException;
}
