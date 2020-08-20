package com.computablefacts.nona.functions.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class WildcardMatcherTest {

  @Test
  public void testMinNumberOfCharacters() {
    assertEquals(0, WildcardMatcher.minNumberOfCharacters(null));
    assertEquals(0, WildcardMatcher.minNumberOfCharacters(""));
    assertEquals(4, WildcardMatcher.minNumberOfCharacters("*****ba*****ab"));
    assertEquals(4, WildcardMatcher.minNumberOfCharacters("ba*****ab"));
    assertEquals(4, WildcardMatcher.minNumberOfCharacters("ba*ab"));
    assertEquals(3, WildcardMatcher.minNumberOfCharacters("*a*****ab"));
    assertEquals(4, WildcardMatcher.minNumberOfCharacters("ba*ab****"));
    assertEquals(0, WildcardMatcher.minNumberOfCharacters("****"));
    assertEquals(0, WildcardMatcher.minNumberOfCharacters("*"));
    assertEquals(2, WildcardMatcher.minNumberOfCharacters("b*b"));
    assertEquals(7, WildcardMatcher.minNumberOfCharacters("baaabab"));
    assertEquals(6, WildcardMatcher.minNumberOfCharacters("*baaaba*"));
    assertEquals(7, WildcardMatcher.minNumberOfCharacters("???????"));
  }

  @Test(expected = NullPointerException.class)
  public void testPrefixNull() {
    String prefix = WildcardMatcher.prefix(null);
  }

  @Test
  public void testPrefix() {
    assertEquals("", WildcardMatcher.prefix(""));
    assertEquals("", WildcardMatcher.prefix("*****ba*****ab"));
    assertEquals("ba", WildcardMatcher.prefix("ba*****ab"));
    assertEquals("ba", WildcardMatcher.prefix("ba*ab"));
    assertEquals("", WildcardMatcher.prefix("*a*****ab"));
    assertEquals("ba", WildcardMatcher.prefix("ba*ab****"));
    assertEquals("", WildcardMatcher.prefix("****"));
    assertEquals("", WildcardMatcher.prefix("*"));
    assertEquals("b", WildcardMatcher.prefix("b*b"));
    assertEquals("baaabab", WildcardMatcher.prefix("baaabab"));
    assertEquals("", WildcardMatcher.prefix("*baaaba*"));
    assertEquals("", WildcardMatcher.prefix("???????"));
  }

  @Test(expected = NullPointerException.class)
  public void testSuffixNull() {
    String suffix = WildcardMatcher.suffix(null);
  }

  @Test
  public void testSuffix() {
    assertEquals("", WildcardMatcher.suffix(""));
    assertEquals("ab", WildcardMatcher.suffix("*****ba*****ab"));
    assertEquals("ab", WildcardMatcher.suffix("ba*****ab"));
    assertEquals("ab", WildcardMatcher.suffix("ba*ab"));
    assertEquals("ab", WildcardMatcher.suffix("*a*****ab"));
    assertEquals("", WildcardMatcher.suffix("ba*ab****"));
    assertEquals("", WildcardMatcher.suffix("****"));
    assertEquals("", WildcardMatcher.suffix("*"));
    assertEquals("b", WildcardMatcher.suffix("b*b"));
    assertEquals("baaabab", WildcardMatcher.suffix("baaabab"));
    assertEquals("", WildcardMatcher.suffix("*baaaba*"));
    assertEquals("", WildcardMatcher.suffix("???????"));
  }

  @Test
  public void testSplitPatternWithoutWildcards() {

    List<String> splits = new ArrayList<>();
    splits.add("baba");

    assertEquals(splits, WildcardMatcher.split("baba"));
  }

  @Test
  public void testSplitPatternWithWildcards() {

    List<String> splits = new ArrayList<>();
    splits.add("?");
    splits.add("ba");
    splits.add("*");
    splits.add("ab");
    splits.add("*");

    assertEquals(splits, WildcardMatcher.split("?ba*ab*"));
  }

  @Test(expected = NullPointerException.class)
  public void testHasWildcardsNull() {
    boolean hasWildcards = WildcardMatcher.hasWildcards(null);
  }

  @Test
  public void testHasWildcards() {
    assertFalse(WildcardMatcher.hasWildcards(""));
    assertTrue(WildcardMatcher.hasWildcards("*****ba*****ab"));
    assertTrue(WildcardMatcher.hasWildcards("ba*****ab"));
    assertTrue(WildcardMatcher.hasWildcards("ba*ab"));
    assertTrue(WildcardMatcher.hasWildcards("*a*****ab"));
    assertTrue(WildcardMatcher.hasWildcards("ba*ab****"));
    assertTrue(WildcardMatcher.hasWildcards("****"));
    assertTrue(WildcardMatcher.hasWildcards("*"));
    assertTrue(WildcardMatcher.hasWildcards("b*b"));
    assertFalse(WildcardMatcher.hasWildcards("baaabab"));
    assertTrue(WildcardMatcher.hasWildcards("*baaaba*"));
    assertTrue(WildcardMatcher.hasWildcards("???????"));
  }

  @Test(expected = NullPointerException.class)
  public void testIsOnlyMadeOfWildcardsNull() {
    boolean isOnlyMadeOfWildcards = WildcardMatcher.isOnlyMadeOfWildcards(null);
  }

  @Test
  public void testIsOnlyMadeOfWildcards() {
    assertFalse(WildcardMatcher.isOnlyMadeOfWildcards(""));
    assertFalse(WildcardMatcher.isOnlyMadeOfWildcards("*****ba*****ab"));
    assertFalse(WildcardMatcher.isOnlyMadeOfWildcards("ba*****ab"));
    assertFalse(WildcardMatcher.isOnlyMadeOfWildcards("ba*ab"));
    assertFalse(WildcardMatcher.isOnlyMadeOfWildcards("*a*****ab"));
    assertFalse(WildcardMatcher.isOnlyMadeOfWildcards("ba*ab****"));
    assertTrue(WildcardMatcher.isOnlyMadeOfWildcards("****"));
    assertTrue(WildcardMatcher.isOnlyMadeOfWildcards("*"));
    assertFalse(WildcardMatcher.isOnlyMadeOfWildcards("b*b"));
    assertFalse(WildcardMatcher.isOnlyMadeOfWildcards("baaabab"));
    assertFalse(WildcardMatcher.isOnlyMadeOfWildcards("*baaaba*"));
    assertTrue(WildcardMatcher.isOnlyMadeOfWildcards("???????"));
  }

  @Test(expected = NullPointerException.class)
  public void testStartsWithWildcardsNull() {
    boolean startsWithWildcard = WildcardMatcher.startsWithWildcard(null);
  }

  @Test
  public void testStartsWithWildcards() {
    assertFalse(WildcardMatcher.startsWithWildcard(""));
    assertTrue(WildcardMatcher.startsWithWildcard("*****ba*****ab"));
    assertFalse(WildcardMatcher.startsWithWildcard("ba*****ab"));
    assertFalse(WildcardMatcher.startsWithWildcard("ba*ab"));
    assertTrue(WildcardMatcher.startsWithWildcard("*a*****ab"));
    assertFalse(WildcardMatcher.startsWithWildcard("ba*ab****"));
    assertTrue(WildcardMatcher.startsWithWildcard("****"));
    assertTrue(WildcardMatcher.startsWithWildcard("*"));
    assertFalse(WildcardMatcher.startsWithWildcard("b*b"));
    assertFalse(WildcardMatcher.startsWithWildcard("baaabab"));
    assertTrue(WildcardMatcher.startsWithWildcard("*baaaba*"));
    assertTrue(WildcardMatcher.startsWithWildcard("???????"));
  }

  @Test(expected = NullPointerException.class)
  public void testEndsWithWildcardsNull() {
    boolean endsWithWildcard = WildcardMatcher.endsWithWildcard(null);
  }

  @Test
  public void testEndsWithWildcards() {
    assertFalse(WildcardMatcher.endsWithWildcard(""));
    assertFalse(WildcardMatcher.endsWithWildcard("*****ba*****ab"));
    assertFalse(WildcardMatcher.endsWithWildcard("ba*****ab"));
    assertFalse(WildcardMatcher.endsWithWildcard("ba*ab"));
    assertFalse(WildcardMatcher.endsWithWildcard("*a*****ab"));
    assertTrue(WildcardMatcher.endsWithWildcard("ba*ab****"));
    assertTrue(WildcardMatcher.endsWithWildcard("****"));
    assertTrue(WildcardMatcher.endsWithWildcard("*"));
    assertFalse(WildcardMatcher.endsWithWildcard("b*b"));
    assertFalse(WildcardMatcher.endsWithWildcard("baaabab"));
    assertTrue(WildcardMatcher.endsWithWildcard("*baaaba*"));
    assertTrue(WildcardMatcher.endsWithWildcard("???????"));
  }

  @Test(expected = NullPointerException.class)
  public void testCompactNull() {
    String pattern = WildcardMatcher.compact(null);
  }

  @Test
  public void testCompact() {
    assertEquals("", WildcardMatcher.compact(""));
    assertEquals("*ba*ab", WildcardMatcher.compact("*****ba*****ab"));
    assertEquals("ba*ab", WildcardMatcher.compact("ba*****ab"));
    assertEquals("ba*ab", WildcardMatcher.compact("ba*ab"));
    assertEquals("*a*ab", WildcardMatcher.compact("*a*****ab"));
    assertEquals("ba*ab*", WildcardMatcher.compact("ba*ab****"));
    assertEquals("*", WildcardMatcher.compact("****"));
    assertEquals("*", WildcardMatcher.compact("*"));
    assertEquals("b*b", WildcardMatcher.compact("b*b"));
    assertEquals("baaabab", WildcardMatcher.compact("baaabab"));
    assertEquals("*baaaba*", WildcardMatcher.compact("*baaaba*"));
    assertEquals("???????", WildcardMatcher.compact("???????"));
  }

  @Test
  public void testMatchNullInput() {
    assertTrue(WildcardMatcher.match(null, null));
    assertTrue(WildcardMatcher.match(null, ""));
    assertFalse(WildcardMatcher.match(null, "not empty"));
  }

  @Test
  public void testMatchEmptyInput() {
    assertTrue(WildcardMatcher.match("", null));
    assertTrue(WildcardMatcher.match("", ""));
    assertFalse(WildcardMatcher.match("", "not empty"));
  }

  @Test
  public void testMatchNullPattern() {
    assertTrue(WildcardMatcher.match("", null));
    assertFalse(WildcardMatcher.match("not empty", null));
  }

  @Test
  public void testMatchEmptyPattern() {
    assertTrue(WildcardMatcher.match("", ""));
    assertFalse(WildcardMatcher.match("not empty", ""));
  }

  @Test
  public void testMatchSingleStarPattern() {
    assertFalse(WildcardMatcher.match("", "*"));
    assertTrue(WildcardMatcher.match("not empty", "*"));
    assertTrue(WildcardMatcher.match("*", "*"));
  }

  @Test
  public void testMatchSingleQuestionMarkPattern() {
    assertFalse(WildcardMatcher.match("", "?"));
    assertFalse(WildcardMatcher.match("not empty", "?"));
    assertTrue(WildcardMatcher.match("*", "?"));
  }

  @Test
  public void testMatchPatterns() {
    assertTrue(WildcardMatcher.match("baaabab", "*****ba*****ab"));
    assertTrue(WildcardMatcher.match("baaabab", "ba*****ab"));
    assertTrue(WildcardMatcher.match("baaabab", "ba*ab"));
    assertTrue(WildcardMatcher.match("baaabab", "*a*****ab"));
    assertTrue(WildcardMatcher.match("baaabab", "ba*ab****"));
    assertTrue(WildcardMatcher.match("baaabab", "****"));
    assertTrue(WildcardMatcher.match("baaabab", "*"));
    assertTrue(WildcardMatcher.match("baaabab", "b*b"));
    assertTrue(WildcardMatcher.match("baaabab", "baaabab"));
    assertTrue(WildcardMatcher.match("baaabab", "*baaaba*"));
    assertTrue(WildcardMatcher.match("baaabab", "???????"));
  }

  @Test
  public void testMatchNoPatterns() {
    assertFalse(WildcardMatcher.match("baaabab", "a*ab"));
    assertFalse(WildcardMatcher.match("baaabab", "a*****ab"));
    assertFalse(WildcardMatcher.match("baaabab", "a*a"));
    assertFalse(WildcardMatcher.match("baaabab", "aa?ab"));
    assertFalse(WildcardMatcher.match("baaabab", "?baaabab"));
    assertFalse(WildcardMatcher.match("baaabab", "????????"));
  }

  @Test
  public void testMatchPatternsCaseInsensitive() {
    assertTrue(WildcardMatcher.match("BaaaBaB", "*****ba*****ab"));
    assertTrue(WildcardMatcher.match("BaaaBaB", "ba*****ab"));
    assertTrue(WildcardMatcher.match("BaaaBaB", "ba*ab"));
    assertTrue(WildcardMatcher.match("BaaaBaB", "*a*****ab"));
    assertTrue(WildcardMatcher.match("BaaaBaB", "ba*ab****"));
    assertTrue(WildcardMatcher.match("BaaaBaB", "****"));
    assertTrue(WildcardMatcher.match("BaaaBaB", "*"));
    assertTrue(WildcardMatcher.match("BaaaBaB", "b*b"));
    assertTrue(WildcardMatcher.match("BaaaBaB", "baaabab"));
    assertTrue(WildcardMatcher.match("BaaaBaB", "*baaaba*"));
    assertTrue(WildcardMatcher.match("BaaaBaB", "???????"));
  }
}
