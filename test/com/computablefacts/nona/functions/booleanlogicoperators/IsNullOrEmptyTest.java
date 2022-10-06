package com.computablefacts.nona.functions.booleanlogicoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.asterix.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class IsNullOrEmptyTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsNullOrEmptyNoParameters() {

    Function fn = new Function("IS_NULL_OR_EMPTY()");
    BoxedType<?> bt = fn.evaluate(Function.definitions());
  }

  @Test
  public void testIsNullOrEmptyOfInteger() {

    Function fn = new Function("IS_NULL_OR_EMPTY(3)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOrEmptyOfDouble() {

    Function fn = new Function("IS_NULL_OR_EMPTY(3.14)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOrEmptyOfString() {

    Function fn = new Function("IS_NULL_OR_EMPTY(test)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOrEmptyOfEmptyString() {

    Function fn = new Function("IS_NULL_OR_EMPTY(\"\")");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOrEmptyOfBlankString() {

    Function fn = new Function("IS_NULL_OR_EMPTY(\"   \")");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }
}
