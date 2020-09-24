package com.computablefacts.nona.functions.booleanlogicoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class AndTest {

  @Test
  public void testAndTrueFalse() {

    Function fn = new Function("AND(true, false)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testAndFalseTrue() {

    Function fn = new Function("AND(false, true)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testAndTrueTrue() {

    Function fn = new Function("AND(true, true)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testAndFalseFalse() {

    Function fn = new Function("AND(false, false)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }
}
