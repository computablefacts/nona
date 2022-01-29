package com.computablefacts.nona.functions.listoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.collect.Lists;
import com.google.errorprone.annotations.Var;

public class TailTest {

  @Test
  public void testTailOnEmptyList() {
    Function fn = new Function("TAIL(TO_LIST([]))");
    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testTail() {
    @Var
    Function fn = new Function("TAIL(TO_LIST(_([1, 2, 3])))");
    Assert.assertEquals(BoxedType.create(Lists.newArrayList("2", "3")),
        fn.evaluate(Function.definitions()));
  }
}
