package com.computablefacts.nona.functions.mathematicaloperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.junit.Assert;
import org.junit.Test;

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
