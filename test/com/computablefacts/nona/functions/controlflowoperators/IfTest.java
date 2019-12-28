package com.computablefacts.nona.functions.controlflowoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.functions.comparisonoperators.GreaterThanOrEqual;
import com.computablefacts.nona.types.BoxedType;

public class IfTest {

  @Test
  public void testIf() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IF", new If());
    functions.put("GTE", new GreaterThanOrEqual());

    Function fn = new Function("IF(GTE(cyrille, patrick), cyrille, patrick)");
    Assert.assertEquals(BoxedType.create("patrick"), fn.evaluate(functions));
  }
}
