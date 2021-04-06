package com.computablefacts.nona.helpers;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;

public class DefaultLexicoderTest {

  @Test
  public void testLexicodeEdgeCases() {
    assertEquals("", Codecs.defaultLexicoder.apply(null).text());
    assertEquals("", Codecs.defaultLexicoder.apply("").text());
  }

  @Test
  public void testLexicodeString() {
    assertEquals("abc", Codecs.defaultLexicoder.apply("abc").text());
    assertEquals("   ", Codecs.defaultLexicoder.apply("   ").text());
    assertEquals("abc 123", Codecs.defaultLexicoder.apply("abc 123").text());
    assertEquals("123 abc", Codecs.defaultLexicoder.apply("123 abc").text());
  }

  @Test
  public void testLexicodeInteger() {
    assertEquals("?0*", Codecs.defaultLexicoder.apply(0).text());
    assertEquals("??220*", Codecs.defaultLexicoder.apply(20).text());
    assertEquals("**779?", Codecs.defaultLexicoder.apply(-20).text());
    assertEquals("?0*", Codecs.defaultLexicoder.apply(1 / 3).text());
    assertEquals("?3*", Codecs.defaultLexicoder.apply(6 / 2).text());
    assertEquals("??3522*", Codecs.defaultLexicoder.apply(0x20A).text());
  }

  @Test
  public void testLexicodeLong() {
    assertEquals("??9123456789*", Codecs.defaultLexicoder.apply(123456789L).text());
    assertEquals("**0876543210?", Codecs.defaultLexicoder.apply(-123456789L).text());
  }

  @Test
  public void testLexicodeDouble() {
    assertEquals("?1.5*", Codecs.defaultLexicoder.apply(1.5d).text());
    assertEquals("?0.0*", Codecs.defaultLexicoder.apply(0.0d).text());
    assertEquals("*8:4?", Codecs.defaultLexicoder.apply(-1.5d).text());
    assertEquals("?1.5555555555555556*",
        Codecs.defaultLexicoder.apply(1.555555555555555555555555555555555555d).text());
    assertEquals("?0.3333333333333333*", Codecs.defaultLexicoder.apply(1.0d / 3d).text());
    assertEquals("?3.0*", Codecs.defaultLexicoder.apply(6d / 2.0d).text());
    assertEquals("inf", Codecs.defaultLexicoder.apply(1.5d / 0.0d).text());
    assertEquals("inf", Codecs.defaultLexicoder.apply(-1.5d / 0.0d).text());
    assertEquals("NaN", Codecs.defaultLexicoder.apply(0.0d / 0.0d).text());
  }

  @Test
  public void testLexicodeFloat() {
    assertEquals("?1.5*", Codecs.defaultLexicoder.apply(1.5f).text());
    assertEquals("?0.0*", Codecs.defaultLexicoder.apply(0.0f).text());
    assertEquals("*8:4?", Codecs.defaultLexicoder.apply(-1.5f).text());
    assertEquals("?1.5555555820465088*",
        Codecs.defaultLexicoder.apply(1.555555555555555555555555555555555555f).text());
    assertEquals("?3.0*", Codecs.defaultLexicoder.apply(6f / 2.0f).text());
    assertEquals("inf", Codecs.defaultLexicoder.apply(1.5f / 0.0f).text());
    assertEquals("inf", Codecs.defaultLexicoder.apply(-1.5f / 0.0f).text());
    assertEquals("NaN", Codecs.defaultLexicoder.apply(0.0f / 0.0f).text());
  }

  @Test
  public void testLexicodeDate() {
    Date date = new Date();
    assertEquals(DateTimeFormatter.ISO_INSTANT.format(date.toInstant()),
        Codecs.defaultLexicoder.apply(date).text());
  }

  @Test
  public void testLexicodeList() {
    List<Integer> listInt = Ints.asList(1, 2, 3, 4, 5);
    List<Integer> listEmpty = Ints.asList();
    assertEquals("", Codecs.defaultLexicoder.apply(listInt).text());
    assertEquals("", Codecs.defaultLexicoder.apply(listEmpty).text());
  }

  @Test
  public void testLexicodeMap() {
    Map<String, Integer> map = ImmutableMap.of("a", 1, "b", 2, "c", 3);
    Map<String, Integer> mapEmpty = ImmutableMap.of();
    assertEquals("", Codecs.defaultLexicoder.apply(map).text());
    assertEquals("", Codecs.defaultLexicoder.apply(mapEmpty).text());
  }

  @Test
  public void testLexicodeArray() {
    int[] array = {1, 2, 3};
    int[] arrayEmpty = {};
    assertEquals("", Codecs.defaultLexicoder.apply(array).text());
    assertEquals("", Codecs.defaultLexicoder.apply(arrayEmpty).text());
  }

  @Test
  public void testLexicodeCharacter() {
    // TODO : tous les caractères sont mappés sur la chaîne de caractères vide. Est-ce correct?
    assertEquals("", Codecs.defaultLexicoder.apply('a').text());
    assertEquals("", Codecs.defaultLexicoder.apply('\0').text());
  }

  @Test
  public void testLexicodeBigInteger() {
    assertEquals("??210*", Codecs.defaultLexicoder.apply(BigInteger.valueOf(10)).text());
    assertEquals("??214*", Codecs.defaultLexicoder.apply(BigInteger.valueOf(14)).text());
  }

  @Test
  public void testLexicodeBigDecimal() {
    assertEquals("??210*", Codecs.defaultLexicoder.apply(BigDecimal.valueOf(10)).text());
    assertEquals("??214.7*", Codecs.defaultLexicoder.apply(BigDecimal.valueOf(14.7)).text());
  }

  @Test
  public void testLexicodeBoolean() {
    assertEquals("true", Codecs.defaultLexicoder.apply(true).text());
    assertEquals("false", Codecs.defaultLexicoder.apply(false).text());
  }
}
