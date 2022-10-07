package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class ConcatTest {

  @Test
  public void testConcatSpace() {

    Function fn = new Function("CONCAT(arg1, \" \", arg2, \" \", arg3)");
    Assert.assertEquals(BoxedType.of("arg1 arg2 arg3"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testConcatComma() {

    Function fn = new Function("CONCAT(arg1, \", \", arg2, \", \", arg3)");
    Assert.assertEquals(BoxedType.of("arg1, arg2, arg3"), fn.evaluate(Function.definitions()));
  }
}
