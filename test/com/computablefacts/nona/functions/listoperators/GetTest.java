package com.computablefacts.nona.functions.listoperators;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.errorprone.annotations.Var;

public class GetTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testGetOnEmptyList() {
    Function fn = new Function("GET(TO_LIST([]), 0)");
    Assert.assertEquals(BoxedType.create(new ArrayList<>()), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetArrayElement() {

    @Var
    Function fn = new Function("GET(TO_LIST(_([1, 2, 3])), 0)");
    Assert.assertEquals(BoxedType.create("1"), fn.evaluate(Function.definitions()));

    fn = new Function("GET(TO_LIST(_([1, 2, 3])), 1)");
    Assert.assertEquals(BoxedType.create("2"), fn.evaluate(Function.definitions()));

    fn = new Function("GET(TO_LIST(_([1, 2, 3])), 2)");
    Assert.assertEquals(BoxedType.create("3"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetPathValueOfJsonArray() {

    String json = "[{\"id\": 1} , {\"id\": 2}, {\"id\": 3}]";

    Function fn = new Function("GET(TO_JSON(" + Function.wrap(json) + ", flatten), [1].id)");

    Assert.assertEquals(BoxedType.create(2), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetPathValueOfJsonObject() {

    String json = "{\"ids\":[{\"id\": 1} , {\"id\": 2}, {\"id\": 3}]}";

    Function fn = new Function("GET(TO_JSON(" + Function.wrap(json) + ", flatten), ids[2].id)");

    Assert.assertEquals(BoxedType.create(3), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testGetInvalidJsonPathValue() {

    String json = "{\"ids\":[{\"id\": 1} , {\"id\": 2}, {\"id\": 3}]}";

    // index 3 is out of bound
    Function fn = new Function("GET(TO_JSON(" + Function.wrap(json) + "), ids[3].id)");

    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }
}
