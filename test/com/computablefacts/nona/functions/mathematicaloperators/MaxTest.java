package com.computablefacts.nona.functions.mathematicaloperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class MaxTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testMaxWithStrings() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MAX", new Max());

    Function fn = new Function("MAX(1, 2, 3, alexis, cyrille, sophie)");
    Assert.assertEquals(BoxedType.create("sophie"), fn.evaluate(functions));
  }

  @Test
  public void testMaxWithSortedIntegers() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MAX", new Max());

    Function fn = new Function("MAX(1, 2, 3, 4, 5)");
    Assert.assertEquals(BoxedType.create(5), fn.evaluate(functions));
  }

  @Test
  public void testMaxWithSortedDoubles() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MAX", new Max());

    Function fn = new Function("MAX(1.1, 2.2, 3.3, 4.4, 5.5)");
    Assert.assertEquals(BoxedType.create(5.5), fn.evaluate(functions));
  }

  @Test
  public void testMaxWithSortedIntegersAndDoubles() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MAX", new Max());

    Function fn = new Function("MAX(1.1, 2, 3.3, 4, 5.5)");
    Assert.assertEquals(BoxedType.create(5.5), fn.evaluate(functions));
  }

  @Test
  public void testMaxWithUnsortedIntegers() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MAX", new Max());

    Function fn = new Function("MAX(1, 3, 5, 2, 4)");
    Assert.assertEquals(BoxedType.create(5), fn.evaluate(functions));
  }

  @Test
  public void testMaxWithUnsortedDoubles() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MAX", new Max());

    Function fn = new Function("MAX(1.1, 3.3, 5.5, 2.2, 4.4)");
    Assert.assertEquals(BoxedType.create(5.5), fn.evaluate(functions));
  }

  @Test
  public void testMaxWithUnsortedIntegersAndDoubles() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MAX", new Max());

    Function fn = new Function("MAX(1.1, 3.3, 5.5, 2, 4)");
    Assert.assertEquals(BoxedType.create(5.5), fn.evaluate(functions));
  }
}
