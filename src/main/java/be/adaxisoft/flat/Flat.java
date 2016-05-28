package be.adaxisoft.flat;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * This library flattens JSON files.</br>
 * </br>
 * Before:</br>
 * <pre>
 *{
 *	"key1": {
 *		"keyA": "valueI"
 *	},
 *	"key2": {
 *		"keyB": "valueII"
 *	},
 *	"key3": { "a": { "b": { "c": 2 } } }
 *}
 * </pre>
 *
 * After:</br>
 * <pre>
 *{
 *	"key1.keyA": "valueI",
 *	"key2.keyB": "valueII",
 *	"key3.a.b.c": 2
 *}
 * </pre>
 *
 * The library can do the reverse operation as well: unflatten.</br>
 *
 * @author Gérik Bonaert <dev@adaxisoft.be>
 */
public class Flat {


	/**
	 * The default delimiter is used by methods that do not specify a delimiter.
	 * The default value of the delimiter is '.' (dot).
	 */
	public static final String DEFAULT_DELIMITER = ".";

	/**
	 * Prevent from instantiating this class.
	 *
	 * @throws InstantiationException thrown when trying to instantiate this class.
	 */
	private Flat() throws InstantiationException {
		throw new InstantiationException("Instances of this type are forbidden");
	}

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
	 * It will use the given delimiter to separate keys and indexes.
	 * Your keys cannot contain the given delimiter.</br>
	 *
	 * Running the following code:
	 * <pre>
	 * Flat.flatten(new JSONObject("\"key1": { \"nested1\" : 1, \"nested2\" : 2 }}"), "@");
	 * </pre>
	 *
	 * will generate:
	 * <pre>
	 * {
	 * 	"key1@nested1" : 1
	 *	"key1@nested2" : 2
	 * }
	 * </pre>
	 *
	 * instead of the default:</br>
	 * <pre>
	 * {
	 * 	"key1.nested1" : 1
	 *	"key1.nested2" : 2
	 * }
	 * </pre>
	 *
	 * @param jsonObject the JSON object to flatten
	 * @param delimiter the delimiter to use when joining the keys.
	 * @return a string representation of the flattened JSON document.
	 */
	public static String flatten(JSONObject jsonObject, String delimiter) throws JSONException {
		StringBuilder sb = new StringBuilder();
		flatten(sb, null, jsonObject, delimiter);
		String s = "{" + sb.toString() + "}";
		return s;
	}

	/**
	 * This method flattens the string representation of the JSON object.
	 * It will use the given delimiter to separate keys and indexes.
	 * Your keys cannot contain the given delimiter.</br>
	 *
	 * Running the following code:
	 * <pre>
	 * 	Flat.flatten("\"key1": { \"nested1\" : 1, \"nested2\" : 2 }}", "@");
	 * </pre>
	 *
	 * will generate:
	 * <pre>
	 * {
	 * 	"key1@nested1" : 1
	 *	"key1@nested2" : 2
	 * }
	 * </pre>
	 *
	 * instead of the default:</br>
	 * <pre>
	 * {
	 * 	"key1.nested1" : 1
	 *	"key1.nested2" : 2
	 * }
	 * </pre>
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
	 * It will use the given delimiter to separate keys and indexes.
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
	 * It will use the given delimiter to separate keys and indexes.
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
	 * @param flattenedJson StringBuilder that will contain the flattened object.
	 * @param parent parent element of the object.
	 * @param value object to flatten.
	 * @param delimiter the delimiter to use when joining the keys.
	 * @return a string representation of the flattened JSON object.
	 */
	private static void flatten(StringBuilder flattenedJson, String parent, Object value, String delimiter) throws JSONException {

		if (value instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) value;
			for (Iterator<String> i = jsonObject.keys(); i.hasNext();) {
				String key = i.next();
				if (key.contains(delimiter)) {
					throw new IllegalArgumentException("A key cannot contain the delimiter");
				}
				String hkey = (parent == null) ? key : parent + delimiter + key;
				Object jval = jsonObject.get(key);
				flatten(flattenedJson, hkey, jval, delimiter);
				if (i.hasNext()) {
					flattenedJson.append(",");
				}
			}
		} else if (value instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray) value;
			for (int i = 0; i < jsonArray.length(); i++) {
				String hkey = (parent == null) ? Integer.toString(i) : parent + delimiter + i;
				Object aval = jsonArray.get(i);
				flatten(flattenedJson, hkey, aval, delimiter);
				if (i < jsonArray.length() - 1) {
					flattenedJson.append(",");
				}
			}
		} else if (value instanceof String) {
			flattenedJson.append("\"").append(parent).append("\"").append(":");
			String s = (String) value;
			flattenedJson.append(JSONObject.quote(s));
		} else if (value instanceof Integer) {
			flattenedJson.append("\"").append(parent).append("\"").append(":");
			Integer integer = (Integer) value;
			flattenedJson.append(integer);
		} else if (JSONObject.NULL.equals(value)) {
			flattenedJson.append("\"").append(parent).append("\"").append(":");
			flattenedJson.append("null");
		}

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
	 * @param flatJsonObject the string representation of the JSON object.
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
		JSONObject unflattened = new JSONObject();

