package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class Base64EncodeTest {

  @Test
  public void testEncodeSpace() {

    Function fn = new Function("BASE64_ENCODE(\" \")");
    Assert.assertEquals(BoxedType.of("IA=="), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEncodeEmptyString() {

    Function fn = new Function("BASE64_ENCODE(\"\")");
    Assert.assertEquals(BoxedType.of(""), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEncodeString() {

    Function fn = new Function("BASE64_ENCODE(\"random string\")");
    Assert.assertEquals(BoxedType.of("cmFuZG9tIHN0cmluZw=="), fn.evaluate(Function.definitions()));
  }
}
