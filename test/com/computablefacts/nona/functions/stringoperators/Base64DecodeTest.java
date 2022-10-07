package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class Base64DecodeTest {

  @Test
  public void testEncodeSpace() {

    Function fn = new Function("BASE64_DECODE(\"IA==\")");
    Assert.assertEquals(BoxedType.of(" "), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEncodeEmptyString() {

    Function fn = new Function("BASE64_DECODE(\"\")");
    Assert.assertEquals(BoxedType.of(""), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testEncodeString() {

    Function fn = new Function("BASE64_DECODE(\"cmFuZG9tIHN0cmluZw==\")");
    Assert.assertEquals(BoxedType.of("random string"), fn.evaluate(Function.definitions()));
  }
}
