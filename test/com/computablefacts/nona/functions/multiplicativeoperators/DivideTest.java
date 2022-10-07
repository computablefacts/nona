package com.computablefacts.nona.functions.multiplicativeoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class DivideTest {

  @Test
  public void testDiv() {

    Function fn = new Function("DIV(2, 2)");
    Assert.assertEquals(BoxedType.of(1.0), fn.evaluate(Function.definitions()));
  }
}
