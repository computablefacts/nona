package com.computablefacts.nona.functions.jsonoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Json;

public class ToJsonTest {

  @Test
  public void testEmptyArrayToJson() {

    String json = "[]";

    Map<String, Function> functions = new HashMap<>();
    functions.put("TOJSON", new ToJson());

    Function fn = new Function("TOJSON(" + Function.wrap(json) + ")");
    Assert.assertEquals(BoxedType.create(Json.create(json)), fn.evaluate(functions));
  }

  @Test
  public void testArrayToJson() {

    String json =
        "[{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13} , {\"col_1\": 21, \"col_2\": 22, \"col_3\": 23}]";

    Map<String, Function> functions = new HashMap<>();
    functions.put("TOJSON", new ToJson());

    Function fn = new Function("TOJSON(" + Function.wrap(json) + ")");
    Assert.assertEquals(BoxedType.create(Json.create(json)), fn.evaluate(functions));
  }

  @Test
  public void testEmptyObjectToJson() {

    String json = "{}";

    Map<String, Function> functions = new HashMap<>();
    functions.put("TOJSON", new ToJson());

    Function fn = new Function("TOJSON(" + Function.wrap(json) + ")");
    Assert.assertEquals(BoxedType.create(Json.create(json)), fn.evaluate(functions));
  }

  @Test
  public void testObjectToJson() {

    String json = "{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}";

    Map<String, Function> functions = new HashMap<>();
    functions.put("TOJSON", new ToJson());

    Function fn = new Function("TOJSON(" + Function.wrap(json) + ")");
    Assert.assertEquals(BoxedType.create(Json.create(json)), fn.evaluate(functions));
  }

  @Test
  public void testCompareSorted() {

    Json json1 = Json.create("{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}");
    Json json2 = Json.create("{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}");

    Assert.assertEquals(0, json1.compareTo(json2));
    Assert.assertEquals(0, json2.compareTo(json1));
  }

  @Test
  public void testCompareScrambled() {

    Json json1 = Json.create("{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}");
    Json json2 = Json.create("{\"col_3\": 13, \"col_2\": 12, \"col_1\": 11}");

    Assert.assertEquals(0, json1.compareTo(json2));
    Assert.assertEquals(0, json2.compareTo(json1));
  }

  @Test
  public void testCompareLessRows() {

    Json json1 = Json.create(
        "[{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13},{\"col_1\": 21, \"col_2\": 22, \"col_3\": 23}]");
    Json json2 = Json.create("{\"col_3\": 13, \"col_2\": 12, \"col_1\": 11}");

    Assert.assertEquals(0, json1.compareTo(json2));
    Assert.assertEquals(0, json2.compareTo(json1));
  }

  @Test
  public void testCompareLessColumns() {

    Json json1 = Json.create("{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}");
    Json json2 = Json.create("{\"col_1\": 11, \"col_2\": 12}");

    Assert.assertEquals(0, json1.compareTo(json2));
    Assert.assertEquals(0, json2.compareTo(json1));
  }

  @Test
  public void testCompareDifferentColumns() {

    Json json1 = Json.create("{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}");
    Json json2 = Json.create("{\"col_1\": 11, \"col_2\": 12, \"col_n\": 1n}");

    Assert.assertEquals(0, json1.compareTo(json2));
    Assert.assertEquals(0, json2.compareTo(json1));
  }
}
