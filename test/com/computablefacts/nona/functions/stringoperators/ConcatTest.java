package com.computablefacts.nona.functions.stringoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class ConcatTest {

  @Test
  public void testConcatSpace() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("CONCAT", new Concat());

    Function fn = new Function("CONCAT(arg1, \" \", arg2, \" \", arg3)");
    Assert.assertEquals(BoxedType.create("arg1 arg2 arg3"), fn.evaluate(functions));
  }

  @Test
  public void testConcatComma() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("CONCAT", new Concat());

    Function fn = new Function("CONCAT(arg1, \", \", arg2, \", \", arg3)");
    Assert.assertEquals(BoxedType.create("arg1, arg2, arg3"), fn.evaluate(functions));
  }
}
