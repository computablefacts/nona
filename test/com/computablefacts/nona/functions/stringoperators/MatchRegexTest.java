package com.computablefacts.nona.functions.stringoperators;

import com.computablefacts.asterix.BoxedType;
import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;
import com.computablefacts.nona.Function;
import org.junit.Assert;
import org.junit.Test;

public class MatchRegexTest {

  @Test
  public void testExtractPointInTime() {

    String text = "THE GUATEMALA ARMY ATTACKED THE FARM 2 DAYS AGO";

    Span span = new Span(text, 37, 47);
    span.setGroup(1, "2");

    SpanSequence sequence = new SpanSequence();
    sequence.add(span);

    String textWrapped = Function.wrap(text);
    String regexWrapped = Function.wrap("(?is:([1-9][0-9]?)\\s+days\\s+ago)");
    String function = String.format("MATCH_REGEX(%s, %s)", textWrapped, regexWrapped);

    Function fn = new Function(function);
    Assert.assertEquals(BoxedType.of(sequence), fn.evaluate(Function.definitions()));
  }

  @Test
  public void testExtractIsoDatePattern() {

    String text = "GUATEMALA CITY, 4 FEB 90 (ACAN-EFE)";
    Span span = new Span(text, 16, 24);
    span.setGroup(1, "4");
    span.setGroup(2, "FEB");
    span.setGroup(3, "90");

    SpanSequence sequence = new SpanSequence();
    sequence.add(span);

    String textWrapped = Function.wrap(text);
    String regexWrapped = Function.wrap(
        "(?is:([0-9]{1,2})\\s+(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)\\s+([0-9]{2}))");
    String function = String.format("MATCH_REGEX(%s, %s)", textWrapped, regexWrapped);

    Function fn = new Function(function);
    Assert.assertEquals(BoxedType.of(sequence), fn.evaluate(Function.definitions()));
  }
}
