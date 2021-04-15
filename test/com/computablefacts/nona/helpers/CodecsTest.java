package com.computablefacts.nona.helpers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class CodecsTest {

  @Test(expected = NullPointerException.class)
  public void testDeserializeNullEncoder() {
    String b64 = Codecs.decodeB64(null, "");
  }

  @Test(expected = NullPointerException.class)
  public void testDeserializeNullString() {
    String b64 = Codecs.decodeB64(Base64.getDecoder(), null);
  }

  @Test
  public void testDeserializeEmptyString() {
    Assert.assertEquals("", Codecs.decodeB64(Base64.getDecoder(), ""));
  }

  @Test(expected = NullPointerException.class)
  public void testSerializeNullEncoder() {
    String b64 = Codecs.encodeB64(null, "");
  }

  @Test
  public void testSerializeNullString() {
    Assert.assertEquals("", Codecs.encodeB64(Base64.getEncoder(), null));
  }

  @Test
  public void testSerializeEmptyString() {
    Assert.assertEquals("", Codecs.encodeB64(Base64.getEncoder(), ""));
  }

  @Test
  public void testEncodeDecode() {

    String b64 = Codecs.encodeB64(Base64.getEncoder(), "test");
    String string = Codecs.decodeB64(Base64.getDecoder(), "dGVzdA==");

    Assert.assertTrue(Codecs.isProbablyBase64(b64));
    Assert.assertEquals("dGVzdA==", b64);
    Assert.assertEquals("test", string);
  }

  @Test
  public void testIsProbablyBase64NullString() {
    assertFalse(Codecs.isProbablyBase64(null));
  }

  @Test
  public void testIsProbablyBase64EmptyString() {
    assertFalse(Codecs.isProbablyBase64(""));
  }

  @Test
  public void testIsProbablyBase64() {

    String text =
        "uO3lZkI9fkWmzqM3QQuBCB6XhargnehMptMRKoZQxmNDSlMYi8fBv1M7ATIpdFvQaa/MyzTbYhmeLgrCxqMIlmLDLgHG3fkVe/0Vr7eulqemWjZEJABbpLoIHjtduuzioHzyJANZQZXL9MSvADGZk3RDX6cuE8rvV5x+il1GR5PGFNq4NdFRCYm4PxBcM1XKl2b0CkvIPAY/jJoYM2hWDv9OPP5LKhzFKyNdWT6dVU+wqDInfEHqX7y2DAp+i2bhu0ZJItJmZa6tSe/XUZ/pGt/x5vy6ffXm850a3Gg6o0CwuY0tzcz+6nY0rrswbju5l2YgWb7b4Guu87gz+GLWzw==";

    assertTrue(Codecs.isProbablyBase64(text));
  }

  @Test
  public void testIsNotBase64() {

    String text = "====";

    assertFalse(Codecs.isProbablyBase64(text));
  }

  @Test
  public void testDeserializeNullObject() {
    Assert.assertNotNull(Codecs.asObject((String) null));
  }

  @Test
  public void testDeserializeNullCollection() {
    Assert.assertNotNull(Codecs.asCollection((String) null));
  }

  @Test
  public void testDeserializeNullArray() {
    Assert.assertNotNull(Codecs.asArray((String) null));
  }

  @Test
  public void testDeserializeEmptyObject() {
    Assert.assertNotNull(Codecs.asObject("{}"));
  }

  @Test
  public void testDeserializeEmptyCollection() {
    Assert.assertNotNull(Codecs.asCollection("[]"));
  }

  @Test
  public void testDeserializeEmptyArray() {
    Assert.assertNotNull(Codecs.asArray("[]"));
  }

  @Test
  public void testSerializeNullObject() {
    Assert.assertEquals("{}", Codecs.asString((Map) null));
  }

  @Test
  public void testSerializeNullCollection() {
    Assert.assertEquals("[]", Codecs.asString((List) null));
  }

  @Test
  public void testSerializeNullArray() {
    Assert.assertEquals("[]", Codecs.asString((Map[]) null));
  }

  @Test
  public void testSerializeEmptyObject() {
    Assert.assertEquals("{}", Codecs.asString(new HashMap<>()));
  }

  @Test
  public void testSerializeEmptyCollection() {
    Assert.assertEquals("[]", Codecs.asString(new ArrayList<>()));
  }

  @Test
  public void testSerializeEmptyArray() {
    Assert.assertEquals("[]", Codecs.asString(new Map[0]));
  }

  @Test
  public void testMapFromNullObject() {
    Assert.assertEquals(Collections.emptyMap(), Codecs.asMap((Map) null));
  }

  @Test
  public void testCollectionOfMapsFromNullCollection() {
    Assert.assertEquals(Collections.emptyList(), Codecs.asCollectionOfMaps((List) null));
  }

  @Test
  public void testCollectionOfMapsFromNullArray() {
    Assert.assertEquals(Collections.emptyList(), Codecs.asCollectionOfMaps((Map[]) null));
  }

  @Test
  public void testMapFromEmptyObject() {
    Assert.assertEquals(Collections.emptyMap(), Codecs.asMap(new HashMap<>()));
  }

  @Test
  public void testCollectionOfMapsFromEmptyCollection() {
    Assert.assertEquals(Collections.emptyList(), Codecs.asCollectionOfMaps(new ArrayList<>()));
  }

  @Test
  public void testCollectionOfMapsFromEmptyArray() {
    Assert.assertEquals(Collections.emptyList(), Codecs.asCollectionOfMaps(new Map[0]));
  }

  @Test
  public void testSimpleCollection() {

    List<SimplePojo<?>> pojos = Lists.newArrayList(new SimplePojo<>("key1", "value1"),
        new SimplePojo<>("key2", 2), new SimplePojo<>("key3", false));
    String json = Codecs.asString(pojos);

    Assert.assertFalse(Strings.isNullOrEmpty(json));

    Collection<Map<String, Object>> collection1 = Codecs.asCollection(json);
    Collection<Map<String, Object>> collection2 = Codecs.asCollection(
        "[{\"key\":\"key1\",\"value\":\"value1\"},{\"key\":\"key2\",\"value\":2},{\"key\":\"key3\",\"value\":false}]");

    Assert.assertEquals(collection1, collection2);
  }

  @Test
  public void testSerializeDeserializeDate() throws ParseException {

    Map<String, Object> map1 =
        ImmutableMap.of("date", Date.from(Instant.parse("2004-04-01T00:00:00Z")));

    Map<String, Object> map2 = ImmutableMap.of("date", "2004-04-01T00:00:00Z");

    String json1 = Codecs.asString(map1);
    String json2 = Codecs.asString(map2);

    Assert.assertEquals("{\"date\":\"2004-04-01T00:00:00Z\"}", json1);
    Assert.assertEquals("{\"date\":\"2004-04-01T00:00:00Z\"}", json2);

    Assert.assertNotEquals(map1, Codecs.asObject(json1));
    Assert.assertNotEquals(map1, Codecs.asObject(json2));

    Assert.assertEquals(map2, Codecs.asObject(json1));
    Assert.assertEquals(map2, Codecs.asObject(json2));
  }

  @Test
  public void testSplitOnNewline() {

    SpanSequence sequence = Codecs.defaultTokenizer.apply("Tom\n\nCruise");

    Assert.assertEquals(2, sequence.size());
    Assert.assertEquals(new Span("tom\n\ncruise", 0, 3), sequence.span(0));
    Assert.assertEquals(new Span("tom\n\ncruise", 5, 11), sequence.span(1));
  }

  @Test
  public void testSplitOnTab() {

    SpanSequence sequence = Codecs.defaultTokenizer.apply("Tom\t\tCruise");

    Assert.assertEquals(2, sequence.size());
    Assert.assertEquals(new Span("tom\t\tcruise", 0, 3), sequence.span(0));
    Assert.assertEquals(new Span("tom\t\tcruise", 5, 11), sequence.span(1));
  }

  @Test
  public void testSplitOnCarriageReturn() {

    SpanSequence sequence = Codecs.defaultTokenizer.apply("Tom\r\rCruise");

    Assert.assertEquals(2, sequence.size());
    Assert.assertEquals(new Span("tom\r\rcruise", 0, 3), sequence.span(0));
    Assert.assertEquals(new Span("tom\r\rcruise", 5, 11), sequence.span(1));
  }

  @Test
  public void testSplitOnWhitespace() {

    SpanSequence sequence = Codecs.defaultTokenizer.apply("Tom  Cruise");

    Assert.assertEquals(2, sequence.size());
    Assert.assertEquals(new Span("tom  cruise", 0, 3), sequence.span(0));
    Assert.assertEquals(new Span("tom  cruise", 5, 11), sequence.span(1));
  }

  @Test
  public void testSplitOnNoBreakSpace() {

    SpanSequence sequence = Codecs.defaultTokenizer.apply("Tom\u00a0\u00a0Cruise");

    Assert.assertEquals(2, sequence.size());
    Assert.assertEquals(new Span("tom  cruise", 0, 3), sequence.span(0));
    Assert.assertEquals(new Span("tom  cruise", 5, 11), sequence.span(1));
  }

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private static class SimplePojo<T> {

    @JsonProperty("key")
    public final String key_;

    @JsonProperty("value")
    public final T value_;

    public SimplePojo(String key, T value) {
      key_ = key;
      value_ = value;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (!(o instanceof SimplePojo)) {
        return false;
      }
      SimplePojo pojo = (SimplePojo) o;
      return Objects.equals(key_, pojo.key_) && Objects.equals(value_, pojo.value_);
    }

    @Override
    public int hashCode() {
      return Objects.hash(key_, value_);
    }
  }
}
