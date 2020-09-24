package com.computablefacts.nona.functions.controlflowoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class WhichTest {

  @Test
  public void testWhich() {

    Map<String, BoxedType> substitutions = new HashMap<>();
    substitutions.put("x", BoxedType.create(10));

    Function fn = new Function("WHICH(LT(x, 0), negative, GT(x, 0), positive, EQUAL(x, 0), zero)");

    Assert.assertEquals(BoxedType.create("positive"),
        fn.evaluate(Function.definitions(), substitutions));
  }

  @Test
  public void testWhichWithDefaultStatement() {

    Map<String, BoxedType> substitutions = new HashMap<>();
    substitutions.put("x", BoxedType.create(0.0));

    Function fn = new Function("WHICH(LT(x, 0), negative, GT(x, 0), positive, _, zero)");
    Assert.assertEquals(BoxedType.create("zero"),
        fn.evaluate(Function.definitions(), substitutions));
  }

  @Test(expected = IllegalStateException.class)
  public void testWhichWithMissingStatement() {

    Map<String, BoxedType> substitutions = new HashMap<>();
    substitutions.put("x", BoxedType.create(0.0));

    Function fn = new Function("WHICH(LT(x, 0), negative, GT(x, 0), positive)");
    Assert.assertEquals(BoxedType.create(null), fn.evaluate(Function.definitions(), substitutions));
  }
}
