package com.computablefacts.nona.functions.booleanlogicoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class IsNullOrBlankTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsNullOrBlankNoParameters() {

    Function fn = new Function("IS_NULL_OR_BLANK()");
    BoxedType<?> bt = fn.evaluate(Function.definitions());
  }

  @Test
  public void testIsNullOrBlankOfInteger() {

    Function fn = new Function("IS_NULL_OR_BLANK(3)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOrBlankOfDouble() {

    Function fn = new Function("IS_NULL_OR_BLANK(3.14)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOrBlankOfString() {

    Function fn = new Function("IS_NULL_OR_BLANK(test)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOrBlankOfEmptyString() {

    Function fn = new Function("IS_NULL_OR_BLANK(\"\")");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOrBlankOfBlankString() {

    Function fn = new Function("IS_NULL_OR_BLANK(\"   \")");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }
}
