package com.computablefacts.nona.functions.stringoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class ToDecimalTest {

  @Test
  public void testDecimalToDecimal() {

    Function fn = new Function("TO_DECIMAL(2018.123)");
    Assert.assertEquals(BoxedType.create(2018.123), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIntegerToDecimal() {

    Function fn = new Function("TO_DECIMAL(2018)");
    Assert.assertEquals(BoxedType.create(2018), fn.evaluate(Function.definitions()));
  }
}
