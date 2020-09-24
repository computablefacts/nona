package com.computablefacts.nona.functions.patternoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class MonetaryAmountTest {

  @Test
  public void testMonetaryAmounts1() {

    Function fn = new Function("MATCH_MONETARY_AMOUNT(600 €)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("600 €", span.text());
    Assert.assertTrue(span.hasTag("MONETARY_AMOUNT"));
    Assert.assertEquals("600", span.getFeature("AMOUNT"));
    Assert.assertEquals("€", span.getFeature("CURRENCY"));
  }

  @Test
  public void testMonetaryAmounts2() {

    Function fn = new Function("MATCH_MONETARY_AMOUNT(1 933.40 €)");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("1 933.40 €", span.text());
    Assert.assertTrue(span.hasTag("MONETARY_AMOUNT"));
    Assert.assertEquals("1 933.40", span.getFeature("AMOUNT"));
    Assert.assertEquals("€", span.getFeature("CURRENCY"));
  }

  @Test
  public void testMonetaryAmounts3() {

    Function fn = new Function("MATCH_MONETARY_AMOUNT(" + Function.wrap("$10,590.98") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("$10,590.98", span.text());
    Assert.assertTrue(span.hasTag("MONETARY_AMOUNT"));
    Assert.assertEquals("10,590.98", span.getFeature("AMOUNT"));
    Assert.assertEquals("$", span.getFeature("CURRENCY"));
  }
}
