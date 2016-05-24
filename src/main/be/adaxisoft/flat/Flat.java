package be.adaxisoft.flat;

import java.util.Iterator;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Flat {
	
	public static final String DEFAULT_DELIMITER = ".";
	
	public static String flatten(JSONObject jsonObject) throws JSONException {
		return flatten(jsonObject, DEFAULT_DELIMITER);
	}
	
	public static String flatten(String jsonString) throws JSONException {
		return flatten(jsonString, DEFAULT_DELIMITER);
	}
	
	public static String flatten(JSONObject jsonObject, String delimiter) throws JSONException {
		String s = "{" + flatten(null, jsonObject, delimiter) + "}";
		return s;
	}

	public static String flatten(String jsonString, String delimiter) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonString);
		return flatten(jsonObject, delimiter);
	}
	
	public static JSONObject flattenToJSONObject(String jsonString)  throws JSONException {
		String flatJsonString = flatten(jsonString);
		return new JSONObject(flatJsonString);
	}
	
    public static JSONObject flattenToJSONObject(JSONObject jsonObject)  throws JSONException {
		String flatJsonString = flatten(jsonObject);
		return new JSONObject(flatJsonString);
	}
	
	public static JSONObject flattenToJSONObject(String jsonString, String delimiter)  throws JSONException {
		String flatJsonString = flatten(jsonString, delimiter);
		return new JSONObject(flatJsonString);
	}
	
    public static JSONObject flattenToJSONObject(JSONObject jsonObject, String delimiter)  throws JSONException {
		String flatJsonString = flatten(jsonObject, delimiter);
		return new JSONObject(flatJsonString);
	}

	private static String flatten(String parent, Object value, String delimiter) throws JSONException {
		StringBuilder sb = new StringBuilder();
		
		if (value instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) value;
			for (Iterator<String> i = jsonObject.keys(); i.hasNext();) {
				String key = i.next();
				if (key.contains(delimiter)) {
					throw new IllegalArgumentException("A key cannot contain the delimiter");
				}
				String hkey = (parent == null) ? key : parent + delimiter + key;
				Object jval = jsonObject.get(key);
				String json = flatten(hkey, jval, delimiter);
				sb.append(json);
				if (i.hasNext()) {
					sb.append(",");
				}
			}
		} else if (value instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray) value;
			for (int i = 0; i < jsonArray.length(); i++) {
				String hkey = (parent == null) ? "" + i : parent + delimiter + i;
				Object aval = jsonArray.get(i);
				String json = flatten(hkey, aval, delimiter);
				sb.append(json);
				if (i < jsonArray.length() - 1) {
					sb.append(",");
				}
			}
		} else if (value instanceof String) {
			sb.append("\"").append(parent).append("\"").append(":");
			String s = (String) value;
			sb.append(JSONObject.quote(s));
		} else if (value instanceof Integer) {
			sb.append("\"").append(parent).append("\"").append(":");
			Integer integer = (Integer) value;
			sb.append(integer);
		}

		return sb.toString();
	}
	
	
	public static String unflatten(String flatJsonString) throws JSONException {
		return unflatten(flatJsonString, DEFAULT_DELIMITER);
	}
	
	public static String unflatten(JSONObject flatJsonObject) throws JSONException {
		return unflatten(flatJsonObject, DEFAULT_DELIMITER);
	}
	
	public static String unflatten(String flatJsonString, String delimiter) throws JSONException {
		JSONObject flatJsonObject = new JSONObject(flatJsonString);
		return unflatten(flatJsonObject, delimiter);
	}

	public static String unflatten(JSONObject flatJsonObject, String delimiter) throws JSONException {
		return unflattenToJSONObject(flatJsonObject, delimiter).toString();
	}
	
	public static JSONObject unflattenToJSONObject(String flatJsonObject) throws JSONException {
		return unflattenToJSONObject(flatJsonObject, DEFAULT_DELIMITER);
	}
	
	public static JSONObject unflattenToJSONObject(JSONObject flatJsonObject) throws JSONException {
		return unflattenToJSONObject(flatJsonObject, DEFAULT_DELIMITER);
	}
	
	public static JSONObject unflattenToJSONObject(String flatJsonString, String delimiter) throws JSONException {
		JSONObject flatJsonObject = new JSONObject(flatJsonString);
		return unflattenToJSONObject(flatJsonObject, delimiter);
	}

	public static JSONObject unflattenToJSONObject(JSONObject flatJsonObject, String delimiter) throws JSONException {
		JSONObject decoded = new JSONObject();

		for (Iterator<String> i = flatJsonObject.keys(); i.hasNext();) {
			String flattenedKey = i.next();
			String[] keys = flattenedKey.split(Pattern.quote(delimiter));

			Object json = decoded;
			for (int j = 0; j < keys.length; j++) {
				
				
				if (j == keys.length - 1) {
					// We are at a leaf key
					Object value = flatJsonObject.get(flattenedKey);
					if (json instanceof JSONObject) {
						JSONObject jsonObject = (JSONObject)json;
						jsonObject.put(keys[j], value);
					} else if (json instanceof JSONArray) {
						JSONArray jsonArray = (JSONArray)json;
						int index = Integer.parseInt(keys[j]);
						jsonArray.put(index, value);
					}
				} else {
					// We are *not* at a leaf key
					if (!isNumber(keys[j + 1])) {
						// next index is an object
						JSONObject childJsonObject;

						if (json instanceof JSONObject) {
							// The last index was an object: we're creating an object in an object
							JSONObject jsonObject = (JSONObject)json;
							if (jsonObject.has(keys[j])) {
								childJsonObject = jsonObject.getJSONObject(keys[j]);
							} else {
								childJsonObject = new JSONObject();
								jsonObject.put(keys[j], childJsonObject);
							}
						} else if (json instanceof JSONArray) {
							// The last index was an array: we're creating an object in an array
							JSONArray jsonArray = (JSONArray)json;
							int index = Integer.parseInt(keys[j]);
							if (!jsonArray.isNull(index)) {
								childJsonObject = jsonArray.getJSONObject(index);
							} else {
								childJsonObject = new JSONObject();
								jsonArray.put(index, childJsonObject);
							}
						} else {
							throw new AssertionError("Unhandled object type");
						}
						json = childJsonObject;
					} else {
						// The next index is an array element
						JSONArray childJsonArray;

						if (json instanceof JSONObject) {
							// The last index was an object: we're creating an array in an object
							JSONObject jsonObject = (JSONObject)json;
							if (jsonObject.has(keys[j])) {
								childJsonArray = jsonObject.getJSONArray(keys[j]);
							} else {
								childJsonArray = new JSONArray();
								jsonObject.put(keys[j], childJsonArray);
							}
						} else if (json instanceof JSONArray) {
							// The last index was an array: we're creating an array in an array
							JSONArray jsonArray = (JSONArray)json;
							int index = Integer.parseInt(keys[j + 1]);
							if (!jsonArray.isNull(index)) {
								childJsonArray = jsonArray.getJSONArray(index);
							} else {
								childJsonArray = new JSONArray();
								jsonArray.put(index, childJsonArray);
							}
						} else {
							throw new AssertionError("Unhandled object type");
						}
						json = childJsonArray;
					}
				}
			}
		}
		return decoded;
	}

	private static boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}