package be.adaxisoft.flat;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class SerializationTest {

	private String complexJson = "{ "  
			+ " \"string\": \"aString\", "
			+ " \"integer\": -1, "
			+ " \"nested\": { "
			+ "		\"x\": 1, "
			+ "		\"y\": 2, "
			+ "		\"z\": 3  }, "
			+ " \"moreNested\": { "
			+ "		\"a\": [1,2,3], "
			+ "		\"b\": [4,5,6], "
			+ "		\"c\": [7,8,9]}, "
			+ " \"arrayFromHell\": ["
			+ "		{ "
			+ "			\"innerArray\": ["
			+ "								{ \"innerInnerArray\": [1,2,3]}"
			+ "							]"
			+ "		}"
			+ "	]}";
	
	String[] complexJsonKeys = {
			"arrayFromHell.0.innerArray.0.innerInnerArray.0",
			"arrayFromHell.0.innerArray.0.innerInnerArray.1",
			"arrayFromHell.0.innerArray.0.innerInnerArray.2",
			"nested.z",
			"nested.y",
			"nested.x",
			"integer",
			"string",
			"moreNested.b.0",
			"moreNested.b.1",
			"moreNested.b.2",
			"moreNested.c.0",
			"moreNested.c.1",
			"moreNested.c.2",
			"moreNested.a.0",
			"moreNested.a.1",
			"moreNested.a.2",
		};
	
	@Test
	public void testProducesValidJson() {

		try {
			Flat.flatten(complexJson);
		} catch (JSONException e) {
			fail("Flattening an object should return valid JSON");
		}
		
	}

	@Test
	public void testContainsCorrectKeys() throws JSONException {
		String flatJsonSring = Flat.flatten(complexJson);
		JSONObject flatJsonObject = new JSONObject(flatJsonSring);
		
		for (String key : complexJsonKeys) {
			if (!flatJsonObject.has(key)) {
				fail("Expected to have " + key + " in object");
			}
		}
	}
	
	@Test
	public void testFlattenWithCustomDelimiters() throws JSONException {
		String flatJsonSring = Flat.flatten(complexJson, '/');
		JSONObject flatJsonObject = new JSONObject(flatJsonSring);
		
		for (String key : complexJsonKeys) {
			if (!flatJsonObject.has(key.replace('.', '/'))) {
				fail("Expected to have " + key + " in object");
			}
		}
	}
	
	@Test
	public void testUnflatten() throws JSONException {
		JSONObject expectedJsonObject = new JSONObject(complexJson);
		
		String flatJsonSring = Flat.flatten(complexJson);
		JSONObject flatJsonObject = new JSONObject(flatJsonSring);
		
		JSONObject unflattenedJsonObject = Flat.unflattenToJSONObject(flatJsonObject);
		
		assertTrue("The unflattened is not similar to the expected value", expectedJsonObject.similar(unflattenedJsonObject));
	}
	
	@Test
	public void testUnflattenWithCustomDelimiters() throws JSONException {
		JSONObject expectedJsonObject = new JSONObject(complexJson);
		
		String flatJsonSring = Flat.flatten(complexJson, '/');
		JSONObject flatJsonObject = new JSONObject(flatJsonSring);
		
		JSONObject unflattenedJsonObject = Flat.unflattenToJSONObject(flatJsonObject, '/');
		
		assertTrue("The unflattened is not similar to the expected value", expectedJsonObject.similar(unflattenedJsonObject));
	}
	
	public void basicTestFlattenMethods() {
		String jsonString = "{ \"a\": { \"b\": 1 }";
		JSONObject jsonObject = new JSONObject(jsonString);
		
		try {
			Flat.flatten(jsonString);
		} catch (Exception e) {
			fail("Failed to flatten a json string");
		}
		
		try {
			Flat.flatten(jsonObject);
		} catch (Exception e) {
			fail("Failed to flatten a json object");
		}
		
		try {
			Flat.flatten(jsonString, '-');
		} catch (Exception e) {
			fail("Failed to flatten a json string with custom delimiter");
		}
		
		try {
			Flat.flatten(jsonObject, '-');
		} catch (Exception e) {
			fail("Failed to flatten a json object with custom delimiter");
		}
		
		try {
			Flat.flattenToJSONObject(jsonString);
		} catch (Exception e) {
			fail("Failed to flatten a json object to a json object");
		}
		
		try {
			Flat.flattenToJSONObject(jsonObject);
		} catch (Exception e) {
			fail("Failed to flatten a json object to a json object");
		}
		
		try {
			Flat.flattenToJSONObject(jsonObject, '-');
		} catch (Exception e) {
			fail("Failed to flatten a json object to a json object with custom delimiter");
		}
		
		try {
			Flat.flattenToJSONObject(jsonObject, '-');
		} catch (Exception e) {
			fail("Failed to flatten a json object to a json object with custom delimiter");
		}
		
	}
	
	public void basicTestUnFlattenMethods() {
		String jsonString = "{ \"a\": { \"b\": 1 }";
		JSONObject jsonObject = new JSONObject(jsonString);
		
		try {
			Flat.unflatten(jsonString);
		} catch (Exception e) {
			fail("Failed to flatten a json string");
		}
		
		try {
			Flat.unflatten(jsonObject);
		} catch (Exception e) {
			fail("Failed to flatten a json object");
		}
		
		try {
			Flat.unflatten(jsonString, '-');
		} catch (Exception e) {
			fail("Failed to flatten a json string with custom delimiter");
		}
		
		try {
			Flat.unflatten(jsonObject, '-');
		} catch (Exception e) {
			fail("Failed to flatten a json object with custom delimiter");
		}
		
		try {
			Flat.unflattenToJSONObject(jsonString);
		} catch (Exception e) {
			fail("Failed to flatten a json object to a json object");
		}
		
		try {
			Flat.unflattenToJSONObject(jsonObject);
		} catch (Exception e) {
			fail("Failed to flatten a json object to a json object");
		}
		
		try {
			Flat.unflattenToJSONObject(jsonObject, '-');
		} catch (Exception e) {
			fail("Failed to flatten a json object to a json object with custom delimiter");
		}
		
		try {
			Flat.unflattenToJSONObject(jsonObject, '-');
		} catch (Exception e) {
			fail("Failed to flatten a json object to a json object with custom delimiter");
		}
		
	}

}
