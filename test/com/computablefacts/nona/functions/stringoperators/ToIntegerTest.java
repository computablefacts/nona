package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class ToIntegerTest {

  @Test
  public void testIntegerToInteger() {

    Function fn = new Function("TO_INTEGER(2018.0)");
    Assert.assertEquals(BoxedType.of(2018), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testDecimalToInteger() {

    Function fn = new Function("TO_INTEGER(2018.123)");
    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }
}
