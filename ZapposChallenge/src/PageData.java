public class PageData {
	private double max;
	private double min;
	private String data;

	public PageData(double a, double b, String c) {
		max = a;
		min = b;
		data = c;
	}

	public double getMax() {
		return max;
	}

	public double getMin() {
		return min;
	}

	public String getData() {
		return data;
	}
}
