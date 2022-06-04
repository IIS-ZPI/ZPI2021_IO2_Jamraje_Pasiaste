import com.jcabi.http.Request;
import com.jcabi.http.request.JdkRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println(new String(new JdkRequest(new URL("http://api.nbp.pl/api/exchangerates/tables/A")).fetch().binary()));
	}
}
