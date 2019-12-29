package com.computablefacts.nona.functions.comparisonoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class EqualTest {

  @Test
  public void testEqualTrueFalse() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EQUAL", new Equal());

    Function fn = new Function("EQUAL(true, false)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }

  @Test
  public void testEqualFalseTrue() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EQUAL", new Equal());

    Function fn = new Function("EQUAL(false, true)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }

  @Test
  public void testEqualTrueTrue() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EQUAL", new Equal());

    Function fn = new Function("EQUAL(true, true)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testEqualFalseFalse() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EQUAL", new Equal());

    Function fn = new Function("EQUAL(false, false)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testEqualNumeric() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EQUAL", new Equal());

    Function fn = new Function("EQUAL(1, 1)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));

    Function fn2 = new Function("EQUAL(1, 2)");
    Assert.assertEquals(BoxedType.create(false), fn2.evaluate(functions));
  }
}
