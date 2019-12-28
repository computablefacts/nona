package com.computablefacts.nona.functions.comparisonoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class LessThanOrEqualTest {

  @Test
  public void testLte() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("LTE", new LessThanOrEqual());

    Function fn1 = new Function("LTE(cyrille, patrick)");
    Assert.assertEquals(BoxedType.create(true), fn1.evaluate(functions));

    Function fn2 = new Function("LTE(patrick, cyrille)");
    Assert.assertEquals(BoxedType.create(false), fn2.evaluate(functions));

    Function fn3 = new Function("LTE(cyrille, cyrille)");
    Assert.assertEquals(BoxedType.create(true), fn3.evaluate(functions));
  }

  @Test
  public void testLteNumeric() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("LTE", new LessThanOrEqual());

    Function fn1 = new Function("LTE(1, 10)");
    Assert.assertEquals(BoxedType.create(true), fn1.evaluate(functions));

    Function fn2 = new Function("LTE(10, 1)");
    Assert.assertEquals(BoxedType.create(false), fn2.evaluate(functions));

    Function fn3 = new Function("LTE(1, 1)");
    Assert.assertEquals(BoxedType.create(true), fn3.evaluate(functions));
  }
}
