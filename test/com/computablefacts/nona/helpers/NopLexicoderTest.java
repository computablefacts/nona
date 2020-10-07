package com.computablefacts.nona.helpers;

import static org.junit.Assert.assertEquals;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;

public class NopLexicoderTest {

  @Test
  public void testLexicodeEdgeCases() {
    assertEquals("", Codecs.nopLexicoder.apply(null).text());
    assertEquals("", Codecs.nopLexicoder.apply("").text());
  }

  @Test
  public void testLexicodeString() {
    assertEquals("abc", Codecs.nopLexicoder.apply("abc").text());
    assertEquals("   ", Codecs.nopLexicoder.apply("   ").text());
    assertEquals("abc 123", Codecs.nopLexicoder.apply("abc 123").text());
    assertEquals("123 abc", Codecs.nopLexicoder.apply("123 abc").text());
  }

  @Test
  public void testLexicodeInteger() {
    assertEquals("0", Codecs.nopLexicoder.apply(0).text());
    assertEquals("20", Codecs.nopLexicoder.apply(20).text());
    assertEquals("-20", Codecs.nopLexicoder.apply(-20).text());
    assertEquals("0", Codecs.nopLexicoder.apply(1 / 3).text());
    assertEquals("3", Codecs.nopLexicoder.apply(6 / 2).text());
    assertEquals("522", Codecs.nopLexicoder.apply(0x20A).text());
  }

  @Test
  public void testLexicodeLong() {
    assertEquals("123456789", Codecs.nopLexicoder.apply(123456789L).text());
    assertEquals("-123456789", Codecs.nopLexicoder.apply(-123456789L).text());
  }

  @Test
  public void testLexicodeDouble() {
    assertEquals("1.5", Codecs.nopLexicoder.apply(1.5d).text());
    assertEquals("0.0", Codecs.nopLexicoder.apply(0.0d).text());
    assertEquals("-1.5", Codecs.nopLexicoder.apply(-1.5d).text());
    assertEquals("1.5555555555555556",
        Codecs.nopLexicoder.apply(1.555555555555555555555555555555555555d).text());
    assertEquals("0.3333333333333333", Codecs.nopLexicoder.apply(1.0d / 3d).text());
    assertEquals("3.0", Codecs.nopLexicoder.apply(6d / 2.0d).text());
    assertEquals("Infinity", Codecs.nopLexicoder.apply(1.5d / 0.0d).text());
    assertEquals("-Infinity", Codecs.nopLexicoder.apply(-1.5d / 0.0d).text());
    assertEquals("NaN", Codecs.nopLexicoder.apply(0.0d / 0.0d).text());
  }

  @Test
  public void testLexicodeFloat() {
    assertEquals("1.5", Codecs.nopLexicoder.apply(1.5f).text());
    assertEquals("0.0", Codecs.nopLexicoder.apply(0.0f).text());
    assertEquals("-1.5", Codecs.nopLexicoder.apply(-1.5f).text());
    assertEquals("1.5555556",
        Codecs.nopLexicoder.apply(1.555555555555555555555555555555555555f).text());
    assertEquals("3.0", Codecs.nopLexicoder.apply(6f / 2.0f).text());
    assertEquals("Infinity", Codecs.nopLexicoder.apply(1.5f / 0.0f).text());
    assertEquals("-Infinity", Codecs.nopLexicoder.apply(-1.5f / 0.0f).text());
    assertEquals("NaN", Codecs.nopLexicoder.apply(0.0f / 0.0f).text());
  }

  @Test
  public void testLexicodeDate() {
    Date date = new Date();
    assertEquals(DateTimeFormatter.ISO_INSTANT.format(date.toInstant()),
        Codecs.nopLexicoder.apply(date).text());
  }

  @Test
  public void testLexicodeList() {
    List<Integer> listInt = Ints.asList(1, 2, 3, 4, 5);
    List<Integer> listEmpty = Ints.asList();
    assertEquals("[1, 2, 3, 4, 5]", Codecs.nopLexicoder.apply(listInt).text());
    assertEquals("[]", Codecs.nopLexicoder.apply(listEmpty).text());
  }

  @Test
  public void testLexicodeMap() {
    Map<String, Integer> map = ImmutableMap.of("a", 1, "b", 2, "c", 3);
    Map<String, Integer> mapEmpty = ImmutableMap.of();
    assertEquals("{a=1, b=2, c=3}", Codecs.nopLexicoder.apply(map).text());
    assertEquals("{}", Codecs.nopLexicoder.apply(mapEmpty).text());
  }

  @Test
  public void testLexicodeArray() {
    int[] array = {1, 2, 3};
    int[] arrayEmpty = {};
    assertEquals("", Codecs.nopLexicoder.apply(array).text());
    assertEquals("", Codecs.nopLexicoder.apply(arrayEmpty).text());
  }

  @Test
  public void testLexicodeCharacter() {
    // TODO : tous les caractères sont mappés sur la chaîne de caractères vide. Est-ce correct?
    assertEquals("a", Codecs.nopLexicoder.apply('a').text());
    assertEquals("\0", Codecs.nopLexicoder.apply('\0').text());
  }
}
