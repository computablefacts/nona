package com.computablefacts.nona.helpers;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

public class StringsTest {

  @Test
  public void testEncode() {
    Assert.assertEquals("Test legacy encode \\u003a [\\u002c\\u000d\\u000a]",
        Strings.encode("Test legacy encode : [,\n\r]"));
  }

  @Test
  public void testDecode() {
    Assert.assertEquals("Test legacy encode : [,\n\r]",
        Strings.decode("Test legacy encode \\u003a [\\u002c\\u000d\\u000a]"));
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
}
