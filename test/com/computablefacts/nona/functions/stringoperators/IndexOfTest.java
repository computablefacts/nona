package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class IndexOfTest {

  @Test
  public void testIndexOfWithOneMatch() {

    Function fn = new Function("INDEX_OF(john doe, john)");
    Assert.assertEquals(BoxedType.of(0), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testIndexOfWithNoMatch() {

    Function fn = new Function("INDEX_OF(john, john doe)");
    Assert.assertEquals(BoxedType.of(-1), fn.evaluate(Function.definitions()));
  }
}
