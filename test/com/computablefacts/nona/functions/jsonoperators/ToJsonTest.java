package com.computablefacts.nona.functions.jsonoperators;

import static com.computablefacts.nona.Function.box;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.asterix.codecs.JsonCodec;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

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

    Assert.assertEquals(box(JsonCodec.asCollection(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyArrayToJsonKeepArrays() {

    String json = "[]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_arrays)");

    Assert.assertEquals(box(JsonCodec.asCollection(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyArrayToJsonKeepPrimitiveArrays() {

    String json = "[]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_primitive_arrays)");

    Assert.assertEquals(box(JsonCodec.asCollection(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyArrayToJsonInvalidMode() {

    String json = "[]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", invalid_mode)");

    Assert.assertEquals(box(JsonCodec.asCollection(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testArrayToJson() {

    String json = "[{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13} , {\"col_1\": 21, \"col_2\": 22, \"col_3\": 23}]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ")");

    Assert.assertEquals(box(JsonCodec.asCollection(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testArrayToJsonKeepArrays() {

    String json = "[{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13} , {\"col_1\": 21, \"col_2\": 22, \"col_3\": 23}]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_arrays)");

    Assert.assertEquals(box(JsonCodec.asCollection(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testArrayToJsonKeepPrimitiveArrays() {

    String json = "[{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13} , {\"col_1\": 21, \"col_2\": 22, \"col_3\": 23}]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_primitive_arrays)");

    Assert.assertEquals(box(JsonCodec.asObject(
            "{\"[0].col_1\":11,\"[0].col_2\":12,\"[0].col_3\":13,\"[1].col_1\":21,\"[1].col_2\":22,\"[1].col_3\":23}")),
        fn.evaluate(Function.definitions()));
  }

  @Test
  public void testArrayToJsonInvalidMode() {

    String json = "[{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13} , {\"col_1\": 21, \"col_2\": 22, \"col_3\": 23}]";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", invalid_mode)");

    Assert.assertEquals(box(JsonCodec.asObject(
            "{\"[0].col_1\":11,\"[0].col_2\":12,\"[0].col_3\":13,\"[1].col_1\":21,\"[1].col_2\":22,\"[1].col_3\":23}")),
        fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyObjectToJson() {

    String json = "{}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ")");

    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyObjectToJsonKeepArrays() {

    String json = "{}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_arrays)");

    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyObjectToJsonKeepPrimitiveArrays() {

    String json = "{}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_primitive_arrays)");

    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyObjectToJsonInvalidMode() {

    String json = "{}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", invalid_mode)");

    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testObjectToJson() {

    String json = "{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ")");

    Assert.assertEquals(box(JsonCodec.asObject(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testObjectToJsonKeepArrays() {

    String json = "{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_arrays)");

    Assert.assertEquals(box(JsonCodec.asObject(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testObjectToJsonKeepPrimitiveArrays() {

    String json = "{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", keep_primitive_arrays)");

    Assert.assertEquals(box(JsonCodec.asObject(json)), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testObjectToJsonInvalidMode() {

    String json = "{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}";
    Function fn = new Function("TO_JSON(" + Function.wrap(json) + ", invalid_mode)");

    Assert.assertEquals(box(JsonCodec.asObject(json)), fn.evaluate(Function.definitions()));
  }
}

