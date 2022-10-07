package com.computablefacts.nona.functions.multiplicativeoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class MultiplyTest {

  @Test
  public void testMultZero() {

    Function fn = new Function("MUL(3, 2, 1, 0)");
    Assert.assertEquals(BoxedType.of(0), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMult() {

    Function fn = new Function("MUL(2, 2)");
    Assert.assertEquals(BoxedType.of(4.0), fn.evaluate(Function.definitions()));
  }
}
