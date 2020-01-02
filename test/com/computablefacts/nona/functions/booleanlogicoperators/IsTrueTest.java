package com.computablefacts.nona.functions.booleanlogicoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class IsTrueTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testIsTrueEmpty() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS_TRUE", new IsTrue());

    Function fn = new Function("IS_TRUE()");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }

  @Test
  public void testIsTrueString() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS_TRUE", new IsTrue());

    Function fn = new Function("IS_TRUE(test)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }

  @Test
  public void testIsTrueInteger() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS_TRUE", new IsTrue());

    Function fn = new Function("IS_TRUE(3)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }

  @Test
  public void testIsTrueDouble() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS_TRUE", new IsTrue());

    Function fn = new Function("IS_TRUE(3.14)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }

  @Test
  public void testIsTrueTrue() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS_TRUE", new IsTrue());

    Function fn = new Function("IS_TRUE(true)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testIsTrueFalse() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS_TRUE", new IsTrue());

    Function fn = new Function("IS_TRUE(false)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }
}
