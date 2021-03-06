package com.computablefacts.nona.functions.patternoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class DateTest {

  @Test
  public void testDate() {

    Function fn = new Function("MATCH_DATE(16/07/1997)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertTrue(span.hasTag("DATE"));
    Assert.assertEquals("16/07/1997", span.text());
  }
}
