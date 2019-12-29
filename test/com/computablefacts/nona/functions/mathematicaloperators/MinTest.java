package com.computablefacts.nona.functions.mathematicaloperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.google.common.util.concurrent.UncheckedExecutionException;

public class MinTest {

  @Test(expected = UncheckedExecutionException.class)
  public void testMinWithStrings() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MIN", new Min());

    Function fn = new Function("MIN(1, 2, 3, alexis, cyrille, sophie)");
    Assert.assertEquals(BoxedType.create("sophie"), fn.evaluate(functions));
  }

  @Test
  public void testMinWithSortedIntegers() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MIN", new Min());

    Function fn = new Function("MIN(1, 2, 3, 4, 5)");
    Assert.assertEquals(BoxedType.create(1), fn.evaluate(functions));
  }

  @Test
  public void testMinWithSortedDoubles() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MIN", new Min());

    Function fn = new Function("MIN(1.1, 2.2, 3.3, 4.4, 5.5)");
    Assert.assertEquals(BoxedType.create(1.1), fn.evaluate(functions));
  }

  @Test
  public void testMinWithSortedIntegersAndDoubles() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MIN", new Min());

    Function fn = new Function("MIN(1.1, 2, 3.3, 4, 5.5)");
    Assert.assertEquals(BoxedType.create(1.1), fn.evaluate(functions));
  }

  @Test
  public void testMinWithUnsortedIntegers() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MIN", new Min());

    Function fn = new Function("MIN(1, 3, 5, 2, 4)");
    Assert.assertEquals(BoxedType.create(1), fn.evaluate(functions));
  }

  @Test
  public void testMinWithUnsortedDoubles() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MIN", new Min());

    Function fn = new Function("MIN(1.1, 3.3, 5.5, 2.2, 4.4)");
    Assert.assertEquals(BoxedType.create(1.1), fn.evaluate(functions));
  }

  @Test
  public void testMinWithUnsortedIntegersAndDoubles() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MIN", new Min());

    Function fn = new Function("MIN(1.1, 3.3, 5.5, 2, 4)");
    Assert.assertEquals(BoxedType.create(1.1), fn.evaluate(functions));
  }
}
