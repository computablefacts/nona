package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class ContainTest {

  @Test
  public void testContainWithOneMatch() {

    Function fn = new Function("CONTAIN(john doe, john)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testContainWithNoMatch() {

    Function fn = new Function("CONTAIN(john, john doe)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }
}
