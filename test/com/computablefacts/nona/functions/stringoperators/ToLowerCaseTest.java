package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class ToLowerCaseTest {

  @Test
  public void testToLowerCase() {

    Function fn = new Function("TO_LOWERCASE(CYRILLE)");
    Assert.assertEquals(BoxedType.of("cyrille"), fn.evaluate(Function.definitions()));
  }
}
