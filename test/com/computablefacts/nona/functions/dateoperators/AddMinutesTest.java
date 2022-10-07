package com.computablefacts.nona.functions.dateoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class AddMinutesTest {

  @Test
  public void testSubstractMinutes() {

    Function fn = new Function(
        "EQUAL(TO_DATE(2021-21-04 19:01, yyyy-dd-MM HH:mm), ADD_MINUTES(TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm), -1))");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testAddMinutes() {

    Function fn = new Function(
        "EQUAL(TO_DATE(2021-21-04 19:03, yyyy-dd-MM HH:mm), ADD_MINUTES(TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm), 1))");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }
}
