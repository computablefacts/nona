package com.computablefacts.nona.functions.stringoperators;

import java.time.Instant;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class ToDateTest {

  @Test
  public void testToDate() throws Exception {

    Function fn = new Function("TO_DATE(2021/04/21 19:02, yyyy/MM/dd HH:mm)");
    Assert.assertEquals(BoxedType.create(Date.from(Instant.parse("2021-04-21T17:02:00Z"))),
        fn.evaluate(Function.definitions()));
  }
}
