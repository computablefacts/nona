package com.computablefacts.nona.functions.patternoperators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class TimeTest {

  @Test
  public void testTime() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("TIME", new Time());

    Function fn = new Function("TIME(19h20mn30s)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();
    Span span = spans.get(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("19h20mn30s", span.text());
    Assert.assertTrue(span.hasTag("TIME"));
  }
}
