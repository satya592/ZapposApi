import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Zappos {

	public static void main(String[] args) throws IOException, JSONException {
		JSONObject json = ReadFromURL
				.readJsonFromUrl("http://api.zappos.com/Search/term/all?limit=100&facets=[%22priceFacet%22]&excludes=[%22results%22]&key=b05dcd698e5ca2eab4a0cd1eee4117e7db2a10c4");
		System.out.println(json.toString());

		int pagesInfo[] = new int[4];

		if (json.get("statusCode").toString().compareTo("200") == 0) {
			JSONArray results = json.getJSONArray("facets");
			results = ((JSONObject) results.get(0)).getJSONArray("values");
			for (int i = 0; i < results.length(); i++) {
				// System.out.println(((JSONObject)
				// results.get(i)).get("count"));
				pagesInfo[i] = Integer.parseInt((String) ((JSONObject) results
						.get(i)).get("count"));
				System.out.println(pagesInfo[i]);
			}
		} else {
			System.out.println("Failed to connect, please try again");
		}

		PagesInfo pInfo = new PagesInfo(pagesInfo[0], pagesInfo[1],
				pagesInfo[2], pagesInfo[3]);

		pInfo.printPageInfo();

		// for (int i = 0; i < 70; i++) {
		// System.out.println(pInfo.URLCreation(50));
		// }

		Scanner keyboard = new Scanner(System.in);
		int numberOfItems;
		double targetPrice;
		System.out
				.println("Please enter the total number of items you would like to buy: ");
		numberOfItems = keyboard.nextInt();
		System.out
				.println("Please enter the total price you would like to pay: ");
		targetPrice = keyboard.nextDouble();

		// Dynamic programming goes here

		int range = 50;

		SelectedProducts selProducts = new SelectedProducts(
				pInfo.URLCreation(range));
	}
}