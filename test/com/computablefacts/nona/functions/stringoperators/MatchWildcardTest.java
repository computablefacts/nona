package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class MatchWildcardTest {

  @Test
  public void testMatchOneCharacter() {

    Function fn = new Function(
        "MATCH_WILDCARD(cve¤references¤reference_data¤A1A¤url, cve¤references¤reference_data¤A?A¤url)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMatchOneCharacterCaseSensitivity() {

    Function fn = new Function(
        "MATCH_WILDCARD(cve¤references¤reference_data¤A1A¤url, CVE¤REFERENCES¤REFERENCE_DATA¤A?A¤URL)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMatchMoreThanOneCharacter() {

    Function fn = new Function(
        "MATCH_WILDCARD(cve¤references¤reference_data¤A1A¤url, cve¤references¤reference_data¤*)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testMatchMoreThanOneCharacterCaseSensitivity() {

    Function fn = new Function(
        "MATCH_WILDCARD(cve¤references¤reference_data¤A1A¤url, CVE¤REFERENCES¤REFERENCE_DATA¤*)");
    Assert.assertEquals(BoxedType.of(true), fn.evaluate(Function.definitions()));
  }
}
