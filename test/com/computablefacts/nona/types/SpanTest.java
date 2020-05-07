package com.computablefacts.nona.types;

import org.junit.Assert;
import org.junit.Test;

public class SpanTest {

  @Test(expected = NullPointerException.class)
  public void testConstructorWithEmptyString() {
    Span span = new Span(null, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithOutOfBoundBegin() {
    Span span = new Span("text", -1, "text".length());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithOutOfBoundEnd() {
    Span span = new Span("text", 0, "text".length() + 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithEndBefore() {
    Span span = new Span("text", 1, 0);
  }

  @Test
  public void testEquals() {

    Span span1 = new Span("text", 0, "text".length());
    Span span2 = new Span("text", 0, "text".length());

    Assert.assertEquals(span1, span2);
  }

  @Test
  public void testNotEqual() {

    Span span1 = new Span("text1", 0, "text1".length() - 1);
    Span span2 = new Span("text2", 0, "text2".length() - 1);

    Assert.assertNotEquals(span1, span2);
  }

  @Test
  public void testHashcode() {

    Span span1 = new Span("text", 0, "text".length());
    Span span2 = new Span("text", 0, "text".length());

    Assert.assertEquals(span1.hashCode(), span2.hashCode());
  }

  @Test
  public void testNotTheSameHashcode() {

    Span span1 = new Span("text1", 0, "text1".length() - 1);
    Span span2 = new Span("text2", 0, "text2".length() - 1);

    Assert.assertNotEquals(span1.hashCode(), span2.hashCode());
  }

  @Test
  public void testCompareToLT() {

    Span span1 = new Span("text1", 0, "text1".length() - 1);
    Span span2 = new Span("text2", 0, "text2".length() - 1);

    Assert.assertTrue(span1.compareTo(span2) < 0);
  }

  @Test
  public void testCompareToGT() {

    Span span1 = new Span("text1", 0, "text1".length() - 1);
    Span span2 = new Span("text2", 0, "text2".length() - 1);

    Assert.assertTrue(span2.compareTo(span1) > 0);
  }

  @Test
  public void testCompareToEQ() {

    Span span1 = new Span("text", 0, "text".length());
    Span span2 = new Span("text", 0, "text".length());

    Assert.assertTrue(span1.compareTo(span2) == 0);
  }

  @Test
  public void testText() {

    Span span1 = new Span("text1", 0, "text1".length() - 1);
    Span span2 = new Span("text2", 0, "text2".length() - 1);

    Assert.assertEquals(span1.text(), span2.text());
  }

  @Test(expected = NullPointerException.class)
  public void testSetFeatureWithNullKey() {
    Span span = new Span("text", 0, "text".length());
    span.setFeature(null, "value");
  }

  @Test(expected = NullPointerException.class)
  public void testSetFeatureWithNullValue() {
    Span span = new Span("text", 0, "text".length());
    span.setFeature("key", null);
  }

  @Test
  public void testGetFeature() {

    Span span = new Span("text", 0, "text".length());
    span.setFeature("key", "value1");

    Assert.assertEquals("value1", span.getFeature("key"));

    span.setFeature("key", "value2");

    Assert.assertEquals("value2", span.getFeature("key"));
  }

  @Test
  public void testSize() {

    Span span1 = new Span("Hello world!", 0, "Hello world!".length());

    Assert.assertEquals("Hello world!", span1.text());
    Assert.assertEquals(12, span1.length());

    Span span2 = new Span("Hello world!", 0, 5);

    Assert.assertEquals("Hello", span2.text());
    Assert.assertEquals(5, span2.length());
  }

  @Test
  public void testOverlaps() {

    Span span4567 = new Span("123456789", 3, 7);
    Span span345 = new Span("123456789", 2, 5);
    Span span678 = new Span("123456789", 5, 8);
    Span span56 = new Span("123456789", 4, 6);
    Span span89 = new Span("123456789", 7, 9);

    Assert.assertTrue(span4567.overlaps(span345));
    Assert.assertTrue(span345.overlaps(span4567));

    Assert.assertTrue(span4567.overlaps(span678));
    Assert.assertTrue(span678.overlaps(span4567));

    Assert.assertTrue(span4567.overlaps(span56));
    Assert.assertTrue(span56.overlaps(span4567));

    Assert.assertFalse(span4567.overlaps(span89));
    Assert.assertFalse(span89.overlaps(span4567));
  }

  @Test
  public void testOverlapsLeft() {

    Span span4567 = new Span("123456789", 3, 7);
    Span span345 = new Span("123456789", 2, 5);
    Span span567 = new Span("123456789", 4, 7);

    Assert.assertTrue(span4567.overlaps(span345));
    Assert.assertTrue(span345.overlaps(span4567));

    Assert.assertTrue(span4567.overlapsRight(span345));
    Assert.assertTrue(span345.overlapsLeft(span4567));

    Assert.assertTrue(span345.overlaps(span567));
    Assert.assertTrue(span567.overlaps(span345));

    Assert.assertTrue(span345.overlapsLeft(span567));
    Assert.assertTrue(span567.overlapsRight(span345));
  }

  @Test
  public void testOverlapsRight() {

    Span span4567 = new Span("123456789", 3, 7);
    Span span678 = new Span("123456789", 5, 8);
    Span span456 = new Span("123456789", 3, 6);

    Assert.assertTrue(span4567.overlaps(span678));
    Assert.assertTrue(span678.overlaps(span4567));

    Assert.assertTrue(span4567.overlapsLeft(span678));
    Assert.assertTrue(span678.overlapsRight(span4567));

    Assert.assertTrue(span678.overlaps(span456));
    Assert.assertTrue(span456.overlaps(span678));

    Assert.assertTrue(span678.overlapsRight(span456));
    Assert.assertTrue(span456.overlapsLeft(span678));
  }

  @Test
  public void testOverlapsAll() {

    Span span4567 = new Span("123456789", 3, 7);
    Span span89 = new Span("123456789", 4, 6);

    Assert.assertTrue(span4567.overlaps(span89));
    Assert.assertTrue(span89.overlaps(span4567));

    Assert.assertTrue(span4567.overlapsLeft(span89));
    Assert.assertTrue(span4567.overlapsRight(span89));

    Assert.assertTrue(span4567.overlapsAll(span89));
    Assert.assertFalse(span89.overlapsAll(span4567));
  }
}
