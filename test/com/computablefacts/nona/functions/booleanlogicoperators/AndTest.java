package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class AndTest {

  @Test
  public void testAndTrueFalse() {

    Function fn = new Function("AND(true, false)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testAndFalseTrue() {

    Function fn = new Function("AND(false, true)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testAndTrueTrue() {

    Function fn = new Function("AND(true, true)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testAndFalseFalse() {

    Function fn = new Function("AND(false, false)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }
}
