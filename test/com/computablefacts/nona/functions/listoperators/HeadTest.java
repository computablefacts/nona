package com.computablefacts.nona.functions.listoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class HeadTest {

  @Test
  public void testHeadOnEmptyList() {
    Function fn = new Function("HEAD(TO_LIST([]))");
    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testHead() {

    String array = Function.wrap("[1, 2, 3]");
    Function fn = new Function(String.format("HEAD(TO_LIST(%s))", array));

    Assert.assertEquals(BoxedType.of("1"), fn.evaluate(Function.definitions()));
  }
}
