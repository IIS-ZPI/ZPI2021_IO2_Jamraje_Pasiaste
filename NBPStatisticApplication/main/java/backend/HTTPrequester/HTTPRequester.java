package backend.HTTPrequester;

import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JsonResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPRequester {
	public static JsonResponse getJsonResponseFromURL(String address) throws IOException {
		return new JdkRequest(getUrlFromString(address)).fetch().as(JsonResponse.class);
	}

	public static JsonResponse getJsonResponseFromURL(URL address) throws IOException {
		return new JdkRequest(address).fetch().as(JsonResponse.class);
	}

	public static URL getUrlFromString(String address) throws MalformedURLException {
		return new URL(address);
	}
}
