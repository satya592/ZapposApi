import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Zappos {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException,
			JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	public static void main(String[] args) throws IOException, JSONException {
		JSONObject json = readJsonFromUrl("http://api.zappos.com/Product/7515478?includes=[%22styles%22]&key=a73121520492f88dc3d33daf2103d7574f1a3166");
		System.out.println(json.toString());
		if (json.get("statusCode").toString().compareTo("200") == 0) {
			JSONArray results = json.getJSONArray("product");
			for (int i = 0; i < results.length(); i++) {
				System.out.println(((JSONObject) results.get(i)).get("price"));
				System.out.println(((JSONObject) results.get(i))
						.get("productUrl"));
			}
		} else {
			System.out.println("Failed to connect, please try again");
		}
	}
}