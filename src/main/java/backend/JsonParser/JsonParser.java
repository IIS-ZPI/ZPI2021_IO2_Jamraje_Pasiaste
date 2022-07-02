package backend.JsonParser;


import backend.HTTPrequester.HTTPRequester;
import backend.currencies.Currency;
import com.jcabi.http.response.JsonResponse;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static backend.HTTPrequester.HTTPRequester.getJsonResponseFromURL;

public class JsonParser {
	//, "http://api.nbp.pl/api/exchangerates/tables/C?format=json" <- I heard we are not using this table
	private final static List<String> tables = List.of("http://api.nbp.pl/api/exchangerates/tables/A?format=json", "http://api.nbp.pl/api/exchangerates/tables/B?format=json");
	private final static String rates = "http://api.nbp.pl/api/exchangerates/rates/";

	public String getCurrency(JsonResponse response) {

		return response.json().readArray().toString();
	}

	public JsonObject getJsonObject(JsonResponse response) {
		return response.json().readObject();
	}

	public JsonObject getJsonObject(JsonArray response, int index) {
		return response.getJsonObject(index);
	}

	public JsonArray getJsonArray(JsonResponse response) {
		return response.json().readArray();
	}

	public List<Currency> getAvailableCurrenciesList() throws IOException, ParseException {
		ArrayList<Currency> currencyList = new ArrayList<>();
		JsonResponse jsonResponse;
		String tableName;
		String effectiveDate;
		String[] temp;

		for (String table : tables) {
			jsonResponse = getJsonResponseFromURL(table);

			tableName = String.valueOf(jsonResponse
					.json()
					.readArray()
					.getJsonObject(0)
					.getJsonString("table")
			).replaceAll("\"", "");

			effectiveDate = String.valueOf(jsonResponse
					.json()
					.readArray()
					.getJsonObject(0)
					.getJsonString("effectiveDate")
			).replaceAll("\"", "");

			for (JsonValue array : jsonResponse.json().readArray().getJsonObject(0).getJsonArray("rates")) {
				temp = array.toString()
						.replaceAll("[\\{:,}]", "")
						.split("\"");
				currencyList.add(new Currency(tableName, temp[3], temp[7], temp[10], effectiveDate, Double.valueOf(temp[10]),new SimpleDateFormat("yyyy-MM-dd").parse(effectiveDate)));
			}
		}
		return currencyList;
	}

	public List<Currency> getCurrencyDataFromDateRange(Currency currency, String startDate, String endDate) throws IOException, ParseException {
		JsonArray ratesArray = getJsonArray(currency, startDate, endDate);

		return getListFromJsonArray(currency, ratesArray);
	}

	private JsonArray getJsonArray(Currency currency, String startDate, String endDate) throws IOException {
		String addressToBeCalled = rates
				+ currency.getTable()
				+ "/"
				+ currency.getCode()
				+ "/"
				+ startDate
				+ "/"
				+ endDate
				+ "/?format=json";
		JsonResponse jsonResponse = HTTPRequester.getJsonResponseFromURL(addressToBeCalled);
		return jsonResponse.json().readObject().getJsonArray("rates");
	}

	private List<Currency> getListFromJsonArray(Currency currency, JsonArray ratesArray) throws ParseException {
		int i = 0;
		List<Currency> currencyList = new ArrayList<>();
		try {
			while (true) {
				currencyList.add(
						currency.toBuilder()
								.value(ratesArray.getJsonObject(i).getJsonNumber("mid").toString())
								.valueAsDouble(Double.valueOf(ratesArray.getJsonObject(i).getJsonNumber("mid").toString()))
								.date(ratesArray.getJsonObject(i++).getJsonString("effectiveDate").getString())
								.dateAsDateObject(new SimpleDateFormat("yyyy-MM-dd").parse(ratesArray.getJsonObject(i).getJsonString("effectiveDate").getString()))
								.build()
				);
			}
		} catch (IndexOutOfBoundsException e) {
			//exception jest warunkiem wyjscia z petli
			//ignore
		}
		return currencyList;
	}

	public Currency getSingleDateData(Currency currency, String date) throws IOException, ParseException {
		String addressToBeCalled = "http://api.nbp.pl/api/exchangerates/rates/" + currency.getTable() + "/" + currency.getCode() + "/" + date + "/?format=json";
		JsonResponse jsonResponse = HTTPRequester.getJsonResponseFromURL(addressToBeCalled);
		JsonArray ratesArray = jsonResponse.json().readObject().getJsonArray("rates");
		String value = ratesArray.getJsonObject(0).getJsonNumber("mid").toString();
		return currency.toBuilder()
				.date(date)
				.value(value)
				.valueAsDouble(Double.valueOf(value))
				.dateAsDateObject(new SimpleDateFormat("yyyy-MM-dd").parse(date))
				.build();
	}

}
