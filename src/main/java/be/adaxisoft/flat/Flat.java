package be.adaxisoft.flat;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author Gérik Bonaert <dev@adaxisoft.be>
 */
public class Flat {

	/**
	 * The default delimiter is used by methods that do not specify a delimiter.
	 * The default value of the delimiter is '.' (dot).
	 */
	public static final String DEFAULT_DELIMITER = ".";

	/**
	 * This method flattens the given JSON object.
	 * It will use the default delimiter.
	 * Your keys cannot contain the default delimiter.
	 *
	 * @param jsonObject the JSON object to flatten
	 * @return a string representation of the flattened JSON document.
	 */
	public static String flatten(JSONObject jsonObject) throws JSONException {
		return flatten(jsonObject, DEFAULT_DELIMITER);
	}

	/**
	 * This method flattens the string representation of the JSON object.
	 * It will use the default delimiter.
	 * Your keys cannot contain the default delimiter.
	 *
	 * @param jsonString the string representation of the JSON object.
	 * @return a string representation of the flattened JSON document.
	 * @throws JSONException when the given string cannot be parsed to a JSON object.
	 */
	public static String flatten(String jsonString) throws JSONException {
		return flatten(jsonString, DEFAULT_DELIMITER);
	}

	/**
	 * This method flattens the given JSON object.
	 * Your keys cannot contain the given delimiter.
	 *
	 * @param jsonObject the JSON object to flatten
	 * @param delimiter the delimiter to use when joining the keys.
	 * @return a string representation of the flattened JSON document.
	 */
	public static String flatten(JSONObject jsonObject, String delimiter) throws JSONException {
		String s = "{" + flatten(null, jsonObject, delimiter) + "}";
		return s;
	}

