package com.computablefacts.nona.functions.booleanlogicoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class IsNullTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsNullEmpty() {

    Function fn = new Function("IS_NULL()");
    BoxedType<?> bt = fn.evaluate(Function.definitions());
  }

  @Test
  public void testIsNullString() {

    Function fn = new Function("IS_NULL(test)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullInteger() {

    Function fn = new Function("IS_NULL(3)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullDouble() {

    Function fn = new Function("IS_NULL(3.14)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullEmptyString() {

    Function fn = new Function("IS_NULL(\"\")");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }
}
