package com.computablefacts.nona.types;

import org.junit.Assert;
import org.junit.Test;

public class SpanSequenceTest {

  @Test(expected = NullPointerException.class)
  public void testAddNullSpan() {
    SpanSequence sequence = new SpanSequence();
    sequence.add(null);
  }

  @Test
  public void testEquals() {

    SpanSequence sequence1 = new SpanSequence();
    SpanSequence sequence2 = new SpanSequence();
    SpanSequence sequence3 = new SpanSequence();

    for (int i = 0; i < 100; i++) {

      Span span1 = new Span("span-" + Integer.toString(i, 10));
      Span span2 = new Span("span-" + Integer.toString(i, 10));
      Span span3 = new Span("span_" + Integer.toString(i, 10));

      sequence1.add(span1);
      sequence2.add(span2);
      sequence3.add(span3);
    }

    Assert.assertEquals(sequence1, sequence1);
    Assert.assertEquals(sequence2, sequence2);
    Assert.assertEquals(sequence3, sequence3);

    Assert.assertEquals(sequence1, sequence2);

    Assert.assertNotEquals(sequence3, sequence1);
    Assert.assertNotEquals(sequence3, sequence2);
  }

  @Test
  public void testHashcode() {

    SpanSequence sequence1 = new SpanSequence();
    SpanSequence sequence2 = new SpanSequence();
    SpanSequence sequence3 = new SpanSequence();

    for (int i = 0; i < 100; i++) {

      Span span1 = new Span("span-" + Integer.toString(i, 10));
      Span span2 = new Span("span-" + Integer.toString(i, 10));
      Span span3 = new Span("span_" + Integer.toString(i, 10));

      sequence1.add(span1);
      sequence2.add(span2);
      sequence3.add(span3);
    }

    Assert.assertEquals(sequence1.hashCode(), sequence1.hashCode());
    Assert.assertEquals(sequence2.hashCode(), sequence2.hashCode());
    Assert.assertEquals(sequence3.hashCode(), sequence3.hashCode());

    Assert.assertEquals(sequence1.hashCode(), sequence2.hashCode());

    Assert.assertNotEquals(sequence3.hashCode(), sequence1.hashCode());
    Assert.assertNotEquals(sequence3.hashCode(), sequence2.hashCode());
  }

  @Test
  public void testCompareTo() {

    SpanSequence sequence1 = new SpanSequence();
    SpanSequence sequence2 = new SpanSequence();

    for (int i = 0; i < 100; i++) {

      Span span1 = new Span("span-" + Integer.toString(i, 10));
      Span span2 = new Span("span-" + Integer.toString(i, 10));

      sequence1.add(span1);
      sequence2.add(span2);
    }

    Assert.assertEquals(0, sequence1.compareTo(sequence2));

    sequence2.sequence().sort(Span::compareTo);

    Assert.assertEquals(-1, sequence2.compareTo(sequence1));
    Assert.assertEquals(1, sequence1.compareTo(sequence2));
  }
}
