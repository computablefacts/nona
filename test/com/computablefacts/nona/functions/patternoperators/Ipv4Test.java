package com.computablefacts.nona.functions.patternoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.nona.Function;

public class Ipv4Test {

  @Test
  public void testIpv4NonLocal() {

    Function fn = new Function("MATCH_IPV4(100.1.2.3)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("100.1.2.3", span.text());
    Assert.assertTrue(span.hasTag("IPV4"));
    Assert.assertEquals("false", span.getFeature("IS_LOCAL"));
  }

  @Test
  public void testIpv4Local() {

    Function fn = new Function("MATCH_IPV4(127.0.0.1)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("127.0.0.1", span.text());
    Assert.assertTrue(span.hasTag("IPV4"));
    Assert.assertEquals("true", span.getFeature("IS_LOCAL"));
  }
}
