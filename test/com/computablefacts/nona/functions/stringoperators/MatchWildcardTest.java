package com.computablefacts.nona.functions.stringoperators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;

public class MatchWildcardTest {

  @Test
  public void testMatchOneCharacter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MATCH_WILDCARD", new MatchWildcard());

    Function fn = new Function(
        "MATCH_WILDCARD(cve¤references¤reference_data¤A1A¤url, cve¤references¤reference_data¤A?A¤url)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testMatchOneCharacterCaseSensitivity() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MATCH_WILDCARD", new MatchWildcard());

    Function fn = new Function(
        "MATCH_WILDCARD(cve¤references¤reference_data¤A1A¤url, CVE¤REFERENCES¤REFERENCE_DATA¤A?A¤URL)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testMatchMoreThanOneCharacter() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MATCH_WILDCARD", new MatchWildcard());

    Function fn = new Function(
        "MATCH_WILDCARD(cve¤references¤reference_data¤A1A¤url, cve¤references¤reference_data¤*)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testMatchMoreThanOneCharacterCaseSensitivity() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("MATCH_WILDCARD", new MatchWildcard());

    Function fn = new Function(
        "MATCH_WILDCARD(cve¤references¤reference_data¤A1A¤url, CVE¤REFERENCES¤REFERENCE_DATA¤*)");
    Assert.assertEquals(BoxedType.create(true), fn.evaluate(functions));
  }

  @Test
  public void testNullInput() {
    assertTrue(new MatchWildcard().match(null, null));
    assertTrue(new MatchWildcard().match(null, ""));
    assertFalse(new MatchWildcard().match(null, "not empty"));
  }

  @Test
  public void testEmptyInput() {
    assertTrue(new MatchWildcard().match("", null));
    assertTrue(new MatchWildcard().match("", ""));
    assertFalse(new MatchWildcard().match("", "not empty"));
  }

  @Test
  public void testNullPattern() {
    assertTrue(new MatchWildcard().match("", null));
    assertFalse(new MatchWildcard().match("not empty", null));
  }

  @Test
  public void testEmptyPattern() {
    assertTrue(new MatchWildcard().match("", ""));
    assertFalse(new MatchWildcard().match("not empty", ""));
  }

  @Test
  public void testSingleStarPattern() {
    assertFalse(new MatchWildcard().match("", "*"));
    assertTrue(new MatchWildcard().match("not empty", "*"));
    assertTrue(new MatchWildcard().match("*", "*"));
  }

  @Test
  public void testSingleQuestionMarkPattern() {
    assertFalse(new MatchWildcard().match("", "?"));
    assertFalse(new MatchWildcard().match("not empty", "?"));
    assertTrue(new MatchWildcard().match("*", "?"));
  }

  @Test
  public void testMatchPatterns() {
    assertTrue(new MatchWildcard().match("baaabab", "*****ba*****ab"));
    assertTrue(new MatchWildcard().match("baaabab", "ba*****ab"));
    assertTrue(new MatchWildcard().match("baaabab", "ba*ab"));
    assertTrue(new MatchWildcard().match("baaabab", "*a*****ab"));
    assertTrue(new MatchWildcard().match("baaabab", "ba*ab****"));
    assertTrue(new MatchWildcard().match("baaabab", "****"));
    assertTrue(new MatchWildcard().match("baaabab", "*"));
    assertTrue(new MatchWildcard().match("baaabab", "b*b"));
    assertTrue(new MatchWildcard().match("baaabab", "baaabab"));
    assertTrue(new MatchWildcard().match("baaabab", "*baaaba*"));
    assertTrue(new MatchWildcard().match("baaabab", "???????"));
  }

  @Test
  public void testNoMatchPatterns() {
    assertFalse(new MatchWildcard().match("baaabab", "a*ab"));
    assertFalse(new MatchWildcard().match("baaabab", "a*****ab"));
    assertFalse(new MatchWildcard().match("baaabab", "a*a"));
    assertFalse(new MatchWildcard().match("baaabab", "aa?ab"));
    assertFalse(new MatchWildcard().match("baaabab", "?baaabab"));
    assertFalse(new MatchWildcard().match("baaabab", "????????"));
  }

  @Test
  public void testCaseInsensitivePatterns() {
    assertTrue(new MatchWildcard().match("BaaaBaB", "*****ba*****ab"));
    assertTrue(new MatchWildcard().match("BaaaBaB", "ba*****ab"));
    assertTrue(new MatchWildcard().match("BaaaBaB", "ba*ab"));
    assertTrue(new MatchWildcard().match("BaaaBaB", "*a*****ab"));
    assertTrue(new MatchWildcard().match("BaaaBaB", "ba*ab****"));
    assertTrue(new MatchWildcard().match("BaaaBaB", "****"));
    assertTrue(new MatchWildcard().match("BaaaBaB", "*"));
    assertTrue(new MatchWildcard().match("BaaaBaB", "b*b"));
    assertTrue(new MatchWildcard().match("BaaaBaB", "baaabab"));
    assertTrue(new MatchWildcard().match("BaaaBaB", "*baaaba*"));
    assertTrue(new MatchWildcard().match("BaaaBaB", "???????"));
  }
}
