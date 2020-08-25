package com.computablefacts.nona.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.google.common.collect.Lists;

public class NGramIteratorTest {

  @Test(expected = NullPointerException.class)
  public void testNullString() {
    Iterator<String> iterator = new NGramIterator(3, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroLength() {
    Iterator<String> iterator = new NGramIterator(0, "test");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeLength() {
    Iterator<String> iterator = new NGramIterator(-1, "test");
  }

  @Test
  public void testHasNextOnEmptyString() {

    Iterator<String> iterator = new NGramIterator(3, "");

    assertFalse(iterator.hasNext());
  }

  @Test(expected = NoSuchElementException.class)
  public void testNextOnEmptyString() {

    Iterator<String> iterator = new NGramIterator(3, "");

    assertNull(iterator.next());
  }

  @Test
  public void testOverlapsTrue() {

    Iterator<String> iterator = new NGramIterator(3, "overlaps", true);
    List<String> ngrams = Lists.newArrayList(iterator);

    assertEquals(6, ngrams.size());
    assertEquals("ove", ngrams.get(0));
    assertEquals("ver", ngrams.get(1));
    assertEquals("erl", ngrams.get(2));
    assertEquals("rla", ngrams.get(3));
    assertEquals("lap", ngrams.get(4));
    assertEquals("aps", ngrams.get(5));
  }

  @Test
  public void testOverlapsFalse() {

    Iterator<String> iterator = new NGramIterator(3, "overlaps", false);
    List<String> ngrams = Lists.newArrayList(iterator);

    assertEquals(3, ngrams.size());
    assertEquals("ove", ngrams.get(0));
    assertEquals("rla", ngrams.get(1));
    assertEquals("ps", ngrams.get(2));
  }
}
