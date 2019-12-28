package com.computablefacts.nona.functions.stringoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class ToIntegerTest {

  @Test
  public void testToInteger() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("TOINTEGER", new ToInteger());

    Function fn = new Function("TOINTEGER(2018.0)");
    Assert.assertEquals(BoxedType.create(2018), fn.evaluate(functions));
  }
}
