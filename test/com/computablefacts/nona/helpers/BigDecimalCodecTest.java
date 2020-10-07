package com.computablefacts.nona.helpers;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class BigDecimalCodecTest {

  @Test
  public void testDecodeAsStringNull() {
    Assert.assertNull(BigDecimalCodec.decode(null));
  }

  @Test
  public void testDecodeAsStringEmptyString() {
    Assert.assertEquals("", BigDecimalCodec.decode(""));
  }

  @Test
  public void testDecodeAsString() {
    Assert.assertEquals("1234567890", BigDecimalCodec.decode("???2101234567890*"));
    Assert.assertEquals("123.4567890", BigDecimalCodec.decode("??3123.4567890*"));
    Assert.assertEquals("-1234567890", BigDecimalCodec.decode("***7898765432109?"));
    Assert.assertEquals("-123.4567890", BigDecimalCodec.decode("**6876:5432109?"));
  }

  @Test
  public void testDecodeAsBigDecimalNull() {
    Assert.assertNull(BigDecimalCodec.decodeAsBigDecimal(null));
  }

  @Test(expected = RuntimeException.class)
  public void testDecodeAsBigDecimalEmptyString() {
    BigDecimal bd = BigDecimalCodec.decodeAsBigDecimal("");
  }

  @Test
  public void testDecodeAsBigDecimal() {
    Assert.assertEquals(BigDecimal.valueOf(1234567890),
        BigDecimalCodec.decodeAsBigDecimal("???2101234567890*"));
    Assert.assertEquals(BigDecimal.valueOf(123.4567890),
        BigDecimalCodec.decodeAsBigDecimal("??3123.456789*"));
    Assert.assertEquals(BigDecimal.valueOf(-1234567890),
        BigDecimalCodec.decodeAsBigDecimal("***7898765432109?"));
    Assert.assertEquals(BigDecimal.valueOf(-123.4567890),
        BigDecimalCodec.decodeAsBigDecimal("**6876:543210?"));
  }

  @Test
  public void testEncodeStringNull() {
    Assert.assertNull(BigDecimalCodec.encode((String) null));
  }

  @Test
  public void testEncodeStringEmptyString() {
    Assert.assertEquals("", BigDecimalCodec.encode(""));
  }

  @Test
  public void testEncodeString() {
    Assert.assertEquals("???2101234567890*", BigDecimalCodec.encode("1234567890"));
    Assert.assertEquals("??3123.4567890*", BigDecimalCodec.encode("123.4567890"));
    Assert.assertEquals("***7898765432109?", BigDecimalCodec.encode("-1234567890"));
    Assert.assertEquals("**6876:5432109?", BigDecimalCodec.encode("-123.4567890"));
  }

  @Test
  public void testEncodeBigDecimalNull() {
    Assert.assertNull(BigDecimalCodec.encode((BigDecimal) null));
  }

  @Test
  public void testEncodeBigDecimal() {
    Assert.assertEquals("???2101234567890*",
        BigDecimalCodec.encode(BigDecimal.valueOf(1234567890)));
    Assert.assertEquals("??3123.456789*", BigDecimalCodec.encode(BigDecimal.valueOf(123.4567890)));
    Assert.assertEquals("***7898765432109?",
        BigDecimalCodec.encode(BigDecimal.valueOf(-1234567890)));
    Assert.assertEquals("**6876:543210?", BigDecimalCodec.encode(BigDecimal.valueOf(-123.4567890)));
  }
}
