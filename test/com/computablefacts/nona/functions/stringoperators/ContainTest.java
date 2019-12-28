package com.computablefacts.nona.functions.stringoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class ContainTest {

  @Test
  public void testContain() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("CONTAIN", new Contain());

    Function fn = new Function("CONTAIN(john doe, john)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));

    Function fn2 = new Function("CONTAIN(john, john doe)");
    Assert.assertEquals(BoxedType.create(false), fn2.evaluate(functions));
  }
}
