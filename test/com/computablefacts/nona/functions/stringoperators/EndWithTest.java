package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class EndWithTest {

  @Test
  public void testEndWith() {

    Function fn = new Function("END_WITH(cve¤references¤reference_data¤A1A¤url, references¤reference_data¤A1A¤url)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEndWithCaseSensitivity() {

    Function fn = new Function("END_WITH(cve¤references¤reference_data¤a1a¤url, REFERENCES¤REFERENCE_DATA¤A1A¤URL)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }
}
