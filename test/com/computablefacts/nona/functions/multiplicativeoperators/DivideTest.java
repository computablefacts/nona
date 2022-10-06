package com.computablefacts.nona.functions.multiplicativeoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.asterix.BoxedType;

public class DivideTest {

  @Test
  public void testDiv() {

    Function fn = new Function("DIV(2, 2)");
    Assert.assertEquals(BoxedType.of(1.0), fn.evaluate(Function.definitions()));
  }
}
