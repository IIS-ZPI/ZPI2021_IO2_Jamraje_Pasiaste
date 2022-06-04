package backend.HTTPrequester;

import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JsonResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPRequester {
	public URL getUrlFromString(String address) throws MalformedURLException {
		return new URL(address);
	}

	public JsonResponse getJsonResponseFromURL(URL url) throws IOException {
		return new JdkRequest(url).fetch().as(JsonResponse.class);
	}
}
