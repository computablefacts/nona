package com.computablefacts.nona.functions.multiplicativeoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class ModuloTest {

  @Test
  public void test88Modulo3() {

    Function fn = new Function("MOD(88, 3)");
    Assert.assertEquals(BoxedType.of(1), fn.evaluate(Function.definitions()));
  }

  @Test
  public void test88Modulo5() {

    Function fn = new Function("MOD(88, 5)");
    Assert.assertEquals(BoxedType.of(3.0), fn.evaluate(Function.definitions()));
  }
}
