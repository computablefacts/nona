package com.computablefacts.nona.functions.stringoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class IndexOfTest {

  @Test
  public void testIndexOfWithOneMatch() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("INDEX_OF", new IndexOf());

    Function fn = new Function("INDEX_OF(john doe, john)");
    Assert.assertEquals(BoxedType.create(0), fn.evaluate(functions));
  }

  @Test
  public void testIndexOfWithNoMatch() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("INDEX_OF", new IndexOf());

    Function fn = new Function("INDEX_OF(john, john doe)");
    Assert.assertEquals(BoxedType.create(-1), fn.evaluate(functions));
  }
}
