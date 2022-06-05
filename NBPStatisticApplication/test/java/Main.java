import backend.JsonParser.JsonParser;
import com.jcabi.http.response.JsonResponse;

import java.io.IOException;

import static backend.HTTPrequester.HTTPRequester.getJsonResponseFromURL;

public class Main {
	public static void main(String[] args) throws IOException {
		//JsonResponse s;
		//s = getJsonResponseFromURL("http://api.nbp.pl/api/exchangerates/tables/A?format=json");
		//System.out.println(s.json().readArray().getJsonObject(0).getJsonArray("rates").getJsonObject(0));
		JsonParser j = new JsonParser();
		System.out.println(j.getAvailableCurrenciesList());
	}
}
