package com.computablefacts.nona.functions.stringoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class ToLowerCaseTest {

  @Test
  public void testToLowerCase() {

    Function fn = new Function("TO_LOWERCASE(CYRILLE)");
    Assert.assertEquals(BoxedType.create("cyrille"), fn.evaluate(Function.definitions()));
  }
}
