package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class SubstringTest {

  @Test
  public void testExtractWholeString() {

    Function fn = new Function("SUBSTRING(" + Function.wrap("Hello world!") + ", 0)");
    Assert.assertEquals(BoxedType.of("Hello world!"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testExtractSubstring() {

    Function fn = new Function("SUBSTRING(" + Function.wrap("Hello world!") + ", 6, 11)");
    Assert.assertEquals(BoxedType.of("world"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testExtractSubstringFromBigInteger() {

    Function fn = new Function("SUBSTRING(53064045700012, 0, 9)");
    Assert.assertEquals(BoxedType.of("530640457"), fn.evaluate(Function.definitions()));
  }
}
