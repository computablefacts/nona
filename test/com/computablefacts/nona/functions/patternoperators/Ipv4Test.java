package com.computablefacts.nona.functions.patternoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class Ipv4Test {

  @Test
  public void testIpv4NonLocal() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IPV4", new IpV4());

    Function fn = new Function("IPV4(100.1.2.3)");
    SpanSequence spans = (SpanSequence) fn.evaluate(functions).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("100.1.2.3", span.text());
    Assert.assertTrue(span.hasTag("IPV4"));
    Assert.assertEquals("false", span.getFeature("IS_LOCAL"));
  }

  @Test
  public void testIpv4Local() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IPV4", new IpV4());

    Function fn = new Function("IPV4(127.0.0.1)");
    SpanSequence spans = (SpanSequence) fn.evaluate(functions).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("127.0.0.1", span.text());
    Assert.assertTrue(span.hasTag("IPV4"));
    Assert.assertEquals("true", span.getFeature("IS_LOCAL"));
  }
}
