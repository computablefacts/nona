package com.computablefacts.nona.functions.stringoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class SubstringTest {

  @Test
  public void testExtractWholeString() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("SUBSTRING", new Substring());

    Function fn = new Function("SUBSTRING(" + Function.wrap("Hello world!") + ", 0)");
    Assert.assertEquals(BoxedType.create("Hello world!"), fn.evaluate(functions));
  }

  @Test
  public void testExtractSubstring() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("SUBSTRING", new Substring());

    Function fn = new Function("SUBSTRING(" + Function.wrap("Hello world!") + ", 6, 11)");
    Assert.assertEquals(BoxedType.create("world"), fn.evaluate(functions));
  }
}
