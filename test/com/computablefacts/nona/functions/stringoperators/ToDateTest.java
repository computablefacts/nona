package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import java.time.Instant;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

public class ToDateTest {

  @Test
  public void testToDate() throws Exception {

    Function fn = new Function("TO_DATE(2021/04/21 19:02 UTC+00:00, yyyy/MM/dd HH:mm z)");
    Assert.assertEquals(BoxedType.of(Date.from(Instant.parse("2021-04-21T19:02:00Z"))),
        fn.evaluate(Function.definitions()));
  }

  @Test
  public void testToInvalidDate() throws Exception {

    Function fn = new Function("TO_DATE(2021/04/21 19:02 UTC+00:00, yyyy-MM-ddTHH:mm:ssZ)");
    Assert.assertEquals(BoxedType.empty(), fn.evaluate(Function.definitions()));
  }
}
