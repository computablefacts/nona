package com.computablefacts.nona.functions.multiplicativeoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class MultiplyTest {

  @Test
  public void testMultZero() {

    Function fn = new Function("MUL(3, 2, 1, 0)");
    Assert.assertEquals(BoxedType.create(0), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMult() {

    Function fn = new Function("MUL(2, 2)");
    Assert.assertEquals(BoxedType.create(4.0), fn.evaluate(Function.definitions()));
  }
}
