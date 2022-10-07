package com.computablefacts.nona.functions.comparisonoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class EqualTest {

  @Test
  public void testEqualTrueFalse() {

    Function fn = new Function("EQUAL(true, false)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEqualFalseTrue() {

    Function fn = new Function("EQUAL(false, true)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEqualTrueTrue() {

    Function fn = new Function("EQUAL(true, true)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEqualFalseFalse() {

    Function fn = new Function("EQUAL(false, false)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEqualNumeric() {

    Function fn = new Function("EQUAL(1, 1)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));

    Function fn2 = new Function("EQUAL(1, 2)");
    Assert.assertEquals(BoxedType.of(false), fn2.evaluate(Function.definitions()));
  }

  @Test
  public void testEqualDate() {

    Function fn = new Function(
        "EQUAL(TO_DATE(2021-21-04 19:02, yyyy-dd-MM HH:mm), TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm))");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));

    Function fn2 = new Function(
        "EQUAL(TO_DATE(2021-22-04 19:02, yyyy-dd-MM HH:mm), TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm))");
    Assert.assertEquals(BoxedType.of(false), fn2.evaluate(Function.definitions()));
  }
}
