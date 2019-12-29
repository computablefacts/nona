package com.computablefacts.nona.functions.stringoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class ContainTest {

  @Test
  public void testContainWithOneMatch() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("CONTAIN", new Contain());

    Function fn = new Function("CONTAIN(john doe, john)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testContainWithNoMatch() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("CONTAIN", new Contain());

    Function fn = new Function("CONTAIN(john, john doe)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }
}
