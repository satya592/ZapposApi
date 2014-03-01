public class Pages {
	public int lt50Pages;
	public int lt100Pages;
	public int lt200Pages;
	public int mt200Pages;

	public Pages(int a, int b, int c, int d) {
		lt50Pages = a / 100;
		lt100Pages = (b - a) / 100;
		lt200Pages = (c - b) / 100;
		mt200Pages = d / 100;
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
			return mt200Pages;
		default:
			return 0;
		}
	}
}
