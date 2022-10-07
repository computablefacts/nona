package com.computablefacts.nona.functions.listoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.asterix.codecs.JsonCodec;
import com.computablefacts.nona.Function;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.errorprone.annotations.Var;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class GetTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testGetOnEmptyList() {
    Function fn = new Function("GET(TO_LIST([]), 0)");
    Assert.assertEquals(BoxedType.of(new ArrayList<>()), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetArrayElement() {

    String array = Function.wrap("[1, 2, 3]");

    @Var Function fn = new Function(String.format("GET(TO_LIST(%s), 0)", array));
    Assert.assertEquals(BoxedType.of("1"), fn.evaluate(Function.definitions()));

    fn = new Function(String.format("GET(TO_LIST(%s), 1)", array));
    Assert.assertEquals(BoxedType.of("2"), fn.evaluate(Function.definitions()));

    fn = new Function(String.format("GET(TO_LIST(%s), 2)", array));
    Assert.assertEquals(BoxedType.of("3"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetArrayElementFromString() {

    String json = "[{\"id\": 1} , {\"id\": 2}, {\"id\": 3}]";

    Function fn = new Function("GET(" + Function.wrap(json) + ", 2)");

    Assert.assertEquals("{\"id\":3}", fn.evaluate(Function.definitions()).asString());
  }

  @Test
  public void testGetAttributeValueFromString() {

    String json = "{\"id\": 1}";

    Function fn = new Function("GET(" + Function.wrap(json) + ", id)");

    Assert.assertEquals(BoxedType.of(1), fn.evaluate(Function.definitions()));
  }

  @Test(expected = UncheckedExecutionException.class)
  public void testGetArrayElementFromInvalidString1() {

    String json = "{\"id\": 1} , {\"id\": 2}, {\"id\": 3}";

    Function fn = new Function("GET(" + Function.wrap(json) + ", 2)");

    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetArrayElementFromInvalidString2() {

    String json = "[{\"id\": 1} , {\"id\": 2}, {\"id\": 3}";

    Function fn = new Function("GET(" + Function.wrap(json) + ", 2)");

    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetAttributeValueFromInvalidString() {

    String json = "{id: 1}";

    Function fn = new Function("GET(" + Function.wrap(json) + ", id)");

    Assert.assertEquals(BoxedType.of(""), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetPathValueOfJsonArray() {

    String json = "[{\"id\": 1} , {\"id\": 2}, {\"id\": 3}]";

    Function fn = new Function("GET(TO_JSON(" + Function.wrap(json) + ", flatten), [1].id)");

    Assert.assertEquals(BoxedType.of(2), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetPathValueOfJsonObject() {

    String json = "{\"ids\":[{\"id\": 1} , {\"id\": 2}, {\"id\": 3}]}";

    Function fn = new Function("GET(TO_JSON(" + Function.wrap(json) + ", flatten), ids[2].id)");

    Assert.assertEquals(BoxedType.of(3), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetInvalidJsonPathValue() {

    String json = "{\"ids\":[{\"id\": 1} , {\"id\": 2}, {\"id\": 3}]}";

    // index 3 is out of bound
    Function fn = new Function("GET(TO_JSON(" + Function.wrap(json) + "), ids[3].id)");

    Assert.assertEquals(BoxedType.of(""), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetPathValueOfMap() {

    String json = "{\"ids\":[{\"id\": 1} , {\"id\": 2}, {\"id\": 3}]}";

    Function fn = new Function("GET(TO_JSON(" + Function.wrap(json) + "), ids)");

    Assert.assertEquals(BoxedType.of(JsonCodec.asCollection("[{\"id\":1},{\"id\":2},{\"id\":3}]")),
        fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetInvalidMapPathValue() {

    String json = "{\"ids\":[{\"id\": 1} , {\"id\": 2}, {\"id\": 3}]}";

    // index 3 is out of bound
    Function fn = new Function("GET(TO_JSON(" + Function.wrap(json) + "), idz)");

    Assert.assertEquals(BoxedType.of(""), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetObjectInArrayOfObjects() {

    String json = "{\"ids\":[{\"id\": 1} , {\"id\": 2}, {\"id\": 3}]}";

    Function fn = new Function("GET(GET(TO_JSON(" + Function.wrap(json) + "), ids), 1)");

    Assert.assertEquals(BoxedType.of(JsonCodec.asObject("{\"id\":2}")), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetKeyValueOfObjectInArrayOfObjects() {

    String json = "{\"ids\":[{\"id\": 1} , {\"id\": 2}, {\"id\": 3}]}";

    Function fn = new Function("GET(GET(GET(TO_JSON(" + Function.wrap(json) + "), ids), 1), id)");

    Assert.assertEquals(BoxedType.of(2), fn.evaluate(Function.definitions()));
  }
}
