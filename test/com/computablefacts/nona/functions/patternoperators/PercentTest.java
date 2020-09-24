package com.computablefacts.nona.functions.patternoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class PercentTest {

  @Test
  public void testPercent() {

    Function fn = new Function("MATCH_PERCENT(11.1%)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("11.1%", span.text());
    Assert.assertTrue(span.hasTag("PERCENT"));
  }

  @Test
  public void testPercentFromText() {

    Function fn = new Function("MATCH_PERCENT(" + Function.wrap(
        "In June 2017, its return on invested capital was 11.1%, followed by Goldman Sachs' 9.5%, and JPMorgan Chase's 9.2%.")
        + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();

    Assert.assertEquals(3, spans.size());
    Assert.assertEquals("11.1%", spans.span(0).text());
    Assert.assertEquals("9.5%", spans.span(1).text());
    Assert.assertEquals("9.2%", spans.span(2).text());
  }
}
