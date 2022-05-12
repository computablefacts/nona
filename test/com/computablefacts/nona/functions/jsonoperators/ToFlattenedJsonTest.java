package com.computablefacts.nona.functions.jsonoperators;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Json;

public class ToFlattenedJsonTest {

  @Test
  public void testEmptyStringToJson() {

    String json = "";
    Function fn = new Function("TO_FLATTENED_JSON(" + Function.wrap(json) + ")");

    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyArrayToJson() {

    String json = "[]";
    Function fn = new Function("TO_FLATTENED_JSON(" + Function.wrap(json) + ")");

    Assert.assertEquals(BoxedType.create(Json.create("[]")), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testArrayToJson() {

    String json =
        "[{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13} , {\"col_1\": 21, \"col_2\": 22, \"col_3\": 23}]";
    Function fn = new Function("TO_FLATTENED_JSON(" + Function.wrap(json) + ")");

    Assert.assertEquals(BoxedType.create(Json.create(
        "{\"[0].col_1\":11,\"[0].col_2\":12,\"[0].col_3\":13,\"[1].col_1\":21,\"[1].col_2\":22,\"[1].col_3\":23}")),
        fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyObjectToJson() {

    String json = "{}";
    Function fn = new Function("TO_FLATTENED_JSON(" + Function.wrap(json) + ")");

    Assert.assertEquals(BoxedType.create(new Json(new HashMap<>())),
        fn.evaluate(Function.definitions()));
  }

  @Test
  public void testObjectToJson() {

    String json = "{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}";
    Function fn = new Function("TO_FLATTENED_JSON(" + Function.wrap(json) + ")");

    Assert.assertEquals(BoxedType.create(Json.create(json)), fn.evaluate(Function.definitions()));
  }
}
