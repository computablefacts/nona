package com.computablefacts.nona.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

public class StringsTest {

  @Test
  public void testEncode() {
    Assert.assertEquals("Test legacy encode : [\\u002c\\u000d\\u000a]",
        Strings.encode("Test legacy encode : [,\n\r]"));
  }

  @Test
  public void testDecode() {
    Assert.assertEquals("Test legacy encode : [,\n\r]",
        Strings.decode("Test legacy encode : [\\u002c\\u000d\\u000a]"));
  }

  @Test(expected = NullPointerException.class)
  public void testAnagramHashWithNullWord() {
    String anagram = Strings.anagramHash(null);
  }

  @Test
  public void testAnagramHash() {
    Assert.assertEquals("39100657332", Strings.anagramHash("act"));
    Assert.assertEquals("39100657332", Strings.anagramHash("atc"));
    Assert.assertEquals("39100657332", Strings.anagramHash("cat"));
    Assert.assertEquals("39100657332", Strings.anagramHash("cta"));
    Assert.assertEquals("39100657332", Strings.anagramHash("tac"));
    Assert.assertEquals("39100657332", Strings.anagramHash("tca"));
  }

  @Test(expected = NullPointerException.class)
  public void testLevenshteinDistanceWithNullWord1() {
    int distance = Strings.levenshteinDistance(null, "word2");
  }

  @Test(expected = NullPointerException.class)
  public void testLevenshteinDistanceWithNullWord2() {
    int distance = Strings.levenshteinDistance("word1", null);
  }

  @Test
  public void testLevenshteinDistance() {
    Assert.assertEquals(0, Strings.levenshteinDistance("", ""));
    Assert.assertEquals(0, Strings.levenshteinDistance("ABCDE", "ABCDE"));
    Assert.assertEquals(1, Strings.levenshteinDistance("ABCDE", "ABCD"));
    Assert.assertEquals(3, Strings.levenshteinDistance("kitten", "sitting"));
    Assert.assertEquals(5, Strings.levenshteinDistance("matrix", "xxxmatr"));
    Assert.assertEquals(8, Strings.levenshteinDistance("rosettacode", "raisethysword"));
  }

  @Test(expected = NullPointerException.class)
  public void testNormalizedLevenshteinDistanceWithNullWord1() {
    int distance = Strings.levenshteinDistance(null, "word2");
  }

  @Test(expected = NullPointerException.class)
  public void testNormalizedLevenshteinDistanceWithNullWord2() {
    int distance = Strings.levenshteinDistance("word1", null);
  }

  @Test
  public void testNormalizedLevenshteinDistance() {
    Assert.assertEquals(0.0, Strings.normalizedLevenshteinDistance("", ""), 0.00000001);
    Assert.assertEquals(0.0, Strings.normalizedLevenshteinDistance("ABCDE", "ABCDE"), 0.00000001);
    Assert.assertEquals(1.0 / 5.0, Strings.normalizedLevenshteinDistance("ABCDE", "ABCD"),
        0.00000001);
    Assert.assertEquals(3.0 / 7.0, Strings.normalizedLevenshteinDistance("kitten", "sitting"),
        0.00000001);
    Assert.assertEquals(5.0 / 7.0, Strings.normalizedLevenshteinDistance("matrix", "xxxmatr"),
        0.00000001);
    Assert.assertEquals(8.0 / 13.0,
        Strings.normalizedLevenshteinDistance("rosettacode", "raisethysword"), 0.00000001);
  }

  @Test(expected = NullPointerException.class)
  public void testNullByteArrayToHexadecimalString() {
    String string = Strings.hex(null);
  }

  @Test
  public void testByteArrayToHexadecimalString() {

    byte[] bytes = "test".getBytes(StandardCharsets.UTF_8);

    assertEquals("74657374", Strings.hex(bytes));
  }

  @Test(expected = NullPointerException.class)
  public void testNullStringToByteArray() {
    byte[] bytes = Strings.unhex(null);
  }

