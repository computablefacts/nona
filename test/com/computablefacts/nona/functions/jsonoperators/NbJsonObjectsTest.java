package com.computablefacts.nona.functions.jsonoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

@Deprecated
public class NbJsonObjectsTest {

  @Test
  public void testEmptyArrayNumberOfObjects() {

    String json = "[]";

    Function fn = new Function("NB_JSON_OBJECTS(TO_JSON(" + Function.wrap(json) + "))");
    Assert.assertEquals(BoxedType.of(0), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testArrayNumberOfObjects() {

    String json = "[{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13} , {\"col_1\": 21, \"col_2\": 22, \"col_3\": 23}]";

    Function fn = new Function("NB_JSON_OBJECTS(TO_JSON(" + Function.wrap(json) + "))");
    Assert.assertEquals(BoxedType.of(2), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEmptyObjectNumberOfObjects() {

    String json = "{}";

    Function fn = new Function("NB_JSON_OBJECTS(TO_JSON(" + Function.wrap(json) + "))");
    Assert.assertEquals(BoxedType.of(0), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testObjectNumberOfObjects() {

    String json = "{\"col_1\": 11, \"col_2\": 12, \"col_3\": 13}";

    Function fn = new Function("NB_JSON_OBJECTS(TO_JSON(" + Function.wrap(json) + "))");
    Assert.assertEquals(BoxedType.of(1), fn.evaluate(Function.definitions()));
  }
}
