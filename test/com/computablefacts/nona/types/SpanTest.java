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
}
