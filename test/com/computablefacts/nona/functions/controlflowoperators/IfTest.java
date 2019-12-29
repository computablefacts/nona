package com.computablefacts.nona.functions.controlflowoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.functions.comparisonoperators.GreaterThanOrEqual;
import com.computablefacts.nona.types.BoxedType;

public class IfTest {

  @Test
  public void testIfWithIntegers() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IF", new If());
    functions.put("GTE", new GreaterThanOrEqual());

    Function fn = new Function("IF(GTE(1, 2), 1, 2)");
    Assert.assertEquals(BoxedType.create(2), fn.evaluate(functions));
  }

  @Test
  public void testIfWithDoubles() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IF", new If());
    functions.put("GTE", new GreaterThanOrEqual());

    Function fn = new Function("IF(GTE(1.1, 2.2), 1.1, 2.2)");
    Assert.assertEquals(BoxedType.create(2.2), fn.evaluate(functions));
  }

  @Test
  public void testIfWithIntegersAndDoubles() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IF", new If());
    functions.put("GTE", new GreaterThanOrEqual());

    Function fn = new Function("IF(GTE(1, 1.0), 1, 1.0)");
    Assert.assertEquals(BoxedType.create(1), fn.evaluate(functions));
  }

  @Test
  public void testIfWithStrings() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IF", new If());
    functions.put("GTE", new GreaterThanOrEqual());

    Function fn = new Function("IF(GTE(cyrille, patrick), cyrille, patrick)");
    Assert.assertEquals(BoxedType.create("patrick"), fn.evaluate(functions));
  }
}
