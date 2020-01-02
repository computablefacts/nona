package com.computablefacts.nona.functions.stringoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class StartWithTest {

  @Test
  public void testStartWith() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("START_WITH", new StartWith());

    Function fn = new Function(
        "START_WITH(cve¤references¤reference_data¤A1A¤url, cve¤references¤reference_data¤A1A)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testStartWithCaseSensitivity() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("START_WITH", new StartWith());

    Function fn = new Function(
        "START_WITH(cve¤references¤reference_data¤a1a¤url, CVE¤REFERENCES¤REFERENCE_DATA¤A1A)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }
}
