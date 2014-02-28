public class PagesInfo {
	public int lt50Pages;
	public int lt100Pages;
	public int lt200Pages;
	public int gt200Pages;

	public int lt50Pointer;
	public int lt100Pointer;
	public int lt200Pointer;
	public int gt200Pointer;

	public void printPageInfo() {

		System.out.println(lt50Pointer + "<=" + lt50Pages);
		System.out.println(lt100Pointer + "<=" + lt100Pages);
		System.out.println(lt200Pointer + "<=" + lt200Pages);
		System.out.println(gt200Pointer + "<=" + gt200Pages);

	}

	public PagesInfo(int a, int b, int c, int d) {
		lt50Pages = (int) Math.ceil(a / 100.0);
		lt100Pages = (int) Math.ceil((b - a) / 100.0);
		lt200Pages = (int) Math.ceil((c - b) / 100.0);
		gt200Pages = (int) Math.ceil(d / 100.0);

		lt50Pointer = lt50Pages;
		lt100Pointer = lt100Pages;
		lt200Pointer = lt200Pages;
		gt200Pointer = gt200Pages;

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
