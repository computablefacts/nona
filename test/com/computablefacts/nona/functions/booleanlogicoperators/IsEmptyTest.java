package com.computablefacts.nona.functions.booleanlogicoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class IsEmptyTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsEmptyEmpty() {

    Function fn = new Function("IS_EMPTY()");
    BoxedType<?> bt = fn.evaluate(Function.definitions());
  }

  @Test
  public void testIsEmptyString() {

    Function fn = new Function("IS_EMPTY(test)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test(expected = UncheckedExecutionException.class)
  public void testIsEmptyInteger() {

    Function fn = new Function("IS_EMPTY(3)");
    BoxedType<?> bt = fn.evaluate(Function.definitions());
  }

  @Test(expected = UncheckedExecutionException.class)
  public void testIsEmptyDouble() {

    Function fn = new Function("IS_EMPTY(3.14)");
    BoxedType<?> bt = fn.evaluate(Function.definitions());
  }

  @Test
  public void testIsEmptyEmptyString() {

    Function fn = new Function("IS_EMPTY(\"\")");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }
}
