package com.computablefacts.nona.functions.booleanlogicoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.asterix.BoxedType;

public class NotTest {

  @Test
  public void testNotTrue() {

    Function fn = new Function("NOT(true)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testNotFalse() {

    Function fn = new Function("NOT(false)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }
}
