package com.computablefacts.nona.functions.stringoperators;

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
}
