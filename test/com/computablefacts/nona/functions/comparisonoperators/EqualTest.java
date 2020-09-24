package com.computablefacts.nona.functions.comparisonoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class EqualTest {

  @Test
  public void testEqualTrueFalse() {

    Function fn = new Function("EQUAL(true, false)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEqualFalseTrue() {

    Function fn = new Function("EQUAL(false, true)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEqualTrueTrue() {

    Function fn = new Function("EQUAL(true, true)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEqualFalseFalse() {

    Function fn = new Function("EQUAL(false, false)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEqualNumeric() {

    Function fn = new Function("EQUAL(1, 1)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));

    Function fn2 = new Function("EQUAL(1, 2)");
    Assert.assertEquals(BoxedType.create(false), fn2.evaluate(Function.definitions()));
  }
}
