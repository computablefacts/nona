package com.computablefacts.nona.functions.patternoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.nona.Function;

public class WordWithoutApostrophesOrDashesTest {

  @Test
  public void testWordWithApostrophe() {

    Function fn = new Function("MATCH_WORD_WITHOUT_APOSTROPHES_OR_DASHES("
        + Function.wrap("He doesn't like celery in a salad.") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();

    Assert.assertEquals(8, spans.size());
    Assert.assertEquals("He", spans.span(0).text());
    Assert.assertEquals("doesn", spans.span(1).text());
    Assert.assertEquals("t", spans.span(2).text());
    Assert.assertEquals("like", spans.span(3).text());
    Assert.assertEquals("celery", spans.span(4).text());
    Assert.assertEquals("in", spans.span(5).text());
    Assert.assertEquals("a", spans.span(6).text());
    Assert.assertEquals("salad", spans.span(7).text());
  }

  @Test
  public void testWordWithApostropheAtTheEnd() {

    Function fn = new Function(
        "MATCH_WORD_WITHOUT_APOSTROPHES_OR_DASHES(" + Function.wrap("Ms. Peters’ house.") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();

    Assert.assertEquals(3, spans.size());
    Assert.assertEquals("Ms", spans.span(0).text());
    Assert.assertEquals("Peters", spans.span(1).text());
    Assert.assertEquals("house", spans.span(2).text());
  }

  @Test
  public void testWordWithDash() {

    Function fn = new Function("MATCH_WORD_WITHOUT_APOSTROPHES_OR_DASHES("
        + Function.wrap("Rock-forming minerals are minerals that form rocks.") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();

    Assert.assertEquals(8, spans.size());
    Assert.assertEquals("Rock", spans.span(0).text());
    Assert.assertEquals("forming", spans.span(1).text());
    Assert.assertEquals("minerals", spans.span(2).text());
    Assert.assertEquals("are", spans.span(3).text());
    Assert.assertEquals("minerals", spans.span(4).text());
    Assert.assertEquals("that", spans.span(5).text());
    Assert.assertEquals("form", spans.span(6).text());
    Assert.assertEquals("rocks", spans.span(7).text());
  }

  @Test
  public void testWordWithApostropheAndDash() {

    Function fn = new Function("MATCH_WORD_WITHOUT_APOSTROPHES_OR_DASHES("
        + Function.wrap("My brother-in-law’s house is down the block.") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(Function.definitions()).value();

    Assert.assertEquals(10, spans.size());
    Assert.assertEquals("My", spans.span(0).text());
    Assert.assertEquals("brother", spans.span(1).text());
    Assert.assertEquals("in", spans.span(2).text());
    Assert.assertEquals("law", spans.span(3).text());
    Assert.assertEquals("s", spans.span(4).text());
    Assert.assertEquals("house", spans.span(5).text());
    Assert.assertEquals("is", spans.span(6).text());
    Assert.assertEquals("down", spans.span(7).text());
    Assert.assertEquals("the", spans.span(8).text());
    Assert.assertEquals("block", spans.span(9).text());
  }
}
