package com.computablefacts.nona.functions.stringoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class ConcatTest {

  @Test
  public void testConcatSpace() {

    Function fn = new Function("CONCAT(arg1, \" \", arg2, \" \", arg3)");
    Assert.assertEquals(BoxedType.create("arg1 arg2 arg3"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testConcatComma() {

    Function fn = new Function("CONCAT(arg1, \", \", arg2, \", \", arg3)");
    Assert.assertEquals(BoxedType.create("arg1, arg2, arg3"), fn.evaluate(Function.definitions()));
  }
}
