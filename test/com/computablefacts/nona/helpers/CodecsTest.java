package com.computablefacts.nona.helpers;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;

public class CodecsTest {

  @Test
  public void testSplitOnNewline() {

    SpanSequence sequence = Codecs.defaultTokenizer.apply("Tom\n\nCruise");

    Assert.assertEquals(2, sequence.size());
    Assert.assertEquals(new Span("tom\n\ncruise", 0, 3), sequence.span(0));
    Assert.assertEquals(new Span("tom\n\ncruise", 5, 11), sequence.span(1));
  }

  @Test
  public void testSplitOnTab() {

    SpanSequence sequence = Codecs.defaultTokenizer.apply("Tom\t\tCruise");

    Assert.assertEquals(2, sequence.size());
    Assert.assertEquals(new Span("tom\t\tcruise", 0, 3), sequence.span(0));
    Assert.assertEquals(new Span("tom\t\tcruise", 5, 11), sequence.span(1));
  }

  @Test
  public void testSplitOnCarriageReturn() {

    SpanSequence sequence = Codecs.defaultTokenizer.apply("Tom\r\rCruise");

    Assert.assertEquals(2, sequence.size());
    Assert.assertEquals(new Span("tom\r\rcruise", 0, 3), sequence.span(0));
    Assert.assertEquals(new Span("tom\r\rcruise", 5, 11), sequence.span(1));
  }

  @Test
  public void testSplitOnWhitespace() {

    SpanSequence sequence = Codecs.defaultTokenizer.apply("Tom  Cruise");

    Assert.assertEquals(2, sequence.size());
    Assert.assertEquals(new Span("tom  cruise", 0, 3), sequence.span(0));
    Assert.assertEquals(new Span("tom  cruise", 5, 11), sequence.span(1));
  }

  @Test
  public void testSplitOnNoBreakSpace() {

    SpanSequence sequence = Codecs.defaultTokenizer.apply("Tom\u00a0\u00a0Cruise");

    Assert.assertEquals(2, sequence.size());
    Assert.assertEquals(new Span("tom  cruise", 0, 3), sequence.span(0));
    Assert.assertEquals(new Span("tom  cruise", 5, 11), sequence.span(1));
  }
}
