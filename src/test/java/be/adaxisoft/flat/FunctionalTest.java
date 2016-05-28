package be.adaxisoft.flat;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class FunctionalTest {

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

	private String[] complexJsonKeys = {
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
	public void noInstanceTest() throws InstantiationException, IllegalAccessException, IllegalArgumentException {
		final Class<?> cls = Flat.class;
		final Constructor<?> c = cls.getDeclaredConstructors()[0];
		c.setAccessible(true);

		Throwable targetException = null;
	    try {
	        c.newInstance((Object[])null);
	    } catch (InvocationTargetException e) {
	        targetException = e.getTargetException();
	    }

	    assertNotNull(targetException);
	    assertEquals(targetException.getClass(), InstantiationException.class);
	}

	@Test
	public void flattenStringNoDelimiter() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		String flatJsonString = "{ \"a.b\": 1 }}";
		assertTrue("The flattened is not similar to the expected value. ",
				new JSONObject(Flat.flatten(jsonString)).similar(new JSONObject(flatJsonString)));
	}

	@Test
	public void flattenStringWithDelimiter() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		String flatJsonString = "{ \"a-b\": 1 }}";
		assertTrue("The flattened is not similar to the expected value. ",
				new JSONObject(Flat.flatten(jsonString, "-")).similar(new JSONObject(flatJsonString)));
	}

	@Test
	public void flattenObjectNoDelimiter() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		JSONObject jsonObject = new JSONObject(jsonString);
		String flatJsonString = "{ \"a.b\": 1 }}";
		Flat.flatten(jsonObject);
		assertTrue("The flattened is not similar to the expected value. ",
				new JSONObject(Flat.flatten(jsonObject)).similar(new JSONObject(flatJsonString)));
	}

	@Test
	public void flattenObjectWithDelimiter() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		JSONObject jsonObject = new JSONObject(jsonString);
		String flatJsonString = "{ \"a-b\": 1 }}";
		assertTrue("The flattened is not similar to the expected value. ",
				new JSONObject(Flat.flatten(jsonObject, "-")).similar(new JSONObject(flatJsonString)));
	}

	@Test
	public void flattenStringNoDelimiterToObject() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		String flatJsonString = "{ \"a.b\": 1 }}";
		assertTrue("The flattened is not similar to the expected value. ",
				Flat.flattenToJSONObject(jsonString).similar(new JSONObject(flatJsonString)));
	}

	@Test
	public void flattenStringWithDelimiterToObject() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		String flatJsonString = "{ \"a-b\": 1 }}";
		assertTrue("The flattened is not similar to the expected value. ",
				Flat.flattenToJSONObject(jsonString, "-").similar(new JSONObject(flatJsonString)));
	}

	@Test
	public void flattenObjectNoDelimiterToObject() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		JSONObject jsonObject = new JSONObject(jsonString);
		String flatJsonString = "{ \"a.b\": 1 }}";
		assertTrue("The flattened is not similar to the expected value. ",
				Flat.flattenToJSONObject(jsonObject).similar(new JSONObject(flatJsonString)));
	}

	@Test
	public void flattenObjectWithDelimiterToObject() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		JSONObject jsonObject = new JSONObject(jsonString);
		String flatJsonString = "{ \"a-b\": 1 }}";
		assertTrue("The flattened is not similar to the expected value. ",
				Flat.flattenToJSONObject(jsonObject, "-").similar(new JSONObject(flatJsonString)));
	}

	@Test
	public void unflattenStringNoDelimiter() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		String flatJsonString = "{ \"a.b\": 1 }}";
		assertTrue("The unflattened is not similar to the expected value. ",
				new JSONObject(Flat.unflatten(flatJsonString)).similar(new JSONObject(jsonString)));
	}

	@Test
	public void unflattenStringWithDelimiter() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		String flatJsonString = "{ \"a-b\": 1 }}";
		assertTrue("The unflattened is not similar to the expected value. ",
				new JSONObject(Flat.unflatten(flatJsonString, "-")).similar(new JSONObject(jsonString)));
	}

	@Test
	public void unflattenObjectNoDelimiter() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		String flatJsonString = "{ \"a.b\": 1 }}";
		JSONObject flatJsonObject = new JSONObject(flatJsonString);
		assertTrue("The unflattened is not similar to the expected value. ",
				new JSONObject(Flat.unflatten(flatJsonObject)).similar(new JSONObject(jsonString)));
	}

	@Test
	public void unflattenObjectWithDelimiter() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		String flatJsonString = "{ \"a-b\": 1 }}";
		JSONObject flatJsonObject = new JSONObject(flatJsonString);
		assertTrue("The unflattened is not similar to the expected value. ",
				new JSONObject(Flat.unflatten(flatJsonObject, "-")).similar(new JSONObject(jsonString)));
	}

	@Test
	public void unflattenStringNoDelimiterToObject() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		String flatJsonString = "{ \"a.b\": 1 }}";
		assertTrue("The unflattened is not similar to the expected value. ",
				Flat.unflattenToJSONObject(flatJsonString).similar(new JSONObject(jsonString)));
	}

	@Test
	public void unflattenStringWithDelimiterToObject() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		String flatJsonString = "{ \"a-b\": 1 }}";
		assertTrue("The unflattened is not similar to the expected value. ",
				Flat.unflattenToJSONObject(flatJsonString, "-").similar(new JSONObject(jsonString)));
	}

	@Test
	public void unflattenObjectNoDelimiterToObject() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		String flatJsonString = "{ \"a.b\": 1 }}";
		JSONObject flatJsonObject = new JSONObject(flatJsonString);
		assertTrue("The unflattened is not similar to the expected value. ",
				Flat.unflattenToJSONObject(flatJsonObject).similar(new JSONObject(jsonString)));
	}

	@Test
	public void unflattenObjectWithDelimiterToObject() {
		String jsonString = "{ \"a\": { \"b\": 1 }}";
		String flatJsonString = "{ \"a-b\": 1 }}";
		JSONObject flatJsonObject = new JSONObject(flatJsonString);
		assertTrue("The unflattened is not similar to the expected value. ",
				Flat.unflattenToJSONObject(flatJsonObject, "-").similar(new JSONObject(jsonString)));
	}

	@Test
	public void flattenEmptyObject() {
		JSONObject jsonObject = new JSONObject();
		JSONObject expectedObject = new JSONObject();
		JSONObject unflattenedJsonObject = Flat.flattenToJSONObject(jsonObject);
		assertTrue("The unflattened is not similar to the expected value. "  + unflattenedJsonObject, expectedObject.similar(unflattenedJsonObject));
	}

	@Test
	public void unflattenEmptyObject() {
		JSONObject jsonObject = new JSONObject();
		JSONObject expectedObject = new JSONObject();
		JSONObject unflattenedJsonObject = Flat.unflattenToJSONObject(jsonObject);
		assertTrue("The unflattened is not similar to the expected value. "  + unflattenedJsonObject, expectedObject.similar(unflattenedJsonObject));
	}

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
		String flatJsonSring = Flat.flatten(complexJson, "/");
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

		String flatJsonSring = Flat.flatten(complexJson, "/");
		JSONObject flatJsonObject = new JSONObject(flatJsonSring);

		JSONObject unflattenedJsonObject = Flat.unflattenToJSONObject(flatJsonObject, "/");

		assertTrue("The unflattened is not similar to the expected value", expectedJsonObject.similar(unflattenedJsonObject));
	}



	@Test(expected=IllegalArgumentException.class)
	public void illegalArgument() {
		String jsonString = "{ \"a.b\": 1 }";
		Flat.flatten(jsonString, ".");
	}

	@Test
	public void arrayToObjectConversionWithObjectParent() {
		JSONObject jsonObject = new JSONObject("{ \"a.2\": 1, \"a.1\": 1, \"a.B\": 1 }");
		JSONObject expectedObject = new JSONObject("{ \"a\": { \"1\": 1, \"2\": 1, \"B\": 1 }}");
		JSONObject unflattenedJsonObject = Flat.unflattenToJSONObject(jsonObject);
		assertTrue("The unflattened is not similar to the expected value. "  + unflattenedJsonObject, expectedObject.similar(unflattenedJsonObject));
	}

	@Test
	public void arrayToObjectConversionWithArrayParent() {
		JSONObject jsonObject = new JSONObject("{ \"a.0.1\": 1, \"a.0.2\": 1, \"a.0.B\": 1 }");
		JSONObject expectedObject = new JSONObject("{ \"a\": [ { \"1\": 1, \"2\": 1, \"B\": 1 } ]}");
		JSONObject unflattenedJsonObject = Flat.unflattenToJSONObject(jsonObject);
		assertTrue("The unflattened is not similar to the expected value. "  + unflattenedJsonObject, expectedObject.similar(unflattenedJsonObject));
	}

	@Test
	public void nonLeafArrayToObjectConversionWithArrayParent() {
		JSONObject jsonObject = new JSONObject("{ \"a.0.0.0\": 1, \"a.0.1.0\": 1, \"a.0.B.0\": 1 }");
		JSONObject expectedObject = new JSONObject("{ \"a\": [ { \"1\": [1], \"0\": [1], \"B\": [1] } ]}");
		JSONObject unflattenedJsonObject = Flat.unflattenToJSONObject(jsonObject);
		assertTrue("The unflattened is not similar to the expected value. "  + unflattenedJsonObject, expectedObject.similar(unflattenedJsonObject));
	}

	@Test
	public void nonLeafArrayToObjectConversionWithObjectParentWithArrayChild() {
		JSONObject jsonObject = new JSONObject("{ \"a.0.0\": 1, \"a.1.0\": 1, \"a.Z.0\": 1 }");
		JSONObject expectedObject = new JSONObject("{ \"a\": { \"1\": [1], \"0\": [1], \"Z\": [1] } }");
		JSONObject unflattenedJsonObject = Flat.unflattenToJSONObject(jsonObject);
		assertTrue("The unflattened is not similar to the expected value. "  + unflattenedJsonObject, expectedObject.similar(unflattenedJsonObject));
	}

	@Test
	public void nonLeafArrayToObjectConversionWithObjectParentWithObjectChild() {
		JSONObject jsonObject = new JSONObject("{ \"a.0.a\": 1, \"a.1.b\": 1, \"a.Z.c\": 1 }");
		JSONObject expectedObject = new JSONObject("{ \"a\": { \"1\": { \"b\": 1 }, \"0\": { \"a\": 1 }, \"Z\": { \"c\": 1 } } }");
		JSONObject unflattenedJsonObject = Flat.unflattenToJSONObject(jsonObject);
		assertTrue("The unflattened is not similar to the expected value. "  + unflattenedJsonObject + " => " + expectedObject, expectedObject.similar(unflattenedJsonObject));
	}

	@Test
	public void baseArrayToObjectConversion() {
		JSONObject jsonObject = new JSONObject("{ \"1\": 1, \"B\": 1 }");
		JSONObject expectedObject = new JSONObject("{ \"1\": 1, \"B\": 1 }");
		JSONObject unflattenedJsonObject = Flat.unflattenToJSONObject(jsonObject);
		assertTrue("The unflattened is not similar to the expected value. "  + unflattenedJsonObject, expectedObject.similar(unflattenedJsonObject));
	}

	@Test
	public void objectToArrayConversion() {
		JSONObject jsonObject = new JSONObject("{ \"a.B\": 1, \"a.1\": 1 }");
		JSONObject expectedObject = new JSONObject("{ \"a\":{  \"1\": 1, \"B\": 1 } }");
		JSONObject unflattenedJsonObject = Flat.unflattenToJSONObject(jsonObject);
		assertTrue("The unflattened is not similar to the expected value. " + unflattenedJsonObject, expectedObject.similar(unflattenedJsonObject));
	}

	@Test
	public void objectToArrayWithNull() {
		JSONObject jsonObject = new JSONObject("{ \"a.0\": null, \"a.B\": 1 }");
		JSONObject expectedObject = new JSONObject("{ \"a\":{  \"0\": null, \"B\": 1 } }");
		JSONObject unflattenedJsonObject = Flat.unflattenToJSONObject(jsonObject);
		assertTrue("The unflattened is not similar to the expected value. " + unflattenedJsonObject.toString(), expectedObject.similar(unflattenedJsonObject));
	}

	@Test
	public void flattenNull () {
		JSONObject jsonObject = new JSONObject("{ \"a\": null }");
		JSONObject expectedObject = new JSONObject("{ \"a\": null }");
		JSONObject unflattenedJsonObject = Flat.flattenToJSONObject(jsonObject);
		assertTrue("The unflattened is not similar to the expected value. "  + unflattenedJsonObject, expectedObject.similar(unflattenedJsonObject));
	}

	@Test
	public void unflattenNull () {
		JSONObject jsonObject = new JSONObject("{ \"a\": null }");
		JSONObject expectedObject = new JSONObject("{ \"a\": null }");
		JSONObject unflattenedJsonObject = Flat.unflattenToJSONObject(jsonObject);
		assertTrue("The unflattened is not similar to the expected value. "  + unflattenedJsonObject, expectedObject.similar(unflattenedJsonObject));
	}

	@Test
	public void missingIndexes() {
		JSONObject jsonObject = new JSONObject("{ \"a.1\": null, }");
		JSONObject expectedObject = new JSONObject("{ \"a\": [ null, null ] }");
		JSONObject unflattenedJsonObject = Flat.unflattenToJSONObject(jsonObject);
		assertTrue("The unflattened is not similar to the expected value. " + unflattenedJsonObject.toString(), expectedObject.similar(unflattenedJsonObject));
	}

}
