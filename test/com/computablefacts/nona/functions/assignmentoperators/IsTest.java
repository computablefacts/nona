package com.computablefacts.nona.functions.assignmentoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class IsTest {

  @Test
  public void testIsFunctionWithInteger() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS", new Is());

    Function fn = new Function("IS(1)");
    Assert.assertEquals(BoxedType.create(1), fn.evaluate(functions));
  }

  @Test
  public void testIsFunctionWithDouble() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS", new Is());

    Function fn = new Function("IS(1.9)");
    Assert.assertEquals(BoxedType.create(1.9), fn.evaluate(functions));
  }

  @Test
  public void testIsFunctionWithUppercaseString() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS", new Is());

    Function fn = new Function("IS(TEST)");
    Assert.assertEquals(BoxedType.create("TEST"), fn.evaluate(functions));
  }

  @Test
  public void testIsFunctionWithLowercaseString() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IS", new Is());

    Function fn = new Function("IS(test)");
    Assert.assertEquals(BoxedType.create("test"), fn.evaluate(functions));
  }
}
