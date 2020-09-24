package com.computablefacts.nona.functions.patternoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class Ipv6Test {

  @Test
  public void testIpv6FullForm() {

    Function fn = new Function("MATCH_IPV6(fe80:0000:0000:0000:0204:61ff:fe9d:f156)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("fe80:0000:0000:0000:0204:61ff:fe9d:f156", span.text());
  }

  @Test
  public void testIpv6DropLeadingZeroes() {

    Function fn = new Function("MATCH_IPV6(fe80:0:0:0:204:61ff:fe9d:f156)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("fe80:0:0:0:204:61ff:fe9d:f156", span.text());
    Assert.assertTrue(span.hasTag("IPV6"));
  }

  @Test
  public void testIpv6CollapseMultipleZeroes() {

    Function fn = new Function("MATCH_IPV6(fe80::204:61ff:fe9d:f156)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("fe80::204:61ff:fe9d:f156", span.text());
    Assert.assertTrue(span.hasTag("IPV6"));
  }
}
