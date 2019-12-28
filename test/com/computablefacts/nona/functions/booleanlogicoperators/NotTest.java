package com.computablefacts.nona.functions.booleanlogicoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class NotTest {

  @Test
  public void testNotTrue() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("NOT", new Not());

    Function fn = new Function("NOT(true)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }

  @Test
  public void testNotFalse() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("NOT", new Not());

    Function fn = new Function("NOT(false)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }
}
