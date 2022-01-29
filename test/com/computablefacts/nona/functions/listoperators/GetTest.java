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
  public void testGet() {

    @Var
    Function fn = new Function("GET(TO_LIST(_([1, 2, 3])), 0)");
    Assert.assertEquals(BoxedType.create("1"), fn.evaluate(Function.definitions()));

    fn = new Function("GET(TO_LIST(_([1, 2, 3])), 1)");
    Assert.assertEquals(BoxedType.create("2"), fn.evaluate(Function.definitions()));

    fn = new Function("GET(TO_LIST(_([1, 2, 3])), 2)");
    Assert.assertEquals(BoxedType.create("3"), fn.evaluate(Function.definitions()));
  }
}
