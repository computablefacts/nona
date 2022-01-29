package com.computablefacts.nona.functions.listoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.errorprone.annotations.Var;

public class HeadTest {

  @Test
  public void testHeadOnEmptyList() {
    Function fn = new Function("HEAD(TO_LIST([]))");
    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testHead() {
    @Var
    Function fn = new Function("HEAD(TO_LIST(_([1, 2, 3])))");
    Assert.assertEquals(BoxedType.create("1"), fn.evaluate(Function.definitions()));
  }
}
