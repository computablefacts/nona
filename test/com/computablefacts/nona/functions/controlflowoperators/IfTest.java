package com.computablefacts.nona.functions.controlflowoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class IfTest {

  @Test
  public void testIfWithIntegers() {

    Function fn = new Function("IF(GTE(1, 2), 1, 2)");
    Assert.assertEquals(BoxedType.create(2), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIfWithDoubles() {

    Function fn = new Function("IF(GTE(1.1, 2.2), 1.1, 2.2)");
    Assert.assertEquals(BoxedType.create(2.2), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIfWithIntegersAndDoubles() {

    Function fn = new Function("IF(GTE(1, 1.0), 1, 1.0)");
    Assert.assertEquals(BoxedType.create(1), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIfWithStrings() {

    Function fn = new Function("IF(GTE(cyrille, patrick), cyrille, patrick)");
    Assert.assertEquals(BoxedType.create("patrick"), fn.evaluate(Function.definitions()));
  }
}
