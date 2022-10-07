package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.junit.Assert;
import org.junit.Test;

public class IsBlankTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsBlankNoParameters() {

    Function fn = new Function("IS_BLANK()");
    BoxedType<?> bt = fn.evaluate(Function.definitions());
  }

  @Test
  public void testIsBlankOfString() {

    Function fn = new Function("IS_BLANK(test)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsBlankOfInteger() {

    Function fn = new Function("IS_BLANK(3)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsBlankOfDouble() {

    Function fn = new Function("IS_BLANK(3.14)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsBlankOfEmptyString() {

    Function fn = new Function("IS_BLANK(\"\")");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsBlankOfBlankString() {

    Function fn = new Function("IS_BLANK(\"   \")");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }
}
