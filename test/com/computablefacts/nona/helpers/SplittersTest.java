package com.computablefacts.nona.helpers;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

import com.computablefacts.asterix.Span;
import com.computablefacts.asterix.SpanSequence;

public class SplittersTest {

  @Test
  public void testSplitOverlappingVeryShortText() {

    Function<SpanSequence, List<SpanSequence>> splitter = Splitters.overlappingGroupBy(5, 2);
    List<SpanSequence> spans = splitter.apply(tokenize(veryShortText()));

    assertEquals(1, spans.size());
    assertEquals("Dummy text.", join(spans.get(0)));
  }

  @Test
  public void testSplitNonOverlappingVeryShortText() {

    Function<SpanSequence, List<SpanSequence>> splitter = Splitters.nonOverlappingGroupBy(5);
    List<SpanSequence> spans = splitter.apply(tokenize(veryShortText()));

    assertEquals(1, spans.size());
    assertEquals("Dummy text.", join(spans.get(0)));
  }

  @Test
  public void testSplitOverlappingShortText() {

    Function<SpanSequence, List<SpanSequence>> splitter = Splitters.overlappingGroupBy(5, 2);
    List<SpanSequence> spans = splitter.apply(tokenize(shortText()));

    assertEquals(2, spans.size());
    assertEquals("Lorem Ipsum is dummy text.", join(spans.get(0)));
    assertEquals("dummy text.", join(spans.get(1)));
  }

  @Test
  public void testSplitNonOverlappingShortText() {

    Function<SpanSequence, List<SpanSequence>> splitter = Splitters.nonOverlappingGroupBy(5);
    List<SpanSequence> spans = splitter.apply(tokenize(shortText()));

    assertEquals(1, spans.size());
    assertEquals("Lorem Ipsum is dummy text.", join(spans.get(0)));
  }

  @Test
  public void testSplitOverlappingLongText() {

    Function<SpanSequence, List<SpanSequence>> splitter = Splitters.overlappingGroupBy(5, 2);
    List<SpanSequence> spans = splitter.apply(tokenize(longText()));

    assertEquals(4, spans.size());
    assertEquals("Lorem Ipsum is simply dummy", join(spans.get(0)));
    assertEquals("simply dummy text of the", join(spans.get(1)));
    assertEquals("of the printing and typesetting", join(spans.get(2)));
    assertEquals("and typesetting industry.", join(spans.get(3)));
  }

  @Test
  public void testSplitNonOverlappingLongText() {

    Function<SpanSequence, List<SpanSequence>> splitter = Splitters.nonOverlappingGroupBy(5);
    List<SpanSequence> spans = splitter.apply(tokenize(longText()));

    assertEquals(3, spans.size());
    assertEquals("Lorem Ipsum is simply dummy", join(spans.get(0)));
    assertEquals("text of the printing and", join(spans.get(1)));
    assertEquals("typesetting industry.", join(spans.get(2)));
  }

  @Test
  public void testOverlappingNGrams() {

    SpanSequence ngrams = Splitters.overlappingNGrams(3).apply("overlaps");

    assertEquals(6, ngrams.size());
    assertEquals("ove", ngrams.span(0).text());
    assertEquals("ver", ngrams.span(1).text());
    assertEquals("erl", ngrams.span(2).text());
    assertEquals("rla", ngrams.span(3).text());
    assertEquals("lap", ngrams.span(4).text());
    assertEquals("aps", ngrams.span(5).text());
  }

  @Test
  public void testNonOverlappingNGrams() {

    SpanSequence ngrams = Splitters.nonOverlappingNGrams(3).apply("overlaps");

    assertEquals(3, ngrams.size());
    assertEquals("ove", ngrams.span(0).text());
    assertEquals("rla", ngrams.span(1).text());
    assertEquals("ps", ngrams.span(2).text());
  }

  private String veryShortText() {
    return "Dummy text.";
  }

  private String shortText() {
    return "Lorem Ipsum is dummy text.";
  }

  private String longText() {
    return "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
  }

  private SpanSequence tokenize(String text) {

    SpanSequence sequence = new SpanSequence();
    StringIterator iterator = new StringIterator(text);

    while (iterator.hasNext()) {

      iterator.movePastWhitespace();
      int begin = iterator.position();
      iterator.moveToWhitespace();
      int end = iterator.position();

      sequence.add(new Span(text, begin, end));
    }
    return sequence;
  }

  private String join(SpanSequence sequence) {
    return sequence.stream().map(s -> s.text()).collect(Collectors.joining(" "));
  }
}
