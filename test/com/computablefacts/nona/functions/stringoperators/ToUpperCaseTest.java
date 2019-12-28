package com.computablefacts.nona.functions.stringoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class ToUpperCaseTest {

  @Test
  public void testToUpperCase() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("TUC", new ToUpperCase());

    Function fn = new Function("TUC(cyrille)");
    Assert.assertEquals(BoxedType.create("CYRILLE"), fn.evaluate(functions));
  }
}
