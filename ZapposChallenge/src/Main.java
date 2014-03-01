import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class Main {

	public static DataProvider dataProvider = new DataProvider();
	public static URLReader urlCreator = new URLReader();
	public static JSONReader jsonHelper = new JSONReader();
	public static Pages pages;

	public static PageInfo cacheLT50 = new PageInfo();
	public static PageInfo cacheLT100 = new PageInfo();
	public static PageInfo cacheLT200 = new PageInfo();
	public static PageInfo cacheMT200 = new PageInfo();

	public static void main(String[] args) {
		String result;
		JSONArray array;
		Object obj;

		/* Construct the schema of the price brackets */
		result = dataProvider
				.query("http://api.zappos.com/Search/term/all?limit=100&facets=[%22priceFacet%22]&excludes=[%22results%22]&key=52ddafbe3ee659bad97fcce7c53592916a6bfd73");
		array = jsonHelper.extractArrayFromString(result, "facets");
		array = jsonHelper.extractArrayFromArray(array, "values");
		int lt50, lt100, lt200, mt200;
		obj = array.get(0);
		lt50 = Integer.parseInt((String) ((JSONObject) obj).get("count"));
		obj = array.get(1);
		lt100 = Integer.parseInt((String) ((JSONObject) obj).get("count"));
		obj = array.get(2);
		lt200 = Integer.parseInt((String) ((JSONObject) obj).get("count"));
		obj = array.get(3);
		mt200 = Integer.parseInt((String) ((JSONObject) obj).get("count"));
		pages = new Pages(lt50, lt100, lt200, mt200);

		/* Get the input from the user */
		Scanner keyboard = new Scanner(System.in);
		int numberOfItems;
		double targetPrice;
		System.out
				.print("Please enter the total number of items you would like: ");
		numberOfItems = keyboard.nextInt();
		System.out
				.print("Please enter the total price you would like to pay: ");
		targetPrice = keyboard.nextDouble();

		/*
		 * Randomly assign values to the items and find items close to their
		 * price in a search
		 */
		int i;
		ArrayList<Product> products = new ArrayList<Product>();
		Random rand = new Random();
		double remaining = targetPrice;
		for (i = 0; i < numberOfItems; i++) {
			double tmp;
			if (remaining / (numberOfItems - i) > 6) {
				tmp = (double) rand
						.nextInt((int) (remaining - (6 * (numberOfItems - i))));
				/* these can be removed, i just got tired of huge prices */
				if (tmp > (remaining / 2))
					tmp = tmp / 2;
				if (tmp < 6)
					tmp = 6;
				if (numberOfItems - i == 1)
					tmp = remaining;
			} else {
				tmp = 6.0;
			}

			products.add(searchByPrice(tmp));
			remaining = remaining - products.get(i).price;
		}

		double total = 0;
		for (i = 0; i < products.size(); i++) {
			Product tmp = products.get(i);
			total = total + tmp.price;
			System.out.println("Product " + (i + 1) + " ($" + tmp.price + ")"
					+ ": " + tmp.brandName + " " + tmp.productName + " URL: "
					+ tmp.productUrl);
		}

		System.out.println("Expected total: " + targetPrice);
		System.out.println("Discovered total: " + total);
	}

	public static Product searchByPrice(double price) {
		if (price < 50) {
			return binaryAPICalls(price, 50);
		} else if (price > 50 && price < 100) {
			return binaryAPICalls(price, 100);
		} else if (price > 100 && price < 200) {
			return binaryAPICalls(price, 200);
		} else {
			return binaryAPICalls(price, 201);
		}

	}

	public static Product binaryAPICalls(double price, int range) {
		int highPage, lowPage, currentPage, numberOfPages;
		PageData highCache, lowCache, currentCache;

		numberOfPages = pages.getNumberOfPages(range);
		currentPage = numberOfPages / 2;

		/* Get the high and current Cache */
		if (!getCache(range).check(currentPage)) {
			currentCache = createCacheEntry(currentPage, range);
			getCache(range).addCacheEntry(currentPage, currentCache);
			highCache = currentCache;
		} else {
			currentCache = getCache(range).getCacheEntry(currentPage);
			highCache = currentCache;
		}
		highPage = 0;

		/* Get the low cache */
		if (!getCache(range).check(numberOfPages)) {
			lowCache = createCacheEntry(currentPage, range);
			getCache(range).addCacheEntry(currentPage, lowCache);
		} else {
			lowCache = getCache(range).getCacheEntry(currentPage);
		}
		lowPage = numberOfPages;

		while ((!(price <= currentCache.getMax() && price >= currentCache
				.getMin()))
				&& (!((currentPage - highPage == 1) && (lowPage - currentPage == 1)))) {

			if (price > currentCache.getMax()) {
				lowPage = currentPage;
				currentPage = (currentPage + highPage) / 2;
				lowCache = currentCache;
			} else if (price < currentCache.getMin()) {
				highPage = currentPage;
				currentPage = (currentPage + lowPage) / 2;
				highCache = currentCache;
			}

			if (getCache(range).check(currentPage)) {
				currentCache = getCache(range).getCacheEntry(currentPage);
			} else {
				currentCache = createCacheEntry(currentPage, range);
				getCache(range).addCacheEntry(currentPage, currentCache);
			}
		}

		return binaryArraySearch(price, currentCache);
	}

	public static Product binaryArraySearch(double price, PageData cache) {
		int highPos = 0;
		int currentPos = 50;
		int lowPos = 99;
		JSONArray array = jsonHelper.extractArrayFromString(cache.getData(),
				"results");
		double currentPrice = getProductPrice(currentPos, array);
		while ((currentPrice != price)
				&& ((currentPos - 1 != highPos) && (currentPos + 1 != lowPos))) {
			if (price > currentPrice) {
				lowPos = currentPos;
				currentPos = (currentPos + highPos) / 2;
			} else if (price < currentPrice) {
				highPos = currentPos;
				currentPos = (currentPos + lowPos) / 2;
			}

			currentPrice = getProductPrice(currentPos, array);
		}

		return createProduct(currentPos, array);
	}

	public static double parsePrice(Object obj) {
		return Double.parseDouble(((String) ((JSONObject) obj).get("price"))
				.replaceAll("[^\\d.]+", ""));
	}

	public static double getProductPrice(int pos, JSONArray array) {
		Object obj = array.get(pos);
		return parsePrice(obj);
	}

	public static PageInfo getCache(int range) {
		switch (range) {
		case 50:
			return cacheLT50;
		case 100:
			return cacheLT100;
		case 200:
			return cacheLT200;
		case 201:
			return cacheMT200;
		default:
			return cacheLT50; /* This will never ever happen */
		}
	}

	public static PageData createCacheEntry(int currentPage, int range) {
		String url, result;
		JSONArray array;
		Object obj;
		double max, min;

		url = urlCreator.create(currentPage, range);
		result = dataProvider.query(url);
		array = jsonHelper.extractArrayFromString(result, "results");
		obj = array.get(0);
		max = parsePrice(obj);
		obj = array.get(array.size() - 1);
		min = parsePrice(obj);

		return new PageData(max, min, result);
	}

	public static Product createProduct(int pos, JSONArray array) {
		Product rv = new Product();
		Object obj = array.get(pos);

		rv.price = parsePrice(obj);
		rv.originalPrice = Double.parseDouble(((String) ((JSONObject) obj)
				.get("originalPrice")).replaceAll("[^\\d.]+", ""));
		rv.percentOff = (String) (((JSONObject) obj).get("percentOff"));
		rv.productId = Integer.parseInt((String) (((JSONObject) obj)
				.get("productId")));
		rv.styleId = Integer.parseInt((String) (((JSONObject) obj)
				.get("styleId")));
		rv.colorId = Integer.parseInt((String) (((JSONObject) obj)
				.get("colorId")));
		rv.brandName = (String) (((JSONObject) obj).get("brandName"));
		rv.productName = (String) (((JSONObject) obj).get("productName"));
		rv.productUrl = (String) (((JSONObject) obj).get("productUrl"));

		return rv;
	}
}