		for (Iterator<String> keyIterator = flatJsonObject.keys(); keyIterator.hasNext();) {
			String flattenedKey = keyIterator.next();
			String[] keys = flattenedKey.split(Pattern.quote(delimiter));

			Object parent = null;
			Object json = unflattened;
			for (int i = 0; i < keys.length; i++) {

				if (i == keys.length - 1) {
					// We are at a leaf key
					Object value = flatJsonObject.get(flattenedKey);
					if (json instanceof JSONObject) {
						JSONObject jsonObject = (JSONObject)json;
						jsonObject.put(keys[i], value);
					} else if (json instanceof JSONArray) {
						JSONArray jsonArray = (JSONArray)json;
						if (isNumber(keys[i])) {
							int index = Integer.parseInt(keys[i]);
							jsonArray.put(index, value);
						} else {
							// It was not an array after all, convert array to object
							JSONObject jsonObject = new JSONObject();
							String parentKeyPrefix = parentKeyPrefix(keys, delimiter);
							for (int j = 0; j< jsonArray.length(); j++) {
								// here we need to check if the leaf we are moving exists and is null
								if (!jsonArray.isNull(j) || flatJsonObject.has(parentKeyPrefix + j)) {
									Object object = jsonArray.get(j);
									jsonObject.put(Integer.toString(j), object);
								}
							}

							if (parent instanceof JSONArray) {
								JSONArray array = (JSONArray)parent;
								int index = Integer.parseInt(keys[i - 1]);
								array.put(index, jsonObject);
							} else {
								JSONObject object = (JSONObject)parent;
								object.put(keys[i - 1], jsonObject);
							}
							jsonObject.put(keys[i], value);
						}

					}
				} else {

					if (json instanceof JSONObject) {
						// The last index was an object: we're creating an object in an object
						JSONObject jsonObject = (JSONObject)json;
						if (jsonObject.has(keys[i])) {
							parent = json;
							json = jsonObject.get(keys[i]);
						} else {
							if (isNumber(keys[i + 1])) {
								parent = json;
								json = new JSONArray();
								jsonObject.put(keys[i], json);
							} else {
								parent = json;
								json = new JSONObject();
								jsonObject.put(keys[i], json);
							}
						}
					} else if (json instanceof JSONArray) {
						// The last index was an array: we're creating an object in an array
						JSONArray jsonArray = (JSONArray)json;
						if (isNumber(keys[i])) {
							int index = Integer.parseInt(keys[i]);
							if (!jsonArray.isNull(index)) {
								parent = json;
								json = jsonArray.get(index);
							} else {
								if (isNumber(keys[i + 1])) {
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
								int index = Integer.parseInt(keys[i - 1]);
								array.put(index, jsonObject);
							} else {
								JSONObject object = (JSONObject)parent;
								object.put(keys[i - 1], jsonObject);
							}

							if (isNumber(keys[i + 1])) {
								parent = json;
								json = new JSONArray();
								jsonObject.put(keys[i], json);
							} else {
								parent = json;
								json = new JSONObject();
								jsonObject.put(keys[i], json);
							}
						}
					}
				}
			}
		}
		return unflattened;
	}

	private static boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static String parentKeyPrefix(String[] keys, String delimiter) {
		String[] keyRoot = Arrays.copyOf(keys, keys.length-1);
		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < keyRoot.length; k++) {
			sb.append(keyRoot[k]).append(delimiter);
		}
		return sb.toString();
	}

	/**
	 * Copies all the elements of the array into the object.
	 * Beware that it will skip null values.
	 *
	 * @param jsonArray the json array to convert
	 * @return the jsonObject
	 */
	private static JSONObject convertJSONArrayToJSONObject(JSONArray jsonArray) {
		JSONObject jsonObject = new JSONObject();
		for (int k = 0; k< jsonArray.length(); k++) {
			if (!jsonArray.isNull(k)) {
				Object object = jsonArray.get(k);
				jsonObject.put(Integer.toString(k), object);
			}
		}
		return jsonObject;
	}
}
