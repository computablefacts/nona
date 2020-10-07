package com.computablefacts.nona.helpers;

import static com.computablefacts.nona.helpers.Codecs.SPAN_SEQUENCE_EMPTY;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class NopTokenizerTest {

  @Test
  public void testTokenizerEdgeCases() {
    assertEquals(SPAN_SEQUENCE_EMPTY, Codecs.nopTokenizer.apply(null));
    assertEquals(SPAN_SEQUENCE_EMPTY, Codecs.nopTokenizer.apply(""));
  }

  @Test
  public void testTokenizeGibberish() {

    SpanSequence spansComputed = Codecs.nopTokenizer.apply("~\"`!@#$%^&*()-+=[]{}\\|;:,.<>?/_");

    String textNormalized = "~\"'!@#$%^&*()-+=[]{}\\|;:,.<>?/_";
    SpanSequence spansExpected = new SpanSequence();
    spansExpected.add(new Span(textNormalized, 0, textNormalized.length()));

    assertEquals(spansExpected, spansComputed);
  }

  @Test
  public void testTokenizeSentence() {

    SpanSequence spansComputed =
        Codecs.nopTokenizer.apply("Nous sommes le 29 avril 2017 (29/04/2017) et il est 12:43.");

    String textNormalized = "nous sommes le 29 avril 2017 (29/04/2017) et il est 12:43.";
    SpanSequence spansExpected = new SpanSequence();
    spansExpected.add(new Span(textNormalized, 0, textNormalized.length()));

    assertEquals(spansExpected, spansComputed);
  }

  @Test
  public void testTokenizeEmail() {

    SpanSequence spansComputed = Codecs.nopTokenizer.apply("csavelief@mncc.fr.");

    String textNormalized = "csavelief@mncc.fr.";
    SpanSequence spansExpected = new SpanSequence();
    spansExpected.add(new Span(textNormalized, 0, textNormalized.length()));

    assertEquals(spansExpected, spansComputed);
  }
}
