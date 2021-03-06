package com.computablefacts.nona.functions.stringoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class SubstringTest {

  @Test
  public void testExtractWholeString() {

    Function fn = new Function("SUBSTRING(" + Function.wrap("Hello world!") + ", 0)");
    Assert.assertEquals(BoxedType.create("Hello world!"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testExtractSubstring() {

    Function fn = new Function("SUBSTRING(" + Function.wrap("Hello world!") + ", 6, 11)");
    Assert.assertEquals(BoxedType.create("world"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testExtractSubstringFromBigInteger() {

    Function fn = new Function("SUBSTRING(53064045700012, 0, 9)");
    Assert.assertEquals(BoxedType.create("530640457"), fn.evaluate(Function.definitions()));
  }
}
