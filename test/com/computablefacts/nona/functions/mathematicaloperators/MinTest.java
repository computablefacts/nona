package com.computablefacts.nona.functions.mathematicaloperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class MinTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testMinWithStrings() {

    Function fn = new Function("MIN(1, 2, 3, alexis, cyrille, sophie)");
    Assert.assertEquals(BoxedType.create("sophie"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMinWithSortedIntegers() {

    Function fn = new Function("MIN(1, 2, 3, 4, 5)");
    Assert.assertEquals(BoxedType.create(1), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMinWithSortedDoubles() {

    Function fn = new Function("MIN(1.1, 2.2, 3.3, 4.4, 5.5)");
    Assert.assertEquals(BoxedType.create(1.1), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMinWithSortedIntegersAndDoubles() {

    Function fn = new Function("MIN(1.1, 2, 3.3, 4, 5.5)");
    Assert.assertEquals(BoxedType.create(1.1), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMinWithUnsortedIntegers() {

    Function fn = new Function("MIN(1, 3, 5, 2, 4)");
    Assert.assertEquals(BoxedType.create(1), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMinWithUnsortedDoubles() {

    Function fn = new Function("MIN(1.1, 3.3, 5.5, 2.2, 4.4)");
    Assert.assertEquals(BoxedType.create(1.1), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMinWithUnsortedIntegersAndDoubles() {

    Function fn = new Function("MIN(1.1, 3.3, 5.5, 2, 4)");
    Assert.assertEquals(BoxedType.create(1.1), fn.evaluate(Function.definitions()));
  }
}
