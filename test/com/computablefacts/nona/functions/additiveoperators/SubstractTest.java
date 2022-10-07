package com.computablefacts.nona.functions.additiveoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class SubstractTest {

  @Test
  public void testSimpleSubFunction() {

    Function fn = new Function("SUB(1, 1)");
    Assert.assertEquals(BoxedType.of(0.0), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testComplexSubFunction() {

    Function fn = new Function("SUB(ADD(1, 1), 1)");
    Assert.assertEquals(BoxedType.of(1.0), fn.evaluate(Function.definitions()));
  }
}
