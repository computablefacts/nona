package com.computablefacts.nona.functions.booleanlogicoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.asterix.BoxedType;

public class OrTest {

  @Test
  public void testOrTrueFalse() {

    Function fn = new Function("OR(true, false)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testOrFalseTrue() {

    Function fn = new Function("OR(false, true)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testOrTrueTrue() {

    Function fn = new Function("OR(true, true)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testOrFalseFalse() {

    Function fn = new Function("OR(false, false)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }
}
