package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class StrLengthTest {

  @Test
  public void testStrLength() {

    Function fn = new Function("STR_LENGTH(cyrille)");
    Assert.assertEquals(BoxedType.of(7), fn.evaluate(Function.definitions()));
  }
}
