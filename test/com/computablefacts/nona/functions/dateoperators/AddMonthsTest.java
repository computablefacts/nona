package com.computablefacts.nona.functions.dateoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class AddMonthsTest {

  @Test
  public void testSubstractMonths() {

    Function fn = new Function(
        "EQUAL(TO_DATE(2021-21-03 19:02, yyyy-dd-MM HH:mm), ADD_MONTHS(TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm), -1))");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testAddMonths() {

    Function fn = new Function(
        "EQUAL(TO_DATE(2021-21-05 19:02, yyyy-dd-MM HH:mm), ADD_MONTHS(TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm), 1))");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }
}
