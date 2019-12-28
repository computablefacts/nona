package com.computablefacts.nona.functions.booleanlogicoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.functions.comparisonoperators.Equal;
import com.computablefacts.nona.types.BoxedType;

public class OrTest {

  @Test
  public void testOrTrueFalse() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("AND", new And());
    functions.put("OR", new Or());
    functions.put("EQUAL", new Equal());

    Function fn = new Function("OR(true, false)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testOrFalseTrue() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("AND", new And());
    functions.put("OR", new Or());
    functions.put("EQUAL", new Equal());

    Function fn = new Function("OR(false, true)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testOrTrueTrue() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("AND", new And());
    functions.put("OR", new Or());
    functions.put("EQUAL", new Equal());

    Function fn = new Function("OR(true, true)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testOrFalseFalse() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("AND", new And());
    functions.put("OR", new Or());
    functions.put("EQUAL", new Equal());

    Function fn = new Function("OR(false, false)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }
}
