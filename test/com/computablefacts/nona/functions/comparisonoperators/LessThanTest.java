package com.computablefacts.nona.functions.comparisonoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class LessThanTest {

  @Test
  public void testLt() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("LT", new LessThan());

    Function fn1 = new Function("LT(cyrille, patrick)");
    Assert.assertEquals(BoxedType.create(true), fn1.evaluate(functions));

    Function fn2 = new Function("LT(patrick, cyrille)");
    Assert.assertEquals(BoxedType.create(false), fn2.evaluate(functions));

    Function fn3 = new Function("LT(cyrille, cyrille)");
    Assert.assertEquals(BoxedType.create(false), fn3.evaluate(functions));
  }

  @Test
  public void testLtNumeric() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("LT", new LessThan());

    Function fn1 = new Function("LT(1, 10)");
    Assert.assertEquals(BoxedType.create(true), fn1.evaluate(functions));

    Function fn2 = new Function("LT(10, 1)");
    Assert.assertEquals(BoxedType.create(false), fn2.evaluate(functions));

    Function fn3 = new Function("LT(1, 1)");
    Assert.assertEquals(BoxedType.create(false), fn3.evaluate(functions));
  }
}
