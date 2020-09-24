package com.computablefacts.nona.functions.additiveoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class SubstractTest {

  @Test
  public void testSimpleSubFunction() {

    Function fn = new Function("SUB(1, 1)");
    Assert.assertEquals(BoxedType.create(0.0), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testComplexSubFunction() {

    Function fn = new Function("SUB(ADD(1, 1), 1)");
    Assert.assertEquals(BoxedType.create(1.0), fn.evaluate(Function.definitions()));
  }
}
