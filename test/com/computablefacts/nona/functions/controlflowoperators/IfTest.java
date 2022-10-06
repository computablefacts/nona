package com.computablefacts.nona.functions.controlflowoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.asterix.BoxedType;

public class IfTest {

  @Test
  public void testIfWithIntegers() {

    Function fn = new Function("IF(GTE(1, 2), 1, 2)");
    Assert.assertEquals(BoxedType.of(2), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIfWithDoubles() {

    Function fn = new Function("IF(GTE(1.1, 2.2), 1.1, 2.2)");
    Assert.assertEquals(BoxedType.of(2.2), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIfWithIntegersAndDoubles() {

    Function fn = new Function("IF(GTE(1, 1.0), 1, 1.0)");
    Assert.assertEquals(BoxedType.of(1), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIfWithStrings() {

    Function fn = new Function("IF(GTE(cyrille, patrick), cyrille, patrick)");
    Assert.assertEquals(BoxedType.of("patrick"), fn.evaluate(Function.definitions()));
  }
}
