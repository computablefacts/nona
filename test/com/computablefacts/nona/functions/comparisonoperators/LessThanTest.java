package com.computablefacts.nona.functions.comparisonoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class LessThanTest {

  @Test
  public void testLt() {

    Function fn1 = new Function("LT(cyrille, patrick)");
    Assert.assertEquals(BoxedType.of(true), fn1.evaluate(Function.definitions()));

    Function fn2 = new Function("LT(patrick, cyrille)");
    Assert.assertEquals(BoxedType.of(false), fn2.evaluate(Function.definitions()));

    Function fn3 = new Function("LT(cyrille, cyrille)");
    Assert.assertEquals(BoxedType.of(false), fn3.evaluate(Function.definitions()));
  }

  @Test
  public void testLtNumeric() {

    Function fn1 = new Function("LT(1, 10)");
    Assert.assertEquals(BoxedType.of(true), fn1.evaluate(Function.definitions()));

    Function fn2 = new Function("LT(10, 1)");
    Assert.assertEquals(BoxedType.of(false), fn2.evaluate(Function.definitions()));

    Function fn3 = new Function("LT(1, 1)");
    Assert.assertEquals(BoxedType.of(false), fn3.evaluate(Function.definitions()));
  }

  @Test
  public void testLtDate() {

    Function fn = new Function(
        "LT(TO_DATE(2021-20-04 19:02, yyyy-dd-MM HH:mm), TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm))");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));

    Function fn2 = new Function(
        "LT(TO_DATE(2021-22-04 19:02, yyyy-dd-MM HH:mm), TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm))");
    Assert.assertEquals(BoxedType.of(false), fn2.evaluate(Function.definitions()));

    Function fn3 = new Function(
        "LT(TO_DATE(2021-21-04 19:02, yyyy-dd-MM HH:mm), TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm))");
    Assert.assertEquals(BoxedType.of(false), fn3.evaluate(Function.definitions()));
  }
}
