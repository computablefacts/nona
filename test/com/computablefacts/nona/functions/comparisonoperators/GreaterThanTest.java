package com.computablefacts.nona.functions.comparisonoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class GreaterThanTest {

  @Test
  public void testGt() {

    Function fn = new Function("GT(cyrille, patrick)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));

    Function fn2 = new Function("GT(patrick, cyrille)");
    Assert.assertEquals(BoxedType.of(true), fn2.evaluate(Function.definitions()));

    Function fn3 = new Function("GT(cyrille, cyrille)");
    Assert.assertEquals(BoxedType.of(false), fn3.evaluate(Function.definitions()));
  }

  @Test
  public void testGtNumeric() {

    Function fn = new Function("GT(1, 10)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));

    Function fn2 = new Function("GT(10, 1)");
    Assert.assertEquals(BoxedType.of(true), fn2.evaluate(Function.definitions()));

    Function fn3 = new Function("GT(1, 1)");
    Assert.assertEquals(BoxedType.of(false), fn3.evaluate(Function.definitions()));
  }

  @Test
  public void testGtDate() {

    Function fn = new Function(
        "GT(TO_DATE(2021-20-04 19:02, yyyy-dd-MM HH:mm), TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm))");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));

    Function fn2 = new Function(
        "GT(TO_DATE(2021-22-04 19:02, yyyy-dd-MM HH:mm), TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm))");
    Assert.assertEquals(BoxedType.of(true), fn2.evaluate(Function.definitions()));

    Function fn3 = new Function(
        "GT(TO_DATE(2021-21-04 19:02, yyyy-dd-MM HH:mm), TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm))");
    Assert.assertEquals(BoxedType.of(false), fn3.evaluate(Function.definitions()));
  }
}
