package com.computablefacts.nona.functions.mathematicaloperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class FloorTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testFloorWithStrings() {

    Function fn = new Function("FLOOR(\"test\")");
    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testFloorWithInteger() {

    Function fn = new Function("FLOOR(1)");
    Assert.assertEquals(BoxedType.create(1), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testFloorWithDecimal() {

    Function fn = new Function("FLOOR(1.1)");
    Assert.assertEquals(BoxedType.create(1), fn.evaluate(Function.definitions()));
  }
}
