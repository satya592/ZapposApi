import java.util.HashMap;

public class PageInfo {
	public HashMap<Integer, PageData> map = new HashMap<Integer, PageData>();

	public PageInfo() {
	}

	public boolean check(int key) {
		return map.containsKey(key);
	}

	public PageData getCacheEntry(int key) {
		return (PageData) map.get(key);
	}

	public void addCacheEntry(int key, PageData cache) {
		map.put(key, cache);
	}
}
