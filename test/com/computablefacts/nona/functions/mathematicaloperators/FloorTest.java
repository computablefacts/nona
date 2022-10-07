package com.computablefacts.nona.functions.mathematicaloperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.junit.Assert;
import org.junit.Test;

public class FloorTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testFloorWithStrings() {

    Function fn = new Function("FLOOR(\"test\")");
    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testFloorWithInteger() {

    Function fn = new Function("FLOOR(1)");
    Assert.assertEquals(BoxedType.of(1), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testFloorWithDecimal() {

    Function fn = new Function("FLOOR(1.1)");
    Assert.assertEquals(BoxedType.of(1), fn.evaluate(Function.definitions()));
  }
}
