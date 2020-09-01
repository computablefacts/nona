package com.computablefacts.nona.dictionaries;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;

public class TldTest {

  @Test
  public void testEqualsNull() {
    Tld.load().forEach(tld -> Assert.assertFalse(tld.equals(null)));
  }

  @Test
  public void testEqualsItself() {
    Tld.load().forEach(tld -> Assert.assertTrue(tld.equals(tld)));
  }

  @Test
  public void testEqualsAndHashcode() {

    Set<String> tlds1 = Tld.load();
    Set<String> tlds2 = Tld.load();

    Assert.assertTrue(Sets.symmetricDifference(tlds1, tlds2).isEmpty());
  }
}
