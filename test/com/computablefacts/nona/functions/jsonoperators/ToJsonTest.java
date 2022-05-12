package com.computablefacts.nona.functions.jsonoperators;

import static com.computablefacts.nona.Function.box;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.asterix.codecs.JsonCodec;
import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Json;

public class ToJsonTest {

  @Test
  public void testEmptyStringToJson() {

    String json = "";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ")");

    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyStringToJsonKeepArrays() {

    String json = "";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_arrays)");

    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyStringToJsonKeepPrimitiveArrays() {

    String json = "";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_primitive_arrays)");

    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyStringToJsonInvalidMode() {

    String json = "";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", invalid_mode)");

    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyArrayToJson() {

    String json = "[]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ")");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyArrayToJsonKeepArrays() {

    String json = "[]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_arrays)");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyArrayToJsonKeepPrimitiveArrays() {

    String json = "[]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_primitive_arrays)");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyArrayToJsonInvalidMode() {

    String json = "[]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", invalid_mode)");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testArrayToJson() {

    String json =
        "[{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13} , {\"col_1\": 21, \"col_2\": 22, \"col_3\": 23}]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ")");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testArrayToJsonKeepArrays() {

    String json =
        "[{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13} , {\"col_1\": 21, \"col_2\": 22, \"col_3\": 23}]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_arrays)");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testArrayToJsonKeepPrimitiveArrays() {

    String json =
        "[{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13} , {\"col_1\": 21, \"col_2\": 22, \"col_3\": 23}]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_primitive_arrays)");

    Assert.assertEquals(box(new Json(JsonCodec.asObject(
        "{\"[0].col_1\":11,\"[0].col_2\":12,\"[0].col_3\":13,\"[1].col_1\":21,\"[1].col_2\":22,\"[1].col_3\":23}"))),
        fn.evaluate(Function.definitions()));
  }

  @Test
  public void testArrayToJsonInvalidMode() {

    String json =
        "[{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13} , {\"col_1\": 21, \"col_2\": 22, \"col_3\": 23}]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", invalid_mode)");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyObjectToJson() {

    String json = "{}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ")");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyObjectToJsonKeepArrays() {

    String json = "{}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_arrays)");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyObjectToJsonKeepPrimitiveArrays() {

    String json = "{}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_primitive_arrays)");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyObjectToJsonInvalidMode() {

    String json = "{}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", invalid_mode)");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testObjectToJson() {

    String json = "{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ")");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testObjectToJsonKeepArrays() {

    String json = "{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_arrays)");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testObjectToJsonKeepPrimitiveArrays() {

    String json = "{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_primitive_arrays)");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testObjectToJsonInvalidMode() {

    String json = "{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", invalid_mode)");

    Assert.assertEquals(box(new Json(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testCompareSorted() {

    Json json1 = new Json("{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}");
    Json json2 = new Json("{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}");

    Assert.assertEquals(0, json1.compareTo(json2));
    Assert.assertEquals(0, json2.compareTo(json1));
  }

  @Test
  public void testCompareScrambled() {

    Json json1 = new Json("{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}");
    Json json2 = new Json("{\"col_3\": 13, \"col_2\": 12, \"col_1\": 11}");

    Assert.assertEquals(0, json1.compareTo(json2));
    Assert.assertEquals(0, json2.compareTo(json1));
  }

  @Test
  public void testCompareLessRows() {

    Json json1 = new Json(
        "[{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13},{\"col_1\": 21, \"col_2\": 22, \"col_3\": 23}]");
    Json json2 = new Json("{\"col_3\": 13, \"col_2\": 12, \"col_1\": 11}");

    Assert.assertEquals(0, json1.compareTo(json2));
    Assert.assertEquals(0, json2.compareTo(json1));
  }

  @Test
  public void testCompareLessColumns() {

    Json json1 = new Json("{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}");
    Json json2 = new Json("{\"col_1\": 11, \"col_2\": 12}");

    Assert.assertEquals(0, json1.compareTo(json2));
    Assert.assertEquals(0, json2.compareTo(json1));
  }

  @Test
  public void testCompareDifferentColumns() {

    Json json1 = new Json("{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}");
    Json json2 = new Json("{\"col_1\": 11, \"col_2\": 12, \"col_n\": 1n}");

    Assert.assertEquals(0, json1.compareTo(json2));
    Assert.assertEquals(0, json2.compareTo(json1));
  }
}
