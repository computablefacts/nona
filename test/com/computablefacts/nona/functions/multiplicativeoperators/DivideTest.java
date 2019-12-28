package com.computablefacts.nona.functions.multiplicativeoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class DivideTest {

  @Test
  public void testDiv() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("DIV", new Divide());

    Function fn = new Function("DIV(2, 2)");
    Assert.assertEquals(BoxedType.create(1.0), fn.evaluate(functions));
  }
}