	/**
	 * This method flattens the string representation of the JSON object.
	 * Your keys cannot contain the given delimiter.
	 *
	 * @param jsonString the string representation of the JSON object.
	 * @param delimiter the delimiter to use when joining the keys.
	 * @return a string representation of the flattened JSON document.
	 * @throws JSONException when the given string cannot be parsed to a JSON object.
	 */
	public static String flatten(String jsonString, String delimiter) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonString);
		return flatten(jsonObject, delimiter);
	}

	/**
	 * This method flattens the string representation of the JSON object.
	 * It will use the default delimiter.
	 * Your keys cannot contain the default delimiter.
	 *
	 * @param jsonString the string representation of the JSON object.
	 * @return a flattened JSON object.
	 * @throws JSONException when the given string cannot be parsed to a JSON object.
	 */
	public static JSONObject flattenToJSONObject(String jsonString)  throws JSONException {
		String flatJsonString = flatten(jsonString);
		return new JSONObject(flatJsonString);
	}

	/**
	 * This method flattens the given JSON object.
	 * It will use the default delimiter.
	 * Your keys cannot contain the default delimiter.
	 *
	 * @param jsonObject the JSON object to flatten.
	 * @return a flattened JSON object.
	 */
	public static JSONObject flattenToJSONObject(JSONObject jsonObject)  throws JSONException {
		String flatJsonString = flatten(jsonObject);
		return new JSONObject(flatJsonString);
	}

	/**
	 * This method flattens the string representation of the JSON object.
	 * Your keys cannot contain the given delimiter.
	 *
	 * @param jsonString the string representation of the JSON object.
	 * @param delimiter the delimiter to use when joining the keys.
	 * @return a flattened JSON object.
	 * @throws JSONException when the given string cannot be parsed to a JSON object.
	 */
	public static JSONObject flattenToJSONObject(String jsonString, String delimiter)  throws JSONException {
		String flatJsonString = flatten(jsonString, delimiter);
		return new JSONObject(flatJsonString);
	}

	/**
	 * This method flattens the string representation of the JSON object.
	 * Your keys cannot contain the given delimiter.
	 *
	 * @param jsonObject the JSON object to flatten.
	 * @param delimiter the delimiter to use when joining the keys.
	 * @return a flattened JSON object.
	 */
	public static JSONObject flattenToJSONObject(JSONObject jsonObject, String delimiter)  throws JSONException {
		String flatJsonString = flatten(jsonObject, delimiter);
		return new JSONObject(flatJsonString);
	}

	/**
	 * This method flattens the string representation of the JSON object.
	 *
	 * @param parent parent element of the object.
	 * @param value object to flatten.
	 * @param delimiter the delimiter to use when joining the keys.
	 * @return a string representation of the flattened JSON object.
	 */
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


	/**
	 * This method takes the string representation of a flattened JSON object
	 * and returns it to the conventional format.
	 * It will use the default delimiter.
	 *
	 * @param flatJsonString the string representation of the JSON object.
	 * @return a string representation of a conventional JSON object.
	 * @throws JSONException when the given string cannot be parsed to a JSON object.
	 */
	public static String unflatten(String flatJsonString) throws JSONException {
		return unflatten(flatJsonString, DEFAULT_DELIMITER);
	}

	/**
	 * This method takes a flattened JSON object and returns it to
	 * the conventional format.
	 * It will use the default delimiter.
	 *
	 * @param flatJsonObject the JSON object to unflatten.
	 * @return a string representation of a conventional JSON object.
	 */
	public static String unflatten(JSONObject flatJsonObject) throws JSONException {
		return unflatten(flatJsonObject, DEFAULT_DELIMITER);
	}

	/**
	 * This method takes the string representation of a flattened JSON object
	 * and returns it to the conventional format.
	 * It will use the given delimiter.
	 *
	 * @param flatJsonString the JSON object to unflatten.
	 * @param delimiter the delimiter to use when splitting the keys.
	 * @return a string representation of a conventional JSON object.
	 * @throws JSONException when the given string cannot be parsed to a JSON object.
	 */
	public static String unflatten(String flatJsonString, String delimiter) throws JSONException {
		JSONObject flatJsonObject = new JSONObject(flatJsonString);
		return unflatten(flatJsonObject, delimiter);
	}

	/**
	 * This method takes a flattened JSON object and returns it to
	 * the conventional format.
	 * It will use the given delimiter.
	 *
	 * @param flatJsonObject the JSON object to unflatten.
	 * @param delimiter the delimiter to use when splitting the keys.
	 * @return a string representation of a conventional JSON object.
	 */
	public static String unflatten(JSONObject flatJsonObject, String delimiter) throws JSONException {
		return unflattenToJSONObject(flatJsonObject, delimiter).toString();
	}

	/**
	 * This method takes the string representation of a flattened JSON object
	 * and returns it to the conventional format.
	 * It will use the default delimiter.
	 *
	 * @param flatJsonString the string representation of the JSON object.
	 * @return a conventional JSON object.
	 * @throws JSONException when the given string cannot be parsed to a JSON object.
	 */
	public static JSONObject unflattenToJSONObject(String flatJsonObject) throws JSONException {
		return unflattenToJSONObject(flatJsonObject, DEFAULT_DELIMITER);
	}


	/**
	 * This method takes a flattened JSON object and returns it to
	 * the conventional format.
	 * It will use the default delimiter.
	 *
	 * @param flatJsonObject the JSON object to unflatten.
	 * @return a conventional JSON object.
	 */
	public static JSONObject unflattenToJSONObject(JSONObject flatJsonObject) throws JSONException {
		return unflattenToJSONObject(flatJsonObject, DEFAULT_DELIMITER);
	}

	/**
	 * This method takes the string representation of a flattened JSON object
	 * and returns it to the conventional format.
	 * It will use the given delimiter.
	 *
	 * @param flatJsonString the JSON object to unflatten.
	 * @param delimiter the delimiter to use when splitting the keys.
	 * @return a conventional JSON object.
	 * @throws JSONException when the given string cannot be parsed to a JSON object.
	 */
	public static JSONObject unflattenToJSONObject(String flatJsonString, String delimiter) throws JSONException {
		JSONObject flatJsonObject = new JSONObject(flatJsonString);
		return unflattenToJSONObject(flatJsonObject, delimiter);
	}

	/**
	 * This method takes a flattened JSON object and returns it to
	 * the conventional format.
	 * It will use the given delimiter.
	 *
	 * @param flatJsonObject the JSON object to unflatten.
	 * @param delimiter the delimiter to use when splitting the keys.
	 * @return a conventional JSON object.
	 */
	public static JSONObject unflattenToJSONObject(JSONObject flatJsonObject, String delimiter) throws JSONException {
		JSONObject decoded = new JSONObject();

		for (Iterator<String> i = flatJsonObject.keys(); i.hasNext();) {
			String flattenedKey = i.next();
			String[] keys = flattenedKey.split(Pattern.quote(delimiter));

			Object parent = null;
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
						if (isNumber(keys[j])) {
							int index = Integer.parseInt(keys[j]);
							jsonArray.put(index, value);
						} else {
							// It was not an array after all, convert array to object
							JSONObject jsonObject = new JSONObject();
							for (int k = 0; k< jsonArray.length(); k++) {
								String[] keyRoot = Arrays.copyOf(keys, keys.length-1);
								StringBuilder sb = new StringBuilder();
								for (int l = 0; l < keyRoot.length; l++) {
									sb.append(keyRoot[l]).append(delimiter);
								}
								sb.append(k);
								// here we need to check if the leaf we are moving exists and is null
								if (!jsonArray.isNull(k) || flatJsonObject.has(sb.toString())) {
									Object object = jsonArray.get(k);
									jsonObject.put(Integer.toString(k), object);
								}
							}

							if (parent instanceof JSONArray) {
								JSONArray array = (JSONArray)parent;
								int index = Integer.parseInt(keys[j - 1]);
								array.put(index, jsonObject);
							} else {
								JSONObject object = (JSONObject)parent;
								object.put(keys[j - 1], jsonObject);
							}
							jsonObject.put(keys[j], value);
						}

					}
				} else {

					if (json instanceof JSONObject) {
						// The last index was an object: we're creating an object in an object
						JSONObject jsonObject = (JSONObject)json;
						if (jsonObject.has(keys[j])) {
							parent = json;
							json = jsonObject.get(keys[j]);
							// Assert that we are not handling a leaf
							if (!(json instanceof JSONArray) && !(json instanceof JSONObject)) {
								throw new AssertionError("Unhandled object type");
							}
						} else {
							if (isNumber(keys[j + 1])) {
								parent = json;
								json = new JSONArray();
								jsonObject.put(keys[j], json);
							} else {
								parent = json;
								json = new JSONObject();
								jsonObject.put(keys[j], json);
							}
						}
					} else if (json instanceof JSONArray) {
						// The last index was an array: we're creating an object in an array
						JSONArray jsonArray = (JSONArray)json;
						if (isNumber(keys[j])) {
							int index = Integer.parseInt(keys[j]);
							if (!jsonArray.isNull(index)) {
								parent = json;
								json = jsonArray.get(index);
								// Assert that we are not handling a leaf
								if (!(json instanceof JSONArray) && !(json instanceof JSONObject)) {
									throw new AssertionError("Unhandled object type");
								}
							} else {
								if (isNumber(keys[j + 1])) {
									parent = json;
									json = new JSONArray();
									jsonArray.put(index, json);
								} else {
									parent = json;
									json = new JSONObject();
									jsonArray.put(index, json);
								}
							}
						} else {
							// It was not an array after all, convert array to object
							JSONObject jsonObject = convertJSONArrayToJSONObject(jsonArray);
							if (parent instanceof JSONArray) {
								JSONArray array = (JSONArray)parent;
								int index = Integer.parseInt(keys[j - 1]);
								array.put(index, jsonObject);
							} else {
								JSONObject object = (JSONObject)parent;
								object.put(keys[j - 1], jsonObject);
							}

							if (jsonObject.has(keys[j])) {
								parent = json;
								json = jsonObject.get(keys[j]);
								// Assert that we are not handling a leaf
								if (!(json instanceof JSONArray) && !(json instanceof JSONObject)) {
									throw new AssertionError("Unhandled object type");
								}
							} else {
								if (isNumber(keys[j + 1])) {
									parent = json;
									json = new JSONArray();
									jsonObject.put(keys[j], json);
								} else {
									parent = json;
									json = new JSONObject();
									jsonObject.put(keys[j], json);
								}
							}
						}
					} else {
						throw new AssertionError("Unhandled object type");
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

	private static JSONObject convertJSONArrayToJSONObject(JSONArray jsonArray) {
		JSONObject jsonObject = new JSONObject();
		for (int k = 0; k< jsonArray.length(); k++) {
			if (!jsonArray.isNull(k)) { // Not the best option!
				Object object = jsonArray.get(k);
				jsonObject.put(Integer.toString(k), object);
			}
		}
		return jsonObject;
	}
}
