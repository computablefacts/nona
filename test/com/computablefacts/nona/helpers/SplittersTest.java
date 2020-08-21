package com.computablefacts.nona.helpers;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.computablefacts.nona.types.Span;
import com.computablefacts.nona.types.SpanSequence;

public class SplittersTest {

  @Test
  public void testSplitOverlappingVeryShortText() {

    Function<SpanSequence, List<SpanSequence>> splitter = Splitters.overlapping(5, 2);
    List<SpanSequence> spans = splitter.apply(tokenize(veryShortText()));

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("Dummy text.", join(spans.get(0)));
  }

  @Test
  public void testSplitNonOverlappingVeryShortText() {

    Function<SpanSequence, List<SpanSequence>> splitter = Splitters.nonOverlapping(5);
    List<SpanSequence> spans = splitter.apply(tokenize(veryShortText()));

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("Dummy text.", join(spans.get(0)));
  }

  @Test
  public void testSplitOverlappingShortText() {

    Function<SpanSequence, List<SpanSequence>> splitter = Splitters.overlapping(5, 2);
    List<SpanSequence> spans = splitter.apply(tokenize(shortText()));

    Assert.assertEquals(2, spans.size());
    Assert.assertEquals("Lorem Ipsum is dummy text.", join(spans.get(0)));
    Assert.assertEquals("dummy text.", join(spans.get(1)));
  }

  @Test
  public void testSplitNonOverlappingShortText() {

    Function<SpanSequence, List<SpanSequence>> splitter = Splitters.nonOverlapping(5);
    List<SpanSequence> spans = splitter.apply(tokenize(shortText()));

    Assert.assertEquals(1, spans.size());
    Assert.assertEquals("Lorem Ipsum is dummy text.", join(spans.get(0)));
  }

  @Test
  public void testSplitOverlappingLongText() {

    Function<SpanSequence, List<SpanSequence>> splitter = Splitters.overlapping(5, 2);
    List<SpanSequence> spans = splitter.apply(tokenize(longText()));

    Assert.assertEquals(4, spans.size());
    Assert.assertEquals("Lorem Ipsum is simply dummy", join(spans.get(0)));
    Assert.assertEquals("simply dummy text of the", join(spans.get(1)));
    Assert.assertEquals("of the printing and typesetting", join(spans.get(2)));
    Assert.assertEquals("and typesetting industry.", join(spans.get(3)));
  }

  @Test
  public void testSplitNonOverlappingLongText() {

    Function<SpanSequence, List<SpanSequence>> splitter = Splitters.nonOverlapping(5);
    List<SpanSequence> spans = splitter.apply(tokenize(longText()));

    Assert.assertEquals(3, spans.size());
    Assert.assertEquals("Lorem Ipsum is simply dummy", join(spans.get(0)));
    Assert.assertEquals("text of the printing and", join(spans.get(1)));
    Assert.assertEquals("typesetting industry.", join(spans.get(2)));
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
