package com.computablefacts.nona.functions.additiveoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class AddTest {

  @Test
  public void testSimpleAddFunction() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("ADD", new Add());

    Function fn = new Function("ADD(1, 1)");
    Assert.assertEquals(BoxedType.create(2.0), fn.evaluate(functions));
  }

  @Test
  public void testComplexAddFunction() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("ADD", new Add());
    functions.put("SUB", new Substract());

    Function fn = new Function("SUB(ADD(1, 1), 1)");
    Assert.assertEquals(BoxedType.create(1.0), fn.evaluate(functions));
  }
}
