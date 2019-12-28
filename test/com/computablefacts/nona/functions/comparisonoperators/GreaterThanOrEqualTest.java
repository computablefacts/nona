package com.computablefacts.nona.functions.comparisonoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class GreaterThanOrEqualTest {

  @Test
  public void testGte() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("GTE", new GreaterThanOrEqual());

    Function fn = new Function("GTE(cyrille, patrick)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));

    Function fn2 = new Function("GTE(patrick, cyrille)");
    Assert.assertEquals(BoxedType.create(true), fn2.evaluate(functions));

    Function fn3 = new Function("GTE(cyrille, cyrille)");
    Assert.assertEquals(BoxedType.create(true), fn3.evaluate(functions));
  }

  @Test
  public void testGteNumeric() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("GTE", new GreaterThanOrEqual());

    Function fn = new Function("GTE(1, 10)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));

    Function fn2 = new Function("GTE(10, 1)");
    Assert.assertEquals(BoxedType.create(true), fn2.evaluate(functions));

    Function fn3 = new Function("GTE(1, 1)");
    Assert.assertEquals(BoxedType.create(true), fn3.evaluate(functions));
  }
}
