package com.computablefacts.nona.functions.patternoperators;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class WordWithApostrophesOrDashesTest {

  @Test
  public void testWordWithApostrophe() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("WORD_WITH_APOSTROPHES_OR_DASHES", new WordWithApostrophesOrDashes());

    Function fn = new Function("WORD_WITH_APOSTROPHES_OR_DASHES("
        + Function.wrap("He doesn't like celery in a salad.") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(functions).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("doesn't", span.text());
  }

  @Test
  public void testWordWithApostropheAtTheEnd() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("WORD_WITH_APOSTROPHES_OR_DASHES", new WordWithApostrophesOrDashes());

    Function fn = new Function(
        "WORD_WITH_APOSTROPHES_OR_DASHES(" + Function.wrap("Ms. Peters’ house.") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(functions).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("Peters’", span.text());
  }

  @Test
  public void testWordWithDash() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("WORD_WITH_APOSTROPHES_OR_DASHES", new WordWithApostrophesOrDashes());

    Function fn = new Function("WORD_WITH_APOSTROPHES_OR_DASHES("
        + Function.wrap("Rock-forming minerals are minerals that form rocks.") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(functions).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("Rock-forming", span.text());
  }

  @Test
  public void testWordWithApostropheAndDash() {

    Map<String, Function> functions = new HashMap<>();
    functions.put("WORD_WITH_APOSTROPHES_OR_DASHES", new WordWithApostrophesOrDashes());

    Function fn = new Function("WORD_WITH_APOSTROPHES_OR_DASHES("
        + Function.wrap("My brother-in-law’s house is down the block.") + ")");
    SpanSequence spans = (SpanSequence) fn.evaluate(functions).value();
    Span span = spans.span(0);

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("brother-in-law’s", span.text());
  }
}
