package com.computablefacts.nona.functions.mathematicaloperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.asterix.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class CeilTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testCeilWithStrings() {

    Function fn = new Function("CEIL(\"test\")");
    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testCeilWithInteger() {

    Function fn = new Function("CEIL(1)");
    Assert.assertEquals(BoxedType.of(1), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testCeilWithDecimal() {

    Function fn = new Function("CEIL(1.1)");
    Assert.assertEquals(BoxedType.of(2), fn.evaluate(Function.definitions()));
  }
}
