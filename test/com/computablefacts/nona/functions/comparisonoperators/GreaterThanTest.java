package com.computablefacts.nona.functions.comparisonoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class GreaterThanTest {

  @Test
  public void testGt() {

    Function fn = new Function("GT(cyrille, patrick)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));

    Function fn2 = new Function("GT(patrick, cyrille)");
    Assert.assertEquals(BoxedType.create(true), fn2.evaluate(Function.definitions()));

    Function fn3 = new Function("GT(cyrille, cyrille)");
    Assert.assertEquals(BoxedType.create(false), fn3.evaluate(Function.definitions()));
  }

  @Test
  public void testGtNumeric() {

    Function fn = new Function("GT(1, 10)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));

    Function fn2 = new Function("GT(10, 1)");
    Assert.assertEquals(BoxedType.create(true), fn2.evaluate(Function.definitions()));

    Function fn3 = new Function("GT(1, 1)");
    Assert.assertEquals(BoxedType.create(false), fn3.evaluate(Function.definitions()));
  }
}
