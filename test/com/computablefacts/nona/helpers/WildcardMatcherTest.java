package com.computablefacts.nona.helpers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

@Deprecated
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
    assertMatch(null, null, "");
    assertMatch(null, "", "");
    assertNoMatch(null, "not empty");
  }

  @Test
  public void testMatchEmptyInput() {
    assertMatch("", null, "");
    assertMatch("", "", "");
    assertNoMatch("", "not empty");
  }

  @Test
  public void testMatchNullPattern() {
    assertMatch("", null, "");
    assertNoMatch("not empty", null);
  }

  @Test
  public void testMatchEmptyPattern() {
    assertMatch("", "", "");
    assertNoMatch("not empty", "");
  }

  @Test
  public void testMatchSingleStarPattern() {
    assertNoMatch("", "*");
    assertMatch("not empty", "*", "not empty");
    assertMatch("*", "*", "*");
  }

  @Test
  public void testMatchSingleQuestionMarkPattern() {
    assertNoMatch("", "?");
    assertNoMatch("not empty", "?");
    assertMatch("*", "?", "*");
  }

  @Test
  public void testMatchPatterns() {
    assertMatch("baaabab", "*****ba*****ab", "baaabab");
    assertMatch("baaabab", "ba*****ab", "baaabab");
    assertMatch("baaabab", "ba*ab", "baaabab");
    assertMatch("baaabab", "*a*****ab", "baaabab");
    assertMatch("baaabab", "ba*ab****", "baaabab");
    assertMatch("baaabab", "****", "baaabab");
    assertMatch("baaabab", "*", "baaabab");
    assertMatch("baaabab", "b*b", "baaabab");
    assertMatch("baaabab", "baaabab", "baaabab");
    assertMatch("baaabab", "*baaaba*", "baaabab");
    assertMatch("baaabab", "???????", "baaabab");
  }

  @Test
  public void testMatchNoPatterns() {
    assertNoMatch("baaabab", "a*ab");
    assertNoMatch("baaabab", "a*****ab");
    assertNoMatch("baaabab", "a*a");
    assertNoMatch("baaabab", "aa?ab");
    assertNoMatch("baaabab", "?baaabab");
    assertNoMatch("baaabab", "????????");
  }

  @Test
  public void testMatchPatternsCaseInsensitive() {
    assertMatch("BaaaBaB", "*****ba*****ab", "BaaaBaB");
    assertMatch("BaaaBaB", "ba*****ab", "BaaaBaB");
    assertMatch("BaaaBaB", "ba*ab", "BaaaBaB");
    assertMatch("BaaaBaB", "*a*****ab", "BaaaBaB");
    assertMatch("BaaaBaB", "ba*ab****", "BaaaBaB");
    assertMatch("BaaaBaB", "****", "BaaaBaB");
    assertMatch("BaaaBaB", "*", "BaaaBaB");
    assertMatch("BaaaBaB", "b*b", "BaaaBaB");
    assertMatch("BaaaBaB", "baaabab", "BaaaBaB");
    assertMatch("BaaaBaB", "*baaaba*", "BaaaBaB");
    assertMatch("BaaaBaB", "???????", "BaaaBaB");
  }

  private void assertMatch(String str, String pattern, String expected) {
    StringBuilder builder = new StringBuilder();
    assertTrue(WildcardMatcher.match(str, pattern, builder));
    assertEquals(expected, builder.toString());
  }

  private void assertNoMatch(String str, String pattern) {
    StringBuilder builder = new StringBuilder();
    assertFalse(WildcardMatcher.match(str, pattern, builder));
    assertEquals("", builder.toString());
  }
}
