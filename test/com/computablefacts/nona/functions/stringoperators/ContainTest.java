package com.computablefacts.nona.functions.stringoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class ContainTest {

  @Test
  public void testContainWithOneMatch() {

    Function fn = new Function("CONTAIN(john doe, john)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testContainWithNoMatch() {

    Function fn = new Function("CONTAIN(john, john doe)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }
}
