public class Pages {
	public int lt50Pages;
	public int lt100Pages;
	public int lt200Pages;
	public int gt200Pages;

	public int lt50Pointer;
	public int lt100Pointer;
	public int lt200Pointer;
	public int gt200Pointer;

	public String[] lt50URLS;
	public String[] lt100URLS;
	public String[] lt200URLS;
	public String[] gt200URLS;

	public void printPageInfo() {
		System.out.println(lt50Pointer + "<=" + lt50Pages);
		System.out.println(lt100Pointer + "<=" + lt100Pages);
		System.out.println(lt200Pointer + "<=" + lt200Pages);
		System.out.println(gt200Pointer + "<=" + gt200Pages);
	}

	private static Pages instance = null;

	public static Pages getInstance(int a, int b, int c, int d) {
		if (instance == null) {
			instance = new Pages(a, b, c, d);
		}
		return instance;
	}

	public static Pages getInstance() {
		if (instance == null) {
			return null;
		}
		return instance;
	}

	private Pages(int a, int b, int c, int d) {
		lt50Pages = (int) Math.ceil(a / 100.0);
		lt100Pages = (int) Math.ceil((b - a) / 100.0);
		lt200Pages = (int) Math.ceil((c - b) / 100.0);
		gt200Pages = (int) Math.ceil(d / 100.0);

		lt50Pointer = lt50Pages;
		lt100Pointer = lt100Pages;
		lt200Pointer = lt200Pages;
		gt200Pointer = gt200Pages;

		lt50URLS = new String[lt50Pages];
		lt100URLS = new String[lt100Pages];
		lt200URLS = new String[lt200Pages];
		gt200URLS = new String[gt200Pages];

	}

	public int getNumberOfPages(int range) {
		switch (range) {
		case 50:
			return lt50Pages;
		case 100:
			return lt100Pages;
		case 200:
			return lt200Pages;
		case 201:
			return gt200Pages;
		default:
			return 0;
		}
	}

	public String getNextPage(int range) {
		String rangeString;
		switch (range) {
		case 201:
			if (gt200Pointer > 0) {
				rangeString = "&filters={\"priceFacet\":\"$200.00 and Over\"}";
				return ("&page=" + gt200Pointer-- + rangeString);
			}
		case 200:
			if (lt200Pointer > 0) {
				rangeString = "&filters={\"priceFacet\":\"$200.00 and Under\"}";
				return ("&page=" + lt200Pointer-- + rangeString);
			}
		case 100:
			if (lt100Pointer > 0) {
				rangeString = "&filters={\"priceFacet\":\"$100.00 and Under\"}";
				return ("&page=" + lt100Pointer-- + rangeString);
			}
		case 50:
			if (lt50Pointer > 0) {
				rangeString = "&filters={\"priceFacet\":\"$50.00 and Under\"}";
				return ("&page=" + lt50Pointer-- + rangeString);
			}
		default:
			return "fail";
		}
	}

	public void LoadURLs() {
		// String url = "";
		//
		// String pageString = null;
		// //
		// // lt50Pages;
		// // lt100Pages;
		// // lt200Pages;
		// // gt200Pages;
		//
		// pageString = this.getNextPage(lt50Pages);
		// for(int i=0;i<= this.lt50Pages;i++) {
		// pageString.compareTo("fail")
		// this.lt50URLS[] = "http://api.zappos.com/Search/term/all?limit=100"
		// + pageString + "&sort={\"price\":\"asc\"}"
		// + Configuration.APIKey;
		// }
	}

	public String URLCreation(int range) {
		String url = "";
		String pageString = this.getNextPage(range);

		if (pageString.compareTo("fail") == 0) {
			return "fail";
		} else {
			url = "http://api.zappos.com/Search/term/all?limit=100"
					+ pageString + "&sort={\"price\":\"asc\"}"
					+ Configuration.APIKey;
			return url;
		}
	}

}
