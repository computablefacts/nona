package com.computablefacts.nona.functions.booleanlogicoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class IsBlankTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsBlankNoParameters() {

    Function fn = new Function("IS_BLANK()");
    BoxedType<?> bt = fn.evaluate(Function.definitions());
  }

  @Test
  public void testIsBlankOfString() {

    Function fn = new Function("IS_BLANK(test)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsBlankOfInteger() {

    Function fn = new Function("IS_BLANK(3)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsBlankOfDouble() {

    Function fn = new Function("IS_BLANK(3.14)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsBlankOfEmptyString() {

    Function fn = new Function("IS_BLANK(\"\")");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsBlankOfBlankString() {

    Function fn = new Function("IS_BLANK(\"   \")");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }
}
