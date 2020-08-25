package com.computablefacts.nona.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.computablefacts.nona.types.Span;
import com.google.common.collect.Lists;

public class NGramIteratorTest {

  @Test(expected = NullPointerException.class)
  public void testNullString() {
    Iterator<Span> iterator = new NGramIterator(3, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroLength() {
    Iterator<Span> iterator = new NGramIterator(0, "test");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeLength() {
    Iterator<Span> iterator = new NGramIterator(-1, "test");
  }

  @Test
  public void testHasNextOnEmptyString() {

    Iterator<Span> iterator = new NGramIterator(3, "");

    assertFalse(iterator.hasNext());
  }

  @Test(expected = NoSuchElementException.class)
  public void testNextOnEmptyString() {

    Iterator<Span> iterator = new NGramIterator(3, "");

    assertNull(iterator.next());
  }

  @Test
  public void testOverlapsTrue() {

    Iterator<Span> iterator = new NGramIterator(3, "overlaps", true);
    List<Span> ngrams = Lists.newArrayList(iterator);

    assertEquals(6, ngrams.size());
    assertEquals("ove", ngrams.get(0).text());
    assertEquals("ver", ngrams.get(1).text());
    assertEquals("erl", ngrams.get(2).text());
    assertEquals("rla", ngrams.get(3).text());
    assertEquals("lap", ngrams.get(4).text());
    assertEquals("aps", ngrams.get(5).text());
  }

  @Test
  public void testOverlapsFalse() {

    Iterator<Span> iterator = new NGramIterator(3, "overlaps", false);
    List<Span> ngrams = Lists.newArrayList(iterator);

    assertEquals(3, ngrams.size());
    assertEquals("ove", ngrams.get(0).text());
    assertEquals("rla", ngrams.get(1).text());
    assertEquals("ps", ngrams.get(2).text());
  }
}
