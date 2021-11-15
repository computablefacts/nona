package com.computablefacts.nona.functions.patternoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.nona.Function;

public class TimeTest {

  @Test
  public void testTime() {

    Function fn = new Function("MATCH_TIME(19h20mn30s)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("19h20mn30s", span.text());
    Assert.assertTrue(span.hasTag("TIME"));
  }
}
