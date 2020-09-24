package com.computablefacts.nona.functions.multiplicativeoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class ModuloTest {

  @Test
  public void test88Modulo3() {

    Function fn = new Function("MOD(88, 3)");
    Assert.assertEquals(BoxedType.create(1), fn.evaluate(Function.definitions()));
  }

  @Test
  public void test88Modulo5() {

    Function fn = new Function("MOD(88, 5)");
    Assert.assertEquals(BoxedType.create(3.0), fn.evaluate(Function.definitions()));
  }
}
