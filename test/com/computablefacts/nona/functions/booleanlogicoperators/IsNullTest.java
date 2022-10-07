package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.junit.Assert;
import org.junit.Test;

public class IsNullTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsNullNoParameters() {

    Function fn = new Function("IS_NULL()");
    BoxedType<?> bt = fn.evaluate(Function.definitions());
  }

  @Test
  public void testIsNullOfString() {

    Function fn = new Function("IS_NULL(test)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOfInteger() {

    Function fn = new Function("IS_NULL(3)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOfDouble() {

    Function fn = new Function("IS_NULL(3.14)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsNullOfEmptyString() {

    Function fn = new Function("IS_NULL(\"\")");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }
}
