package com.computablefacts.nona.functions.booleanlogicoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class IsFalseTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsFalseEmpty() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS_FALSE", new IsFalse());

    Function fn = new Function("IS_FALSE()");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testIsFalseString() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS_FALSE", new IsFalse());

    Function fn = new Function("IS_FALSE(test)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testIsFalseInteger() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS_FALSE", new IsFalse());

    Function fn = new Function("IS_FALSE(3)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testIsFalseDouble() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS_FALSE", new IsFalse());

    Function fn = new Function("IS_FALSE(3.14)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testIsFalseTrue() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS_FALSE", new IsFalse());

    Function fn = new Function("IS_FALSE(true)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }

  @Test
  public void testIsFalseFalse() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS_FALSE", new IsFalse());

    Function fn = new Function("IS_FALSE(false)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }
}
