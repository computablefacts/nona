package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.junit.Assert;
import org.junit.Test;

public class IsNullOrBlankTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsNullOrBlankNoParameters() {

    Function fn = new Function("IS_NULL_OR_BLANK()");
    BoxedType<?> bt = fn.evaluate(Function.definitions());
  }

  @Test
  public void testIsNullOrBlankOfInteger() {

    Function fn = new Function("IS_NULL_OR_BLANK(3)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOrBlankOfDouble() {

    Function fn = new Function("IS_NULL_OR_BLANK(3.14)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOrBlankOfString() {

    Function fn = new Function("IS_NULL_OR_BLANK(test)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOrBlankOfEmptyString() {

    Function fn = new Function("IS_NULL_OR_BLANK(\"\")");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOrBlankOfBlankString() {

    Function fn = new Function("IS_NULL_OR_BLANK(\"   \")");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }
}
