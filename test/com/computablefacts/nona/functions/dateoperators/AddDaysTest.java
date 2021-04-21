package com.computablefacts.nona.functions.dateoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class AddDaysTest {

  @Test
  public void testSubstractDays() {

    Function fn = new Function(
        "EQUAL(TO_DATE(2021-20-04 19:02, yyyy-dd-MM HH:mm), ADD_DAYS(TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm), -1))");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testAddDays() {

    Function fn = new Function(
        "EQUAL(TO_DATE(2021-22-04 19:02, yyyy-dd-MM HH:mm), ADD_DAYS(TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm), 1))");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }
}
