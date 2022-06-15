import backend.JsonParser.JsonParser;
import backend.currencies.Currency;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class FawikowyTest {
	@Test
	public void mainLikeRun() throws IOException {
		//JsonResponse s;
		//s = getJsonResponseFromURL("http://api.nbp.pl/api/exchangerates/tables/A?format=json");
		//System.out.println(s.json().readArray().getJsonObject(0).getJsonArray("rates").getJsonObject(0));
		JsonParser j = new JsonParser();
		List<Currency> cl = j.getAvailableCurrenciesList();
		System.out.println(j.getCurrencyDataFromDateRange(cl.get(0), "2022-05-06", "2022-06-06"));
		System.out.println(cl);
	}
}
