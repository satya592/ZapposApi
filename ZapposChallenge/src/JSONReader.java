import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class JSONReader {

	public JSONReader() {
	}

	public JSONArray extractArrayFromString(String result, String key) {
		Object obj = JSONValue.parse(result);
		return (JSONArray) (((JSONObject) obj).get(key));
	}

	public JSONArray extractArrayFromArray(JSONArray array, String key) {
		Object obj = (Object) array.get(0);
		return (JSONArray) (((JSONObject) obj).get(key));
	}

}
