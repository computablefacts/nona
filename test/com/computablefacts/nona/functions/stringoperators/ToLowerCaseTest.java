package com.computablefacts.nona.functions.stringoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class ToLowerCaseTest {

  @Test
  public void testToLowerCase() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("TLC", new ToLowerCase());

    Function fn = new Function("TLC(CYRILLE)");
    Assert.assertEquals(BoxedType.create("cyrille"), fn.evaluate(functions));
  }
}
