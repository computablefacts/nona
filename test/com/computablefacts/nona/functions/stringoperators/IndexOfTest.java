package com.computablefacts.nona.functions.stringoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class IndexOfTest {

  @Test
  public void testIndexOfWithOneMatch() {

    Function fn = new Function("INDEX_OF(john doe, john)");
    Assert.assertEquals(BoxedType.create(0), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIndexOfWithNoMatch() {

    Function fn = new Function("INDEX_OF(john, john doe)");
    Assert.assertEquals(BoxedType.create(-1), fn.evaluate(Function.definitions()));
  }
}
