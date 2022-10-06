package com.computablefacts.nona.functions.stringoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.asterix.BoxedType;

public class ToUpperCaseTest {

  @Test
  public void testToUpperCase() {

    Function fn = new Function("TO_UPPERCASE(cyrille)");
    Assert.assertEquals(BoxedType.of("CYRILLE"), fn.evaluate(Function.definitions()));
  }
}
