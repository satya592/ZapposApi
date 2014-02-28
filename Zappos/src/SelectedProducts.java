import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SelectedProducts {

	Product[] products;

	SelectedProducts(String url) {
		try {
			products = this.getProducts(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Product[] getProducts(String url) throws IOException, JSONException {
		JSONObject json = ReadFromURL.readJsonFromUrl(url);
		int ResultCount;
		Product[] setOfProducts = null;
		if (json.get("statusCode").toString().compareTo("200") == 0) {
			ResultCount = Integer.parseInt(json.get("currentResultCount")
					.toString());
			JSONArray results = json.getJSONArray("results");

			setOfProducts = new Product[ResultCount];

			for (int i = 0; i < ResultCount; i++) {

				Object obj = results.get(i);

				setOfProducts[i].price = Double
						.parseDouble(((String) ((JSONObject) obj).get("price"))
								.replaceAll("[^\\d.]+", ""));
				setOfProducts[i].originalPrice = Double
						.parseDouble(((String) ((JSONObject) obj)
								.get("originalPrice")).replaceAll("[^\\d.]+",
								""));
				setOfProducts[i].percentOff = (String) (((JSONObject) obj)
						.get("percentOff"));
				setOfProducts[i].productId = (String) (((JSONObject) obj)
						.get("productId"));
				setOfProducts[i].styleId = (String) (((JSONObject) obj)
						.get("styleId"));
				setOfProducts[i].colorId = (String) (((JSONObject) obj)
						.get("colorId"));
				setOfProducts[i].brandName = (String) (((JSONObject) obj)
						.get("brandName"));
				setOfProducts[i].productName = (String) (((JSONObject) obj)
						.get("productName"));
				setOfProducts[i].productUrl = (String) (((JSONObject) obj)
						.get("productUrl"));
			}
		} else {
			System.out.println("Failed to connect, please try again");
		}
		return setOfProducts;
	}

}
