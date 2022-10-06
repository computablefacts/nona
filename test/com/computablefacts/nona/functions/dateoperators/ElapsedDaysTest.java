package com.computablefacts.nona.functions.dateoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.asterix.BoxedType;

public class ElapsedDaysTest {

  @Test
  public void testFirstDateBeforeSecondDate() {

    Function fn = new Function(
        "ELAPSED_DAYS(TO_DATE(2020-21-04, yyyy-dd-MM), ADD_YEARS(TO_DATE(2020/04/21, yyyy/MM/dd), 1))");
    Assert.assertEquals(BoxedType.of(365), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testFirstDateAfterSecondDate() {

    Function fn = new Function(
        "ELAPSED_DAYS(TO_DATE(2020-21-04, yyyy-dd-MM), ADD_YEARS(TO_DATE(2020/04/21, yyyy/MM/dd), -1))");
    Assert.assertEquals(BoxedType.of(-366), fn.evaluate(Function.definitions()));
  }
}
