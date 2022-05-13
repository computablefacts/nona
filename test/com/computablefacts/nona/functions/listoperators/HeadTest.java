package com.computablefacts.nona.functions.listoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

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

    Assert.assertEquals(BoxedType.create("1"), fn.evaluate(Function.definitions()));
  }
}
