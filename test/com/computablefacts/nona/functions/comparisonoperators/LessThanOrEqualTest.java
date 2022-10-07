package com.computablefacts.nona.functions.comparisonoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class LessThanOrEqualTest {

  @Test
  public void testLte() {

    Function fn1 = new Function("LTE(cyrille, patrick)");
    Assert.assertEquals(BoxedType.of(true), fn1.evaluate(Function.definitions()));

    Function fn2 = new Function("LTE(patrick, cyrille)");
    Assert.assertEquals(BoxedType.of(false), fn2.evaluate(Function.definitions()));

    Function fn3 = new Function("LTE(cyrille, cyrille)");
    Assert.assertEquals(BoxedType.of(true), fn3.evaluate(Function.definitions()));
  }

  @Test
  public void testLteNumeric() {

    Function fn1 = new Function("LTE(1, 10)");
    Assert.assertEquals(BoxedType.of(true), fn1.evaluate(Function.definitions()));

    Function fn2 = new Function("LTE(10, 1)");
    Assert.assertEquals(BoxedType.of(false), fn2.evaluate(Function.definitions()));

    Function fn3 = new Function("LTE(1, 1)");
    Assert.assertEquals(BoxedType.of(true), fn3.evaluate(Function.definitions()));
  }

  @Test
  public void testLteDate() {

    Function fn = new Function(
        "LTE(TO_DATE(2021-20-04 19:02, yyyy-dd-MM HH:mm), TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm))");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));

    Function fn2 = new Function(
        "LTE(TO_DATE(2021-22-04 19:02, yyyy-dd-MM HH:mm), TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm))");
    Assert.assertEquals(BoxedType.of(false), fn2.evaluate(Function.definitions()));

    Function fn3 = new Function(
        "LTE(TO_DATE(2021-21-04 19:02, yyyy-dd-MM HH:mm), TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm))");
    Assert.assertEquals(BoxedType.of(true), fn3.evaluate(Function.definitions()));
  }
}
