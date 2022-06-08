package backend.JsonParser;

import backend.HTTPrequester.HTTPRequester;
import backend.currencies.Currency;
import com.jcabi.http.response.JacksonResponse;
import com.jcabi.http.response.JsonResponse;
import lombok.val;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static backend.HTTPrequester.HTTPRequester.getJsonResponseFromURL;

public class JsonParser {
    //todo work in progress
    private final static List<String> tables = List.of("http://api.nbp.pl/api/exchangerates/tables/A?format=json", "http://api.nbp.pl/api/exchangerates/tables/B?format=json", "http://api.nbp.pl/api/exchangerates/tables/C?format=json");

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

    public List<Currency> getAvailableCurrenciesList() throws IOException {
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
                currencyList.add(new Currency(tableName, temp[3], temp[7], temp[10], effectiveDate));
            }
        }
        return currencyList;
    }

    public List<Currency> getCurrencyDataFromDateRange(Currency currency, String startDate, String endDate) throws IOException {
        String addressToBeCalled = "http://api.nbp.pl/api/exchangerates/rates/"
                + currency.getTable()
                + "/"
                + currency.getCode()
                + "/"
                + startDate
                + "/"
                + endDate
                + "/?format=json";
        JsonResponse jsonResponse = HTTPRequester.getJsonResponseFromURL(addressToBeCalled);
        JsonArray ratesArray = jsonResponse.json().readObject().getJsonArray("rates");
        int i = 0;
        List<Currency> currencyList = new ArrayList<>();
        Currency currencyToAdd;
        try {
            while (true) {
                currencyToAdd = new Currency(currency);
                currencyToAdd.setValue(String.valueOf(ratesArray.getJsonObject(i).getJsonNumber("mid")));
                currencyToAdd.setDate(String.valueOf(ratesArray.getJsonObject(i++).getJsonString("effectiveDate")));
                currencyList.add(currencyToAdd);
            }
        }catch(IndexOutOfBoundsException e){
            //exception jest warunkiem wyjscia z petli
            //ignore
        }

        return currencyList;
    }

    public Currency getSingleDateData(Currency currency, String date) throws IOException {
        String addressToBeCalled = "http://api.nbp.pl/api/exchangerates/rates/" + currency.getTable() + "/" + currency.getCode() + "/" + date + "/?format=json";
        JsonResponse jsonResponse = HTTPRequester.getJsonResponseFromURL(addressToBeCalled);
        JsonArray ratesArray = jsonResponse.json().readObject().getJsonArray("rates");
        String value = String.valueOf(ratesArray.getJsonObject(0).getJsonNumber("mid"));
        Currency currencyToBeReturned = new Currency(currency);
        currencyToBeReturned.setDate(date);
        currencyToBeReturned.setValue(value);

        return currencyToBeReturned;
    }

}
