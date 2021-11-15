package com.computablefacts.nona.functions.patternoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.nona.Function;

public class WordWithApostrophesOrDashesTest {

  @Test
  public void testWordWithApostrophe() {

    Function fn = new Function("MATCH_WORD_WITH_APOSTROPHES_OR_DASHES("
        + Function.wrap("He doesn't like celery in a salad.") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("doesn't", span.text());
  }

  @Test
  public void testWordWithApostropheAtTheEnd() {

    Function fn = new Function(
        "MATCH_WORD_WITH_APOSTROPHES_OR_DASHES(" + Function.wrap("Ms. Peters’ house.") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("Peters’", span.text());
  }

  @Test
  public void testWordWithDash() {

    Function fn = new Function("MATCH_WORD_WITH_APOSTROPHES_OR_DASHES("
        + Function.wrap("Rock-forming minerals are minerals that form rocks.") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("Rock-forming", span.text());
  }

  @Test
  public void testWordWithApostropheAndDash() {

    Function fn = new Function("MATCH_WORD_WITH_APOSTROPHES_OR_DASHES("
        + Function.wrap("My brother-in-law’s house is down the block.") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("brother-in-law’s", span.text());
  }
}
