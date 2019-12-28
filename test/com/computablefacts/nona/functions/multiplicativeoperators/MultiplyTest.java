package com.computablefacts.nona.functions.multiplicativeoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class MultiplyTest {

  @Test
  public void testMult() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MULT", new Multiply());

    Function fn = new Function("MULT(2, 2)");
    Assert.assertEquals(BoxedType.create(4.0), fn.evaluate(functions));
  }
}
