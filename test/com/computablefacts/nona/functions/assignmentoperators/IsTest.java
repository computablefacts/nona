package com.computablefacts.nona.functions.assignmentoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class IsTest {

  @Test
  public void testIsFunctionWithInteger() {

    Function fn = new Function("IS(1)");
    Assert.assertEquals(BoxedType.of(1), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsFunctionWithDouble() {

    Function fn = new Function("IS(1.9)");
    Assert.assertEquals(BoxedType.of(1.9), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsFunctionWithUppercaseString() {

    Function fn = new Function("IS(TEST)");
    Assert.assertEquals(BoxedType.of("TEST"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIsFunctionWithLowercaseString() {

    Function fn = new Function("IS(test)");
    Assert.assertEquals(BoxedType.of("test"), fn.evaluate(Function.definitions()));
  }
}
