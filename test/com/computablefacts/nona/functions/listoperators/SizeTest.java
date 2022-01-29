package com.computablefacts.nona.functions.listoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.errorprone.annotations.Var;

public class SizeTest {

  @Test
  public void testSizeOnEmptyList() {
    Function fn = new Function("SIZE(TO_LIST([]))");
    Assert.assertEquals(BoxedType.create(0), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testSize() {
    @Var
    Function fn = new Function("SIZE(TO_LIST(_([1, 2, 3])))");
    Assert.assertEquals(BoxedType.create(3), fn.evaluate(Function.definitions()));
  }
}
