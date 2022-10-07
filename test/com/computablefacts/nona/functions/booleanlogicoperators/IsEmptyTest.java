package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.junit.Assert;
import org.junit.Test;

public class IsEmptyTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsEmptyNoParameters() {

    Function fn = new Function("IS_EMPTY()");
    BoxedType<?> bt = fn.evaluate(Function.definitions());
  }

  @Test
  public void testIsEmptyOfString() {

    Function fn = new Function("IS_EMPTY(test)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsEmptyOfInteger() {

    Function fn = new Function("IS_EMPTY(3)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsEmptyOfDouble() {

    Function fn = new Function("IS_EMPTY(3.14)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsEmptyOfEmptyString() {

    Function fn = new Function("IS_EMPTY(\"\")");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsEmptyOfBlankString() {

    Function fn = new Function("IS_EMPTY(\"   \")");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }
}
