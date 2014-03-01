enum PageRange {
	LT50PAGES(50), LT100PAGES(100), LT200PAGES(200), GT200PAGES(201);
	private int page;

	PageRange(int p) {
		page = p;
	}

	int getPRange() {
		return page;
	}
}

public class Configuration {
	// Parameters for Scheduling
	// public static int NoOfProcessors = 2;
	// public static int HyperPeriod = -1;
	// public static SchedulingAlgorithm TypeOfScheduliing =
	// SchedulingAlgorithm.EDF; // P-Edf,P-Rm,Edf,Rm
	// SchedulingAlgorithm.EDF; // P-Edf,P-Rm,Edf,Rm
	// public static double TickSize = 1;
	//
	// // Parameters for Task-Set Generations
	// public static int TasksInTestTaskSet = 10;
	public static int numberOfItems;
	public static double userPrice;
	public static final int N50 = 50;
	public static final int N100 = 100;
	public static final int N200 = 200;
	public static final int N201 = 201;
	public static String APIKey = "&key=52ddafbe3ee659bad97fcce7c53592916a6bfd73";

}
