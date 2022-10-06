package com.computablefacts.nona.functions.stringoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.asterix.BoxedType;

public class StrLengthTest {

  @Test
  public void testStrLength() {

    Function fn = new Function("STR_LENGTH(cyrille)");
    Assert.assertEquals(BoxedType.of(7), fn.evaluate(Function.definitions()));
  }
}
