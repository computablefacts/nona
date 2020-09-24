package com.computablefacts.nona.functions.booleanlogicoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class IsTrueTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsTrueEmpty() {

    Function fn = new Function("IS_TRUE()");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsTrueString() {

    Function fn = new Function("IS_TRUE(test)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsTrueInteger() {

    Function fn = new Function("IS_TRUE(3)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsTrueDouble() {

    Function fn = new Function("IS_TRUE(3.14)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsTrueTrue() {

    Function fn = new Function("IS_TRUE(true)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsTrueFalse() {

    Function fn = new Function("IS_TRUE(false)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }
}
