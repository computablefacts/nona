package com.computablefacts.nona.helpers;

import static com.computablefacts.nona.helpers.Codecs.SPAN_SEQUENCE_EMPTY;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class DefaultTokenizerTest {

  @Test
  public void testTokenizerEdgeCases() {
    assertEquals(SPAN_SEQUENCE_EMPTY, Codecs.defaultTokenizer.apply(null));
    assertEquals(SPAN_SEQUENCE_EMPTY, Codecs.defaultTokenizer.apply(""));
  }

  @Test
  public void testTokenizeGibberish() {

    SpanSequence spansComputed = Codecs.defaultTokenizer.apply("~\"`!@#$%^&*()-+=[]{}\\|;:,.<>?/_");

    String textNormalized = "~\"'!@#$%^&*()-+=[]{}\\|;:,.<>?/_";
    SpanSequence spansExpected = new SpanSequence();
    spansExpected.add(new Span(textNormalized, 6, 7)); // $

    assertEquals(spansExpected, spansComputed);
  }

  @Test
  public void testTokenizeSentence() {

    SpanSequence spansComputed =
        Codecs.defaultTokenizer.apply("Nous sommes le 29 avril 2017 (29/04/2017) et il est 12:43.");

    String textNormalized = "nous sommes le 29 avril 2017 (29/04/2017) et il est 12:43.";
    SpanSequence spansExpected = new SpanSequence();
    spansExpected.add(new Span(textNormalized, 0, 4)); // nous
    spansExpected.add(new Span(textNormalized, 5, 11)); // sommes
    spansExpected.add(new Span(textNormalized, 12, 14)); // le
    spansExpected.add(new Span(textNormalized, 15, 17)); // 29
    spansExpected.add(new Span(textNormalized, 18, 23)); // avril
    spansExpected.add(new Span(textNormalized, 24, 28)); // 2017
    spansExpected.add(new Span(textNormalized, 30, 32)); // 29
    spansExpected.add(new Span(textNormalized, 33, 35)); // 04
    spansExpected.add(new Span(textNormalized, 36, 40)); // 2017
    spansExpected.add(new Span(textNormalized, 42, 44)); // et
    spansExpected.add(new Span(textNormalized, 45, 47)); // il
    spansExpected.add(new Span(textNormalized, 48, 51)); // est
    spansExpected.add(new Span(textNormalized, 52, 54)); // 12
    spansExpected.add(new Span(textNormalized, 55, 57)); // 43

    assertEquals(spansExpected, spansComputed);
  }

  @Test
  public void testTokenizeEmail() {

    SpanSequence spansComputed = Codecs.defaultTokenizer.apply("csavelief@mncc.fr.");

    String textNormalized = "csavelief@mncc.fr.";
    SpanSequence spansExpected = new SpanSequence();
    spansExpected.add(new Span(textNormalized, 0, 9)); // csavelief
    spansExpected.add(new Span(textNormalized, 10, 14)); // mncc
    spansExpected.add(new Span(textNormalized, 15, 17)); // fr

    assertEquals(spansExpected, spansComputed);
  }
}
