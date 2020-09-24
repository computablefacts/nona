package com.computablefacts.nona.functions.stringoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class EndWithTest {

  @Test
  public void testEndWith() {

    Function fn = new Function(
        "END_WITH(cve¤references¤reference_data¤A1A¤url, references¤reference_data¤A1A¤url)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEndWithCaseSensitivity() {

    Function fn = new Function(
        "END_WITH(cve¤references¤reference_data¤a1a¤url, REFERENCES¤REFERENCE_DATA¤A1A¤URL)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(Function.definitions()));
  }
}