  @Test
  public void testHexadecimalStringToByteArray() {

    String string = "74657374";

    assertEquals("test", new String(Strings.unhex(string), StandardCharsets.UTF_8));
  }

  /**
   * Mostly extracted from
   * https://github.com/apache/commons-lang/blob/master/src/test/java/org/apache/commons/lang3/math/NumberUtilsTest.java
   */
  @Test
  public void testInvalidNumbers() {
    assertFalse(Strings.isNumber(null));
    assertFalse(Strings.isNumber(""));
    assertFalse(Strings.isNumber(" "));
    assertFalse(Strings.isNumber("\r\n\t"));
    assertFalse(Strings.isNumber("--2.3"));
    assertFalse(Strings.isNumber(".12.3"));
    assertFalse(Strings.isNumber("-123E"));
    assertFalse(Strings.isNumber("-123E+-212"));
    assertFalse(Strings.isNumber("-123E2.12"));
    assertFalse(Strings.isNumber("0xGF"));
    assertFalse(Strings.isNumber("0xFAE-1"));
    assertFalse(Strings.isNumber("."));
    assertFalse(Strings.isNumber("-0ABC123"));
    assertFalse(Strings.isNumber("123.4E-D"));
    assertFalse(Strings.isNumber("123.4ED"));
    assertFalse(Strings.isNumber("+000E.12345"));
    assertFalse(Strings.isNumber("-000E.12345"));
    assertFalse(Strings.isNumber("1234E5l"));
    assertFalse(Strings.isNumber("11a"));
    assertFalse(Strings.isNumber("1a"));
    assertFalse(Strings.isNumber("a"));
    assertFalse(Strings.isNumber("11g"));
    assertFalse(Strings.isNumber("11z"));
    assertFalse(Strings.isNumber("11def"));
    assertFalse(Strings.isNumber("11d11"));
    assertFalse(Strings.isNumber("11 11"));
    assertFalse(Strings.isNumber(" 1111"));
    assertFalse(Strings.isNumber("1111 "));
    assertFalse(Strings.isNumber("1.1L"));

    // Added
    assertFalse(Strings.isNumber("+00.12345"));
    assertFalse(Strings.isNumber("+0002.12345"));
    assertFalse(Strings.isNumber("0x"));
    assertFalse(Strings.isNumber("EE"));
    assertFalse(Strings.isNumber("."));
    assertFalse(Strings.isNumber("1E-"));
    assertFalse(Strings.isNumber("123.4E."));
    assertFalse(Strings.isNumber("123.4E15E10"));
  }

  /**
   * Mostly extracted from
   * https://github.com/apache/commons-lang/blob/master/src/test/java/org/apache/commons/lang3/math/NumberUtilsTest.java
   */
  @Test
  public void testValidNumbers() {
    assertTrue(Strings.isNumber("12345"));
    assertTrue(Strings.isNumber("1234.5"));
    assertTrue(Strings.isNumber(".12345"));
    assertTrue(Strings.isNumber("1234E5"));
    assertTrue(Strings.isNumber("1234E+5"));
    assertTrue(Strings.isNumber("1234E-5"));
    assertTrue(Strings.isNumber("123.4E5"));
    assertTrue(Strings.isNumber("-1234"));
    assertTrue(Strings.isNumber("-1234.5"));
    assertTrue(Strings.isNumber("-.12345"));
    assertTrue(Strings.isNumber("-0001.12345"));
    assertTrue(Strings.isNumber("-000.12345"));
    assertTrue(Strings.isNumber("-1234E5"));
    assertTrue(Strings.isNumber("0"));
    assertTrue(Strings.isNumber("-0"));
    assertTrue(Strings.isNumber("01234"));
    assertTrue(Strings.isNumber("-01234"));
    assertTrue(Strings.isNumber("-0xABC123"));
    assertTrue(Strings.isNumber("-0x0"));
    assertTrue(Strings.isNumber("123.4E21D"));
    assertTrue(Strings.isNumber("-221.23F"));
    assertTrue(Strings.isNumber("22338L"));
    assertTrue(Strings.isNumber("2."));
  }
}
