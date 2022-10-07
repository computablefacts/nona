package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class StartWithTest {

  @Test
  public void testStartWith() {

    Function fn = new Function("START_WITH(cve¤references¤reference_data¤A1A¤url, cve¤references¤reference_data¤A1A)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testStartWithCaseSensitivity() {

    Function fn = new Function("START_WITH(cve¤references¤reference_data¤a1a¤url, CVE¤REFERENCES¤REFERENCE_DATA¤A1A)");
    Assert.assertEquals(BoxedType.of(false), fn.evaluate(Function.definitions()));
  }
}
