package com.computablefacts.nona.functions.stringoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class TrimTest {

  @Test
  public void testTrimLeft() {

    Function fn = new Function("TRIM(   cyrille)");
    Assert.assertEquals(BoxedType.create("cyrille"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testTrimRight() {

    Function fn = new Function("TRIM(cyrille   )");
    Assert.assertEquals(BoxedType.create("cyrille"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testTrim() {

    Function fn = new Function("TRIM(   cyrille   )");
    Assert.assertEquals(BoxedType.create("cyrille"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testTrimLeftQuoted() {

    Function fn = new Function("TRIM(\"   cyrille\")");
    Assert.assertEquals(BoxedType.create("cyrille"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testTrimRightQuoted() {

    Function fn = new Function("TRIM(\"cyrille   \")");
    Assert.assertEquals(BoxedType.create("cyrille"), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testTrimQuoted() {

    Function fn = new Function("TRIM(\"   cyrille   \")");
    Assert.assertEquals(BoxedType.create("cyrille"), fn.evaluate(Function.definitions()));
  }
}
