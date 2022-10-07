package com.computablefacts.nona.functions.listoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class SizeTest {

  @Test
  public void testSizeOnEmptyList() {
    Function fn = new Function("SIZE(TO_LIST([]))");
    Assert.assertEquals(BoxedType.of(0), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testSize() {

    String array = Function.wrap("[1, 2, 3]");
    Function fn = new Function(String.format("SIZE(TO_LIST(%s))", array));

    Assert.assertEquals(BoxedType.of(3), fn.evaluate(Function.definitions()));
  }
}
