package com.computablefacts.nona.functions.additiveoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class AddTest {

  @Test
  public void testSimpleAddFunction() {

    Function fn = new Function("ADD(1, 1)");
    Assert.assertEquals(BoxedType.of(2.0), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testComplexAddFunction() {

    Function fn = new Function("SUB(ADD(1, 1), 1)");
    Assert.assertEquals(BoxedType.of(1.0), fn.evaluate(Function.definitions()));
  }
}
