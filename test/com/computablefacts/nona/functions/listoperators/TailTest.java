package com.computablefacts.nona.functions.listoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

public class TailTest {

  @Test
  public void testTailOnEmptyList() {
    Function fn = new Function("TAIL(TO_LIST([]))");
    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testTail() {

    String array = Function.wrap("[1, 2, 3]");
    Function fn = new Function(String.format("TAIL(TO_LIST(%s))", array));

    Assert.assertEquals(BoxedType.of(Lists.newArrayList(2, 3)), fn.evaluate(Function.definitions()));
  }
}
