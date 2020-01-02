package com.computablefacts.nona.functions.stringoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class EndWithTest {

  @Test
  public void testEndWith() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("END_WITH", new EndWith());

    Function fn = new Function(
        "END_WITH(cve¤references¤reference_data¤A1A¤url, references¤reference_data¤A1A¤url)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testEndWithCaseSensitivity() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("END_WITH", new EndWith());

    Function fn = new Function(
        "END_WITH(cve¤references¤reference_data¤a1a¤url, REFERENCES¤REFERENCE_DATA¤A1A¤URL)");
    Assert.assertEquals(BoxedType.create(false), fn.evaluate(functions));
  }
}
