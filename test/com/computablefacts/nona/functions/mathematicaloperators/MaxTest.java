package com.computablefacts.nona.functions.mathematicaloperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.asterix.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class MaxTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testMaxWithStrings() {

    Function fn = new Function("MAX(1, 2, 3, alexis, cyrille, sophie)");
    Assert.assertEquals(BoxedType.of("sophie"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMaxWithSortedIntegers() {

    Function fn = new Function("MAX(1, 2, 3, 4, 5)");
    Assert.assertEquals(BoxedType.of(5), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMaxWithSortedDoubles() {

    Function fn = new Function("MAX(1.1, 2.2, 3.3, 4.4, 5.5)");
    Assert.assertEquals(BoxedType.of(5.5), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMaxWithSortedIntegersAndDoubles() {

    Function fn = new Function("MAX(1.1, 2, 3.3, 4, 5.5)");
    Assert.assertEquals(BoxedType.of(5.5), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMaxWithUnsortedIntegers() {

    Function fn = new Function("MAX(1, 3, 5, 2, 4)");
    Assert.assertEquals(BoxedType.of(5), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMaxWithUnsortedDoubles() {

    Function fn = new Function("MAX(1.1, 3.3, 5.5, 2.2, 4.4)");
    Assert.assertEquals(BoxedType.of(5.5), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMaxWithUnsortedIntegersAndDoubles() {

    Function fn = new Function("MAX(1.1, 3.3, 5.5, 2, 4)");
    Assert.assertEquals(BoxedType.of(5.5), fn.evaluate(Function.definitions()));
  }
}
