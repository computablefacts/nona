package com.computablefacts.nona.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.asterix.codecs.JsonCodec;
import com.google.common.collect.Lists;

import nl.jqno.equalsverifier.EqualsVerifier;

public class JsonTest {

  @Test
  public void testHashcodeAndEquals() {
    EqualsVerifier.forClass(Json.class).verify();
  }

  @Test
  public void testParseArray() {

    Json json = Json.create(JsonCodec.asString(array()));

    Assert.assertEquals(2, json.nbObjects());

    Assert.assertEquals(json1(), json.object(0));
    Assert.assertEquals(1, json.value(0, "id"));
    Assert.assertEquals("john", json.value(0, "first_name"));
    Assert.assertEquals("doe", json.value(0, "last_name"));
    Assert.assertEquals(17, json.value(0, "age"));
    Assert.assertEquals("2004-04-01T00:00:00Z", json.value(0, "birthdate"));

    Assert.assertEquals(json2(), json.object(1));
    Assert.assertEquals(2, json.value(1, "id"));
    Assert.assertEquals("jane", json.value(1, "first_name"));
    Assert.assertEquals("doe", json.value(1, "last_name"));
    Assert.assertEquals(18, json.value(1, "age"));
    Assert.assertEquals("2003-04-01T00:00:00Z", json.value(1, "birthdate"));
  }

  @Test
  public void testParseObject() {

    Json json = Json.create(JsonCodec.asString(json1()));

    Assert.assertEquals(1, json.nbObjects());
    Assert.assertEquals(json1(), json.object(0));
    Assert.assertEquals(1, json.value(0, "id"));
    Assert.assertEquals("john", json.value(0, "first_name"));
    Assert.assertEquals("doe", json.value(0, "last_name"));
    Assert.assertEquals(17, json.value(0, "age"));
    Assert.assertEquals("2004-04-01T00:00:00Z", json.value(0, "birthdate"));
  }

  @Test
  public void testParseEmptyJson() {

    Json json = Json.create(JsonCodec.asString(new HashMap<>()));

    Assert.assertEquals(0, json.nbObjects());
  }

  @Test
  public void testJsonToText() {

    Json json = Json.create(JsonCodec.asString(json1()));

    Assert.assertEquals(JsonCodec.asString(json1()), json.asString());
  }

  private List<Map<String, Object>> array() {
    return Lists.newArrayList(json1(), json2());
  }

  private Map<String, Object> json1() {
    Map<String, Object> json = new HashMap<>();
    json.put("id", 1);
    json.put("first_name", "john");
    json.put("last_name", "doe");
    json.put("age", 17);
    json.put("birthdate", "2004-04-01T00:00:00Z");
    return json;
  }

  private Map<String, Object> json2() {
    Map<String, Object> json = new HashMap<>();
    json.put("id", 2);
    json.put("first_name", "jane");
    json.put("last_name", "doe");
    json.put("age", 18);
    json.put("birthdate", "2003-04-01T00:00:00Z");
    return json;
  }
}
