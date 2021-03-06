package com.computablefacts.nona.functions.stringoperators;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.Function;
import com.computablefacts.nona.types.BoxedType;
import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class MatchRegexTest {

  @Test
  public void testExtractPointInTime() {

    String text = "THE GUATEMALA ARMY ATTACKED THE FARM 2 DAYS AGO";

    Span span = new Span(text, 37, 47);
    span.setGroup(1, "2");

    SpanSequence sequence = new SpanSequence();
    sequence.add(span);

    Function fn = new Function("MATCH_REGEX(" + text + ", _((?is:([1-9][0-9]?)\\s+days\\s+ago)))");
    Assert.assertEquals(BoxedType.create(sequence), fn.evaluate(Function.definitions()));
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

    Function fn = new Function("MATCH_REGEX(_(" + text
        + "), _((?is:([0-9]{1,2})\\s+(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)\\s+([0-9]{2}))))");
    Assert.assertEquals(BoxedType.create(sequence), fn.evaluate(Function.definitions()));
  }
}
