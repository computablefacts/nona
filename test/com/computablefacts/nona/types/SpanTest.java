package com.computablefacts.nona.types;

import org.junit.Assert;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class SpanTest {

  @Test
  public void testHashcodeAndEquals() {
    EqualsVerifier.forClass(Span.class).withIgnoredFields("groups_").verify();
  }

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
  public void testEqualsWithNull() {

    Span span = new Span("text", 0, "text".length());

    Assert.assertFalse(span.equals(null));
  }

  @Test
  public void testEqualsWithWrongObjectType() {

    Span span = new Span("text", 0, "text".length());

    Assert.assertFalse(span.equals("string"));
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

    Span span1 = new Span("text1", 0, "text1".length());
    Span span2 = new Span("text2", 0, "text2".length() - 1 /* remove 2 */);

    Assert.assertTrue(span2.compareTo(span1) < 0);
  }

  @Test
  public void testCompareToGT() {

    Span span1 = new Span("text1", 0, "text1".length());
    Span span2 = new Span("text2", 0, "text2".length() - 1 /* remove 2 */);

    Assert.assertTrue(span1.compareTo(span2) > 0);
  }

  @Test
  public void testCompareToEQ() {

    Span span1 = new Span("text1", 0, "text1".length() - 1 /* remove 1 */);
    Span span2 = new Span("text2", 0, "text2".length() - 1 /* remove 2 */);

    Assert.assertTrue(span1.compareTo(span2) == 0);
  }

  @Test
  public void testRawText() {

    Span span1 = new Span("text", 0, 2);
    Span span2 = new Span("text", 2, 4);

    Assert.assertEquals("te", span1.text());
    Assert.assertEquals("xt", span2.text());
    Assert.assertEquals(span1.rawText(), span2.rawText());
  }

  @Test
  public void testText() {

    Span span1 = new Span("text1", 0, "text1".length() - 1);
    Span span2 = new Span("text2", 0, "text2".length() - 1);

    Assert.assertEquals(span1.text(), span2.text());
  }

  @Test
  public void testCopySpanWithFeatures() {

    Span span1 = new Span("text1", 0, "text1".length() - 1);
    span1.setFeature("key1", "value1");
    span1.setFeature("key2", "value2");

    Span span2 = new Span(span1, true);

    Assert.assertEquals(span1, span2);
  }

  @Test
  public void testCopySpanWithoutFeatures() {

    Span span1 = new Span("text1", 0, "text1".length() - 1);
    span1.setFeature("key1", "value1");
    span1.setFeature("key2", "value2");

    Span span2 = new Span(span1, false);

    Assert.assertNotEquals(span1, span2);

    Assert.assertEquals(2, span1.features().size());
    Assert.assertTrue(span1.hasFeature("key1"));
    Assert.assertTrue(span1.hasFeature("key2"));

    Assert.assertEquals(0, span2.features().size());
    Assert.assertFalse(span2.hasFeature("key1"));
    Assert.assertFalse(span2.hasFeature("key2"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetFeatureWithNullKey() {
    Span span = new Span("text", 0, "text".length());
    span.setFeature(null, "value");
  }

  @Test(expected = NullPointerException.class)
  public void testSetFeatureWithNullValue() {
    Span span = new Span("text", 0, "text".length());
    span.setFeature("key", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetFeatureWithEmptyKey() {
    Span span = new Span("text", 0, "text".length());
    span.setFeature("", "value");
  }

  @Test
  public void testSetFeatureWithEmptyValue() {

    Span span = new Span("text", 0, "text".length());
    span.setFeature("key", "");

    Assert.assertTrue(span.hasFeature("key"));
  }

  @Test
  public void testRemoveFeature() {

    Span span = new Span("text", 0, "text".length());
    span.setFeature("key1", "f1");
    span.setFeature("key2", "f2");

    Assert.assertEquals(2, span.features().size());
    Assert.assertTrue(span.hasFeature("key1"));
    Assert.assertTrue(span.hasFeature("key2"));
    Assert.assertEquals("f1", span.getFeature("key1"));
    Assert.assertEquals("f2", span.getFeature("key2"));

    span.removeFeature("key1");

    Assert.assertEquals(1, span.features().size());
    Assert.assertFalse(span.hasFeature("key1"));
    Assert.assertTrue(span.hasFeature("key2"));
    Assert.assertEquals("f2", span.getFeature("key2"));
  }

  @Test
  public void testRemoveAllFeatures() {

    Span span = new Span("text", 0, "text".length());
    span.setFeature("key1", "f1");
    span.setFeature("key2", "f2");

    Assert.assertEquals(2, span.features().size());
    Assert.assertTrue(span.hasFeature("key1"));
    Assert.assertTrue(span.hasFeature("key2"));
    Assert.assertEquals("f1", span.getFeature("key1"));
    Assert.assertEquals("f2", span.getFeature("key2"));

    span.removeFeatures();

    Assert.assertEquals(0, span.features().size());
    Assert.assertFalse(span.hasFeature("key2"));
    Assert.assertFalse(span.hasFeature("key2"));
  }

  @Test
  public void testSetNullTag() {

    Span span = new Span("text", 0, "text".length());
    span.addTag(null);

    Assert.assertTrue(span.tags().isEmpty());
  }

  @Test
  public void testSetEmptyTag() {

    Span span = new Span("text", 0, "text".length());
    span.addTag("");

    Assert.assertTrue(span.tags().isEmpty());
  }

  @Test
  public void testRemoveTag() {

    Span span = new Span("text", 0, "text".length());
    span.addTag("tag1");
    span.addTag("tag2");

    Assert.assertEquals(2, span.tags().size());
    Assert.assertTrue(span.hasTag("tag1"));
    Assert.assertTrue(span.hasTag("tag2"));

    span.removeTag("tag1");

    Assert.assertEquals(1, span.tags().size());
    Assert.assertFalse(span.hasTag("tag1"));
    Assert.assertTrue(span.hasTag("tag2"));
  }

  @Test
  public void testRemoveAllTags() {

    Span span = new Span("text", 0, "text".length());
    span.addTag("tag1");
    span.addTag("tag2");

    Assert.assertEquals(2, span.tags().size());
    Assert.assertTrue(span.hasTag("tag1"));
    Assert.assertTrue(span.hasTag("tag2"));

    span.removeTags();

    Assert.assertEquals(0, span.tags().size());
    Assert.assertFalse(span.hasTag("tag2"));
    Assert.assertFalse(span.hasTag("tag2"));
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
  public void testOverlapsSomePosition() {

    Span span4567 = new Span("123456789", 3, 7);

    Assert.assertFalse(span4567.overlapsSome(0));
    Assert.assertFalse(span4567.overlapsSome(1));
    Assert.assertFalse(span4567.overlapsSome(2));
    Assert.assertTrue(span4567.overlapsSome(3));
    Assert.assertTrue(span4567.overlapsSome(4));
    Assert.assertTrue(span4567.overlapsSome(5));
    Assert.assertTrue(span4567.overlapsSome(6));
    Assert.assertFalse(span4567.overlapsSome(7));
    Assert.assertFalse(span4567.overlapsSome(8));
  }

  @Test
  public void testOverlapsSome() {

    Span span4567 = new Span("123456789", 3, 7);
    Span span345 = new Span("123456789", 2, 5);
    Span span678 = new Span("123456789", 5, 8);
    Span span56 = new Span("123456789", 4, 6);
    Span span89 = new Span("123456789", 7, 9);

    Assert.assertTrue(span4567.overlapsSome(span345));
    Assert.assertTrue(span345.overlapsSome(span4567));

    Assert.assertFalse(span4567.overlapsAll(span345));
    Assert.assertFalse(span345.overlapsAll(span4567));

    Assert.assertTrue(span4567.overlapsSome(span678));
    Assert.assertTrue(span678.overlapsSome(span4567));

    Assert.assertFalse(span4567.overlapsAll(span678));
    Assert.assertFalse(span678.overlapsAll(span4567));

    Assert.assertTrue(span4567.overlapsSome(span56));
    Assert.assertTrue(span56.overlapsSome(span4567));

    Assert.assertTrue(span4567.overlapsAll(span56));
    Assert.assertFalse(span56.overlapsAll(span4567));

    Assert.assertFalse(span4567.overlapsSome(span89));
    Assert.assertFalse(span89.overlapsSome(span4567));

    Assert.assertFalse(span4567.overlapsAll(span89));
    Assert.assertFalse(span89.overlapsAll(span4567));

    Assert.assertTrue(span4567.overlapsSome(span4567));
    Assert.assertTrue(span4567.overlapsAll(span4567));
  }

  @Test
  public void testOverlapsLeftOf() {

    Span span4567 = new Span("123456789", 3, 7);
    Span span345 = new Span("123456789", 2, 5);
    Span span567 = new Span("123456789", 4, 7);

    Assert.assertTrue(span4567.overlapsSome(span345));
    Assert.assertTrue(span345.overlapsSome(span4567));

    Assert.assertTrue(span4567.overlapsRightOf(span345));
    Assert.assertTrue(span345.overlapsLeftOf(span4567));

    Assert.assertTrue(span345.overlapsSome(span567));
    Assert.assertTrue(span567.overlapsSome(span345));

    Assert.assertTrue(span345.overlapsLeftOf(span567));
    Assert.assertTrue(span567.overlapsRightOf(span345));
  }

  @Test
  public void testOverlapsRightOf() {

    Span span4567 = new Span("123456789", 3, 7);
    Span span678 = new Span("123456789", 5, 8);
    Span span456 = new Span("123456789", 3, 6);

    Assert.assertTrue(span4567.overlapsSome(span678));
    Assert.assertTrue(span678.overlapsSome(span4567));

    Assert.assertTrue(span4567.overlapsLeftOf(span678));
    Assert.assertTrue(span678.overlapsRightOf(span4567));

    Assert.assertTrue(span678.overlapsSome(span456));
    Assert.assertTrue(span456.overlapsSome(span678));

    Assert.assertTrue(span678.overlapsRightOf(span456));
    Assert.assertTrue(span456.overlapsLeftOf(span678));
  }

  @Test
  public void testOverlapsAll() {

    Span span4567 = new Span("123456789", 3, 7);
    Span span56 = new Span("123456789", 4, 6);

    Assert.assertFalse(span4567.overlapsLeftOf(span56));
    Assert.assertFalse(span4567.overlapsRightOf(span56));

    Assert.assertFalse(span56.overlapsLeftOf(span4567));
    Assert.assertFalse(span56.overlapsRightOf(span4567));

    Assert.assertTrue(span4567.overlapsSome(span56));
    Assert.assertTrue(span56.overlapsSome(span4567));

    Assert.assertTrue(span4567.overlapsAll(span56));
    Assert.assertFalse(span56.overlapsAll(span4567));
  }
}
