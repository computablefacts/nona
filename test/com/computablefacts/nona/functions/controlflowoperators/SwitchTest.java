package com.computablefacts.nona.functions.controlflowoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class SwitchTest {

  @Test
  public void testSwitch() {

    Function fn = new Function(
        "SUBSTRING(SWITCH(MOD(88, 3), 0, \"The remainder is 0.\", 1, \"The remainder is 1.\", 2, \"The remainder is 2.\"), 1, 20)");
    Assert.assertEquals(BoxedType.create("The remainder is 1."),
        fn.evaluate(Function.definitions()));
  }

  @Test
  public void testSwitchWithDefaultStatement() {

    Function fn = new Function(
        "SUBSTRING(SWITCH(MOD(88, 3), 0, \"The remainder is 0.\", _, \"The remainder is 1.\", 2, \"The remainder is 2.\"), 1, 20)");
    Assert.assertEquals(BoxedType.create("The remainder is 1."),
        fn.evaluate(Function.definitions()));
  }

  @Test(expected = IllegalStateException.class)
  public void testSwitchWithMissingStatement() {

    Function fn = new Function(
        "SUBSTRING(SWITCH(MOD(88, 3), 0, \"The remainder is 0.\", 2, \"The remainder is 2.\"), 1, 20)");
    Assert.assertEquals(BoxedType.create(null), fn.evaluate(Function.definitions()));
  }
}
