package com.computablefacts.nona.functions.comparisonoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class GreaterThanTest {

  @Test
  public void testGt() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("GT", new GreaterThan());

    Function fn = new Function("GT(cyrille, patrick)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));

    Function fn2 = new Function("GT(patrick, cyrille)");
    Assert.assertEquals(BoxedType.create(true), fn2.evaluate(functions));

    Function fn3 = new Function("GT(cyrille, cyrille)");
    Assert.assertEquals(BoxedType.create(false), fn3.evaluate(functions));
  }

  @Test
  public void testGtNumeric() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("GT", new GreaterThan());

    Function fn = new Function("GT(1, 10)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));

    Function fn2 = new Function("GT(10, 1)");
    Assert.assertEquals(BoxedType.create(true), fn2.evaluate(functions));

    Function fn3 = new Function("GT(1, 1)");
    Assert.assertEquals(BoxedType.create(false), fn3.evaluate(functions));
  }
}
