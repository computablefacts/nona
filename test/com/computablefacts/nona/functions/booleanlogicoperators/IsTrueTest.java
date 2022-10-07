package com.computablefacts.nona.functions.booleanlogicoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.junit.Assert;
import org.junit.Test;

public class IsTrueTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsTrueEmpty() {

    Function fn = new Function("IS_TRUE()");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsTrueString() {

    Function fn = new Function("IS_TRUE(test)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsTrueInteger() {

    Function fn = new Function("IS_TRUE(3)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsTrueDouble() {

    Function fn = new Function("IS_TRUE(3.14)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsTrueTrue() {

    Function fn = new Function("IS_TRUE(true)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsTrueFalse() {

    Function fn = new Function("IS_TRUE(false)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }
}
