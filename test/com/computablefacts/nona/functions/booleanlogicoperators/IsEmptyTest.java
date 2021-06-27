package com.computablefacts.nona.functions.booleanlogicoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class IsEmptyTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsEmptyNoParameters() {

    Function fn = new Function("IS_EMPTY()");
    BoxedType<?> bt = fn.evaluate(Function.definitions());
  }

  @Test
  public void testIsEmptyOfString() {

    Function fn = new Function("IS_EMPTY(test)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsEmptyOfInteger() {

    Function fn = new Function("IS_EMPTY(3)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsEmptyOfDouble() {

    Function fn = new Function("IS_EMPTY(3.14)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsEmptyOfEmptyString() {

    Function fn = new Function("IS_EMPTY(\"\")");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsEmptyOfBlankString() {

    Function fn = new Function("IS_EMPTY(\"   \")");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }
}
