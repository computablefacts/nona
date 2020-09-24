package com.computablefacts.nona.functions.patternoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class DateTimeTest {

  @Test
  public void testDateTimePositiveTimezone() {

    Function fn = new Function("MATCH_DATE_TIME(1997-07-16T19:20:30+01:00)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertTrue(span.hasTag("DATE_TIME"));
    Assert.assertEquals("1997-07-16T19:20:30+01:00", span.text());
    Assert.assertEquals("1997-07-16", span.getFeature("DATE"));
    Assert.assertEquals("19:20:30", span.getFeature("TIME"));
    Assert.assertEquals("+01:00", span.getFeature("TIMEZONE"));
  }

  @Test
  public void testDateTimeNegativeTimezone() {

    Function fn = new Function("MATCH_DATE_TIME(1994-11-05T08:15:30-05:00)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertTrue(span.hasTag("DATE_TIME"));
    Assert.assertEquals("1994-11-05T08:15:30-05:00", span.text());
    Assert.assertEquals("1994-11-05", span.getFeature("DATE"));
    Assert.assertEquals("08:15:30", span.getFeature("TIME"));
    Assert.assertEquals("-05:00", span.getFeature("TIMEZONE"));
  }
}
