import org.json.JSONArray;

public class PagesInfo {

	double maxPrice;
	double minPrice;

	public void printPageInfo() {
		// System.out.println(lt50Pointer + "<=" + lt50Pages);
		// System.out.println(lt100Pointer + "<=" + lt100Pages);
		// System.out.println(lt200Pointer + "<=" + lt200Pages);
		// System.out.println(gt200Pointer + "<=" + gt200Pages);
	}

	public PagesInfo(int pageNo, int range) {
		String url, result;
		JSONArray array;
		Object obj;
		double max, min;

		url = Pages.getInstance().URLCreation(range);
		// urlCreator.create(currentPage,range);
		result = dataProvider.query(url);
		array = jsonHelper.extractArrayFromString(result, "results");
		obj = array.get(0);
		max = parsePrice(obj);
		obj = array.get(array.size() - 1);
		min = parsePrice(obj);

		return new CacheEntry(max, min, result);
	}
}
// public int getNumberOfPages(int range) {
// switch (range) {
// case 50:
// return lt50Pages;
// case 100:
// return lt100Pages;
// case 200:
// return lt200Pages;
// case 201:
// return gt200Pages;
// default:
// return 0;
// }
// }
//
// public String getNextPage(int range) {
// String rangeString;
// switch (range) {
// case 201:
// if (gt200Pointer > 0) {
// rangeString = "&filters={\"priceFacet\":\"$200.00 and Over\"}";
// return ("&page=" + gt200Pointer-- + rangeString);
// }
// case 200:
// if (lt200Pointer > 0) {
// rangeString = "&filters={\"priceFacet\":\"$200.00 and Under\"}";
// return ("&page=" + lt200Pointer-- + rangeString);
// }
// case 100:
// if (lt100Pointer > 0) {
// rangeString = "&filters={\"priceFacet\":\"$100.00 and Under\"}";
// return ("&page=" + lt100Pointer-- + rangeString);
// }
// case 50:
// if (lt50Pointer > 0) {
// rangeString = "&filters={\"priceFacet\":\"$50.00 and Under\"}";
// return ("&page=" + lt50Pointer-- + rangeString);
// }
// default:
// return "fail";
// }
// }
//
// public String URLCreation(int range) {
// String url = "";
// String pageString = this.getNextPage(range);
//
// if (pageString.compareTo("fail") == 0) {
// return "fail";
// } else {
// url = "http://api.zappos.com/Search/term/all?limit=100"
// + pageString + "&sort={\"price\":\"asc\"}"
// + Configuration.APIKey;
// return url;
// }
// }
//
// }
