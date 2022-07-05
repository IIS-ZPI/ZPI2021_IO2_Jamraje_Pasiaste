package tests;

import backend.JsonParser.JsonParser;
import backend.currencies.Currency;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class FawikowyTest {
	public static void main(String[] args) throws IOException, ParseException {
		test();
	}
	public static void test() throws IOException, ParseException {
		JsonParser j = new JsonParser();
		List<Currency> cl = j.getAvailableCurrenciesList();
		System.out.println(j.getCurrencyDataFromDateRange(cl.get(0), "2022-05-06", "2022-06-06"));
		System.out.println(cl);
	}
}