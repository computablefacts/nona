package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class TrimTest {

  @Test
  public void testTrimLeft() {

    Function fn = new Function("TRIM(   cyrille)");
    Assert.assertEquals(BoxedType.of("cyrille"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testTrimRight() {

    Function fn = new Function("TRIM(cyrille   )");
    Assert.assertEquals(BoxedType.of("cyrille"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testTrim() {

    Function fn = new Function("TRIM(   cyrille   )");
    Assert.assertEquals(BoxedType.of("cyrille"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testTrimLeftQuoted() {

    Function fn = new Function("TRIM(\"   cyrille\")");
    Assert.assertEquals(BoxedType.of("cyrille"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testTrimRightQuoted() {

    Function fn = new Function("TRIM(\"cyrille   \")");
    Assert.assertEquals(BoxedType.of("cyrille"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testTrimQuoted() {

    Function fn = new Function("TRIM(\"   cyrille   \")");
    Assert.assertEquals(BoxedType.of("cyrille"), fn.evaluate(Function.definitions()));
  }
}
