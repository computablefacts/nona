package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.junit.Assert;
import org.junit.Test;

public class IsFalseTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsFalseEmpty() {

    Function fn = new Function("IS_FALSE()");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsFalseString() {

    Function fn = new Function("IS_FALSE(test)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsFalseInteger() {

    Function fn = new Function("IS_FALSE(3)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsFalseDouble() {

    Function fn = new Function("IS_FALSE(3.14)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsFalseTrue() {

    Function fn = new Function("IS_FALSE(true)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsFalseFalse() {

    Function fn = new Function("IS_FALSE(false)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }
}
