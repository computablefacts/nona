package com.computablefacts.nona.functions.patternoperators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class DateTimeTest {

  @Test
  public void testDateTimePositiveTimezone() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("DTIME", new DateTime());

    Function fn = new Function("DTIME(1997-07-16T19:20:30+01:00)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();
    Span span = spans.get(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("1997-07-16T19:20:30+01:00", span.text());
    Assert.assertEquals("1997-07-16", span.getFeature("DATE"));
    Assert.assertEquals("19:20:30", span.getFeature("TIME"));
    Assert.assertEquals("+01:00", span.getFeature("TIMEZONE"));
  }

  @Test
  public void testDateTimeNegativeTimezone() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("DTIME", new DateTime());

    Function fn = new Function("DTIME(1994-11-05T08:15:30-05:00)");
    List<Span> spans = ((SpanSequence) fn.evaluate(functions).value()).sequence();
    Span span = spans.get(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("1994-11-05T08:15:30-05:00", span.text());
    Assert.assertEquals("1994-11-05", span.getFeature("DATE"));
    Assert.assertEquals("08:15:30", span.getFeature("TIME"));
    Assert.assertEquals("-05:00", span.getFeature("TIMEZONE"));
  }
}
