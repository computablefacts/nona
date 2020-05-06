package com.computablefacts.nona.functions.patternoperators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class Ipv6Test {

  @Test
  public void testIpv6FullForm() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("EIPV6", new IpV6());

    Function fn = new Function("EIPV6(fe80:0000:0000:0000:0204:61ff:fe9d:f156)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();
    Span span = spans.get(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("fe80:0000:0000:0000:0204:61ff:fe9d:f156", span.text());
  }

  @Test
  public void testIpv6DropLeadingZeroes() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IPV6", new IpV6());

    Function fn = new Function("IPV6(fe80:0:0:0:204:61ff:fe9d:f156)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();
    Span span = spans.get(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("fe80:0:0:0:204:61ff:fe9d:f156", span.text());
  }

  @Test
  public void testIpv6CollapseMultipleZeroes() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("IPV6", new IpV6());

    Function fn = new Function("IPV6(fe80::204:61ff:fe9d:f156)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();
    Span span = spans.get(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("fe80::204:61ff:fe9d:f156", span.text());
  }
}
