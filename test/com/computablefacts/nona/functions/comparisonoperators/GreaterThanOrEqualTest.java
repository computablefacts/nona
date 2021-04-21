package com.computablefacts.nona.functions.comparisonoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class GreaterThanOrEqualTest {

  @Test
  public void testGte() {

    Function fn = new Function("GTE(cyrille, patrick)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));

    Function fn2 = new Function("GTE(patrick, cyrille)");
    Assert.assertEquals(BoxedType.create(true), fn2.evaluate(Function.definitions()));

    Function fn3 = new Function("GTE(cyrille, cyrille)");
    Assert.assertEquals(BoxedType.create(true), fn3.evaluate(Function.definitions()));
  }

  @Test
  public void testGteNumeric() {

    Function fn = new Function("GTE(1, 10)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));

    Function fn2 = new Function("GTE(10, 1)");
    Assert.assertEquals(BoxedType.create(true), fn2.evaluate(Function.definitions()));

    Function fn3 = new Function("GTE(1, 1)");
    Assert.assertEquals(BoxedType.create(true), fn3.evaluate(Function.definitions()));
  }

  @Test
  public void testGteDate() {

    Function fn = new Function(
        "GTE(TO_DATE(2021-20-04 19:02, yyyy-dd-MM HH:mm), TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm))");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));

    Function fn2 = new Function(
        "GTE(TO_DATE(2021-22-04 19:02, yyyy-dd-MM HH:mm), TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm))");
    Assert.assertEquals(BoxedType.create(true), fn2.evaluate(Function.definitions()));

    Function fn3 = new Function(
        "GTE(TO_DATE(2021-21-04 19:02, yyyy-dd-MM HH:mm), TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm))");
    Assert.assertEquals(BoxedType.create(true), fn3.evaluate(Function.definitions()));
  }
}
