package com.computablefacts.nona.functions.booleanlogicoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.functions.comparisonoperators.Equal;
import com.computablefacts.nona.types.BoxedType;

public class AndTest {

  @Test
  public void testAndTrueFalse() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("AND", new And());
    functions.put("OR", new Or());
    functions.put("EQUAL", new Equal());

    Function fn = new Function("AND(true, false)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }

  @Test
  public void testAndFalseTrue() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("AND", new And());
    functions.put("OR", new Or());
    functions.put("EQUAL", new Equal());

    Function fn = new Function("AND(false, true)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }

  @Test
  public void testAndTrueTrue() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("AND", new And());
    functions.put("OR", new Or());
    functions.put("EQUAL", new Equal());

    Function fn = new Function("AND(true, true)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testAndFalseFalse() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("AND", new And());
    functions.put("OR", new Or());
    functions.put("EQUAL", new Equal());

    Function fn = new Function("AND(false, false)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }
}
